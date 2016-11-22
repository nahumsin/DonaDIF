package com.example.nahumsin.donadif;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jxsxs on 6/11/16.
 */

public class ConectionDB{
    private SQLiteDatabase db;
    private Context nContext;
    private DataBase objDb;
    private int id_familia;
    private Cuenta logedUser;


    public ConectionDB(Context context) {
        nContext = context;
    }

    public Cuenta getLogedUser(){
        return logedUser;
    }

    public void insertarFamilia(Familia fam){
        if (!familiaExiste(fam.getDireccion())) {
            class InsertarFamilia extends AsyncTask<Void,Void,String>{
                Familia fam;
                public InsertarFamilia(Familia fam){
                    this.fam = fam;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String,String> params = new HashMap<>();
                    params.put(Config.KEY_FAM_NAME, fam.getNombre());
                    params.put(Config.KEY_FAM_DIR, fam.getDireccion());
                    params.put(Config.KEY_FAM_DES, fam.getDescripcion());
                    params.put(Config.KEY_FAM_IMG, fam.getImagen());
                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.URL_ADD_FAMILIA, params);
                    return res;
                }
            }
            InsertarFamilia ae = new InsertarFamilia(fam);
            ae.execute();
            Toast.makeText(nContext, "Familia creada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(nContext,"Ya existe una familia con la dirección " + fam.getDireccion(),Toast.LENGTH_LONG).show();
        }


    }

    public void insertarCuenta(Cuenta cuen){
        if (!emailUsuarioExiste(cuen.getCorreo())) {
            class InsertarCuenta extends AsyncTask<Void,Void,String>{
                Cuenta cuen;
                public InsertarCuenta(Cuenta cuen){
                        this.cuen = cuen;
                    }

                @Override
                protected void onPreExecute() {
                        super.onPreExecute();
                    }

                @Override
                protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String,String> params = new HashMap<>();
                    params.put(Config.KEY_CUEN_NAME,cuen.getNombreUsuario());
                    params.put(Config.KEY_CUEN_PASS,cuen.getContrasena());
                    params.put(Config.KEY_CUEN_EMAIL,cuen.getCorreo());
                    params.put(Config.KEY_CUEN_PRIV,cuen.getPrivilegio());
                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.URL_ADD_CUENTA, params);
                    return res;
                }
            }
            InsertarCuenta ae = new InsertarCuenta(cuen);
            ae.execute();
        }else{
            Toast.makeText(nContext,cuen.getCorreo() + "Ya esta asociado a otra cuenta!",Toast.LENGTH_LONG).show();
        }
    }

    public void crearDonativo(Donativo dona){
        if (db!=null){
            ContentValues valores = new ContentValues();
            valores.put("id_familia",dona.getIdFamila());
            valores.put("id_cuenta",dona.getIdDonador());
            valores.put("entregado",dona.getEntregado());
            //Toast.makeText(nContext," " + dona.getIdFamila() + " " + dona.getIdDonador(),Toast.LENGTH_SHORT).show();
            db.insert("donativo",null,valores);
            //Toast.makeText(nContext,"Donativo realizado con Exito!!",Toast.LENGTH_SHORT).show();
            db.close();
        }
    }

    public List<Familia> getFamilias(){
        class GetJSON extends AsyncTask<Object,Object,Object>{
            String JSON_STRING;
            List<Familia> listaFamilias= new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);

            }

            @Override
            protected Object doInBackground(Object... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_FAMILIAS);
                JSON_STRING = s;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for(int i = 0; i<result.length(); i++){
                        JSONObject jo = result.getJSONObject(i);
                        Familia familia = new Familia(jo.getString(Config.TAG_FAM_ID),
                                jo.getString(Config.TAG_FAM_NAME),jo.getString(Config.TAG_FAM_DIR),jo.getString(Config.TAG_FAM_DES),jo.getString((Config.TAG_FAM_IMG)));
                        listaFamilias.add(familia);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaFamilias;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Familia>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buscarUsuario(String usuario){
        List<Cuenta> listaCuentas = getCuentas();

        for (Cuenta cuenta:listaCuentas) {
            if(cuenta.getNombreUsuario().equals(usuario)){
                logedUser = cuenta;
                return true;
            }
        }
        return false;
    }
    public boolean buscarFamilia(String nombre){

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()){
            do {
                String nombre_fam = cursor.getString(1);


                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (nombre.equals(nombre_fam)) {
                    setId_familia(cursor.getInt(0));
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
        }
        return false;
    }

    public boolean emailUsuarioExiste(String email) {
        List<Cuenta> listaCuentas = getCuentas();

        for(int i = 0;i<listaCuentas.size();i++){
            String obtEmail = listaCuentas.get(i).getCorreo();
            if(email.equals(obtEmail))
                return true;
        }
        return false;
    }

    public boolean familiaExiste(String direccion){

        List<Familia> listaFamilias = getFamilias();

        for(int i = 0;i<listaFamilias.size();i++){
            String obtDireccion = listaFamilias.get(i).getDireccion();
            if(direccion.equals(obtDireccion))
                return true;
        }
        return false;
    }

    public List<Donativo> getDonativos(){
        List<Donativo> donativos = new ArrayList<>();

        String select = "SELECT * FROM donativo";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do {
                Donativo don = new Donativo(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2));
                donativos.add(don);

            }while (cursor.moveToNext());
        }
        return donativos;
    }

    public int getId_familia() {
        return id_familia;
    }

    public void setId_familia(int id_familia) {
        this.id_familia = id_familia;
    }

    private List<Cuenta> getCuentas(){

        class GetJSON extends AsyncTask<Object,Object,Object>{
            String JSON_STRING;
            List<Cuenta> listaCuentas= new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);

            }

            @Override
            protected Object doInBackground(Object... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_CUENTAS);
                JSON_STRING = s;
                Log.i("JSON", JSON_STRING);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for(int i = 0; i<result.length(); i++){
                        JSONObject jo = result.getJSONObject(i);
                        Cuenta cuenta = new Cuenta(jo.getString(Config.TAG_CUEN_ID),jo.getString(Config.TAG_CUEN_NAME),
                                jo.getString(Config.TAG_CUEN_PASS),jo.getString(Config.TAG_CUEN_EMAIL),jo.getString(Config.TAG_CUEN_PRIV));
                        listaCuentas.add(cuenta);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaCuentas;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Cuenta>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}