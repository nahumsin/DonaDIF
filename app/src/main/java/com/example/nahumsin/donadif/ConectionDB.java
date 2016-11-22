package com.example.nahumsin.donadif;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

        class InsertarDonativo extends AsyncTask<Void,Void,String>{
            Donativo don;
            public InsertarDonativo(Donativo don){
                this.don = don;
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
                params.put(Config.KEY_DON_ID_FAM,don.getIdFamila()+"");
                params.put(Config.KEY_DON_ID_CUEN,don.getIdDonador()+"");
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_DONATIVO, params);
                return res;
            }
        }
        InsertarDonativo ae = new InsertarDonativo(dona);
        ae.execute();
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
                                jo.getString(Config.TAG_FAM_NAME),jo.getString(Config.TAG_FAM_DIR),jo.getString(Config.TAG_FAM_DES),
                                jo.getString(Config.TAG_FAM_IMG),jo.getString(Config.TAG_FAM_ENTR));
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
        if (cursor.moveToFirst()){
            do {
                String nombre_fam = cursor.getString(1);
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
        class GetJSON extends AsyncTask<Object,Object,Object>{
            String JSON_STRING;
            List<Donativo> listaDonativos= new ArrayList<>();
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_DONATIVOS);
                JSON_STRING = s;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for(int i = 0; i<result.length(); i++){
                        JSONObject jo = result.getJSONObject(i);
                        Donativo donativo = new Donativo(Integer.parseInt(jo.getString(Config.TAG_DON_ID)), Integer.parseInt(jo.getString(Config.TAG_DON_ID_FAM)),
                                Integer.parseInt(jo.getString(Config.TAG_DON_ID_CUEN)));
                        listaDonativos.add(donativo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaDonativos;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Donativo>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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