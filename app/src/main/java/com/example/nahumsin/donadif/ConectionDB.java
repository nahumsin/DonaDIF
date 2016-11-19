package com.example.nahumsin.donadif;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    private String contraseña_usuario;

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
        values.put("nombre_familia",fam.getNombre());
        values.put("direccion_familia",fam.getDireccion());
        values.put("desc_familia",fam.getDescripcion());
        values.put("imagen",fam.getImagen());

        db.insert("familia",null,values);
        db.close();

        /*boolean resultado = false;

        try {
            String query = "INSERT INTO familia(nombre_familia,direccion_familia,desc_familia,imagen) " +
                    "VALUES ('"+nombre+"','"+dire+"','"+desc+"','"+imagen+"')'";
            db.execSQL(query);
            resultado = true;
            return resultado;

        }catch (Exception e){
            resultado = false;
            return resultado;
        }*/

    }

    public void insertarCuenta(Cuenta cuen){
        if (db != null){
            ContentValues valores = new ContentValues();
            valores.put("nombre_usuario", cuen.getNombreUsuario());
            valores.put("contra_usuario", cuen.getContrasena());
            valores.put("email", cuen.getCorreo());
            valores.put("privilegio", cuen.getPrivilegio());
            db.insert("cuenta", null, valores);
            db.close();
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
        List<Familia> familias = new ArrayList<>();

        String select = "SELECT * FROM familia";
        db = objDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do {
                Familia fam = new Familia(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
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
                String nombre = cursor.getString(1);
                setId_usuario(cursor.getInt(0));
                setContraseña_usuario(cursor.getString(2));
                setPrivilegio_cuenta(cursor.getInt(4));

                //Toast.makeText(nContext, "Contraseña" + getContraseña_usuario() + " xD " + "ID_" + getId_usuario(), Toast.LENGTH_LONG).show();
                if (usuario.equals(nombre)) {
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
}