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

    public ConectionDB(Context context) {
        nContext = context;
    }

    public void abrirConexion(){
        objDb = new DataBase(nContext,"DonaDIF",null,1);
        db = objDb.getWritableDatabase();
        Toast.makeText(nContext,"Se abrio con exito la conexion",Toast.LENGTH_LONG).show();
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
        Toast.makeText(nContext,"Se inserto una familia",Toast.LENGTH_LONG).show();
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
            valores.put("nombre_usuario",cuen.getNombreUsuario());
            valores.put("contra_usuario",cuen.getContrasena());
            valores.put("email",cuen.getCorreo());
            valores.put("privilegio",cuen.getPrivilegio());
            db.insert("cuenta",null,valores);
            Toast.makeText(nContext,"Se inserto una cuenta",Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    public boolean crearDonativo(int id_cuenta,int id_familia,int entregado){

        boolean resultado = false;

        try {
            String query = "INSERT INTO donativo(id_familia,id_cuenta,entregado) " +
                    "VALUES ('"+id_familia+"','"+id_cuenta+"','"+entregado+"')'";
            db.execSQL(query);
            resultado = true;
            return resultado;
        }catch (Exception e){
            resultado = false;
            return resultado;
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
                //Toast.makeText(nContext, "El usuario " + nombre + " aqui está", Toast.LENGTH_LONG).show();
                if (usuario.equals(nombre)) {
                    return true;
                }
            }while (cursor.moveToNext());
        }else{
            return false;
        }
        return false;
    }


}