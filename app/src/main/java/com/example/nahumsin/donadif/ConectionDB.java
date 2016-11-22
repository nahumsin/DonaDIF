package com.example.nahumsin.donadif;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxsxs on 6/11/16.
 */

public class ConectionDB{
    private SQLiteDatabase db;
    private Context nContext;
    private DataBase objDb;
    private int id_usuario,id_familia,privilegio_cuenta;
    private String contraseña_usuario,nombre_usuario,direccion_familia;

    public ConectionDB(Context context) {
        nContext = context;
    }

    public void abrirConexion(){
        objDb = new DataBase(nContext,"DonaDIF",null,1);
        db = objDb.getWritableDatabase();
        //Toast.makeText(nContext,"Se abrio con exito la conexion",Toast.LENGTH_LONG).show();
    }

    public void cerrarConexion(){
        db.close();
    }

    public void insertarFamilia(Familia fam){

        ContentValues values = new ContentValues();
        if (!familiaExiste(fam.getDireccion())) {
            values.put("nombre_familia", fam.getNombre());
            values.put("direccion_familia", fam.getDireccion());
            values.put("desc_familia", fam.getDescripcion());
            values.put("imagen", fam.getImagen());
            values.put("donativos_recividos" ,fam.getDonativos_recividos());

            db.insert("familia", null, values);
            db.close();
        }else{
            Toast.makeText(nContext,"Ya existe una familia con la dirección " + fam.getDireccion(),Toast.LENGTH_LONG).show();
        }

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            Familia fam;
            public SendPostReqAsyncTask(Familia fam){
                this.fam = fam;
            }
            protected String doInBackground(String... params) {

                String nombre = fam.getNombre();
                String direccion = fam.getDireccion();
                String descripcion = fam.getDescripcion();
                String imagen = fam.getImagen();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nombre_familia", nombre));
                nameValuePairs.add(new BasicNameValuePair("direccion_familia", direccion));
                nameValuePairs.add(new BasicNameValuePair("desc_familia", descripcion));
                nameValuePairs.add(new BasicNameValuePair("imagen", imagen));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.0.15/insertarFamilia.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask(fam);
        sendPostReqAsyncTask.execute(fam.getNombre(),fam.getDireccion(),fam.getDescripcion(),fam.getImagen());
    }

    public void insertarCuenta(Cuenta cuen){
        if (db != null){

            if (!emailUsuarioExiste(cuen.getCorreo())) {
                ContentValues valores = new ContentValues();
                valores.put("nombre_usuario", cuen.getNombreUsuario());
                valores.put("contra_usuario", cuen.getContrasena());
                valores.put("email", cuen.getCorreo());
                valores.put("privilegio", cuen.getPrivilegio());
                db.insert("cuenta", null, valores);
                Toast.makeText(nContext,"Cuenta Creada!",Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(nContext,cuen.getCorreo() + " ya esta asociado a otra cuenta!",Toast.LENGTH_LONG).show();
            }
        }
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            Cuenta cuen;
            public SendPostReqAsyncTask(Cuenta cuen){
                this.cuen = cuen;
            }
            protected String doInBackground(String... params) {

                String user = cuen.getNombreUsuario();
                String pass = cuen.getContrasena();
                String email = cuen.getCorreo();
                String priv = cuen.getPrivilegio();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nombre_usuario", user));
                nameValuePairs.add(new BasicNameValuePair("contra_usuario", pass));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("privilegio", priv));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.0.15/crearCuenta.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask(cuen);
        sendPostReqAsyncTask.execute(cuen.getNombreUsuario(), cuen.getContrasena(),cuen.getCorreo(),cuen.getPrivilegio());


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
        List<Familia> familias = new ArrayList<>();

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do {
                Familia fam = new Familia(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));
                familias.add(fam);

            }while (cursor.moveToNext());
        }
        return familias;
    }

    public boolean buscarUsuario(String usuario){

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()){
            do {
                setNombre_usuario( cursor.getString(1));
                setId_usuario(cursor.getInt(0));
                setContraseña_usuario(cursor.getString(2));
                setPrivilegio_cuenta(cursor.getInt(4));

                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (usuario.equals(getNombre_usuario())) {
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
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
                setDireccion_familia(cursor.getString(2));
                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (nombre.contains(nombre_fam)) {
                    setId_familia(cursor.getInt(0));
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
        }
        return false;
    }

    public boolean emailUsuarioExiste(String email){

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()){
            do {
                String obtEmail = cursor.getString(3);

                //Toast.makeText(nContext, "Email: " + obtEmail + " xD " , Toast.LENGTH_LONG).show();
                if (email.equals(obtEmail)) {
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
        }
        return false;
    }

    public boolean familiaExiste(String direccion){

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()){
            do {
                String dir_fam = cursor.getString(2);

                if (direccion.equals(dir_fam)) {
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
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
                Donativo don = new Donativo(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
                donativos.add(don);

            }while (cursor.moveToNext());
        }
        return donativos;
    }

    public List<Cuenta> getCuentas(){
        List<Cuenta> cuentas = new ArrayList<>();

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do {
                Cuenta cuenta = new Cuenta(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                cuentas.add(cuenta);

            }while (cursor.moveToNext());
        }
        return cuentas;
    }

    public boolean buscaUsuario(int id_cuenta){

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()){
            do {
                int usuario = cursor.getInt(0);

                //Toast.makeText(nContext, "Email: " + obtEmail + " xD " , Toast.LENGTH_LONG).show();
                if (usuario == id_cuenta) {
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
        }
        return false;
    }

    public String getNombreFamilia(int id_familia){

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()) {
            do {
                String nombre_fam = cursor.getString(1);
                int id_fam = cursor.getInt(0);

                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (id_fam == id_familia) {
                    return nombre_fam;
                }
            } while (cursor.moveToNext());
        }
        return "";
    }

    public String getNombreDonador(int id_donador){

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()) {
            do {
                String nombre_dona = cursor.getString(1);
                int id_dona = cursor.getInt(0);

                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (id_dona == id_donador) {
                    return nombre_dona;
                }
            } while (cursor.moveToNext());
        }
        return "";
    }

    public String getEmailDonador(String nombre) {

        String select = "SELECT * FROM cuenta";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        if (cursor.moveToFirst()) {
            do {
                String nombre_user = cursor.getString(1);
                String obtEmail = cursor.getString(3);

                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (nombre.contains(nombre_user)) {
                    return obtEmail;
                }
            } while (cursor.moveToNext());
        }
        return "";
    }
    //Modifica el entregado, de una familia
    public void confirmarFamiliaConCanasta(int id_familia){
        //db = objDb.getReadableDatabase();
        ContentValues update_familia = new ContentValues();
        update_familia.put("donativos_recividos",1);

        db.update("familia",update_familia,"id_familia='" + id_familia + "'",null);
        db.close();
    }

    public boolean entregadasTodasLasFamilias() {

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        //Toast.makeText(nContext,"Al menos estoy aqui :(",Toast.LENGTH_LONG).show();
        int i = 0;
        int entregado = 0;
        if (cursor.moveToFirst()) {
            do {
                entregado = cursor.getInt(5);

                if (entregado == 1){
                    return true;
                }
                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
        return false;
    }

    public void reiniciarRecibidos(){
        //db = objDb.getReadableDatabase();
        ContentValues update_familia = new ContentValues();
        update_familia.put("donativos_recividos",0);

        db.update("familia",update_familia,null,null);
        db.close();
    }
/*
    public List<Cuenta> getCuentas() {
        List<Cuenta> listaCuenta = new ArrayList<Cuenta>();
// Select All Query
        String selectQuery = "SELECT * FROM cuenta";
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cuenta cuenta = new Cuenta(cursor.getString(0),cursor.getString(1),cursor.getString(2),0);
// Adding contact to list
                listaCuenta.add(cuenta);
            } while (cursor.moveToNext());
        }

// return contact list
        return listaCuenta;
    }
    */

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getContraseña_usuario() {
        return contraseña_usuario;
    }

    public void setContraseña_usuario(String contraseña_usuario) {
        this.contraseña_usuario = contraseña_usuario;
    }

    public int getId_familia() {
        return id_familia;
    }

    public void setId_familia(int id_familia) {
        this.id_familia = id_familia;
    }

    public int getPrivilegio_cuenta() {
        return privilegio_cuenta;
    }

    public void setPrivilegio_cuenta(int privilegio_cuenta) {
        this.privilegio_cuenta = privilegio_cuenta;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getDireccion_familia() {
        return direccion_familia;
    }

    public void setDireccion_familia(String direccion_familia) {
        this.direccion_familia = direccion_familia;
    }
}