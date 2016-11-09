package com.example.nahumsin.donadif;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jxsxs on 8/11/16.
 */

public class DataBase extends SQLiteOpenHelper {

    //Query para crear las tablas
    String query_crear_donativo = "CREATE TABLE donativo (id_donativo INTEGER PRIMARY KEY AUTOINCREMENT, id_familia INTEGER," +
            "id_cuenta INTEGER, entregado INTEGER)";
    String query_crear_cuenta = "CREATE TABLE cuenta (id_cuenta INTEGER PRIMARY KEY AUTOINCREMENT, nombre_usuario TEXT, contra_usuario TEXT,email TEXT,privilegio INTEGER)";
    String query_crear_familia = "CREATE TABLE familia (id_familia INTEGER PRIMARY KEY AUTOINCREMENT, nombre_familia TEXT, direccion_familia TEXT, " +
            "desc_familia TEXT, imagen TEXT)";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS familia");
        db.execSQL("DROP TABLE IF EXISTS donativo");
        db.execSQL("DROP TABLE IF EXISTS cuenta");
        db.execSQL(query_crear_cuenta);
        db.execSQL(query_crear_familia);
        db.execSQL(query_crear_donativo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS familia");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS donativo");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cuenta");
        onCreate(sqLiteDatabase);
    }
}