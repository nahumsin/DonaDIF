package com.example.nahumsin.donadif;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nahumsin on 07/11/16.
 */

public class DBCuenta extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DonaDIF";
    private static final String TABLE_CUENTAS = "cuenta";
    // Shops Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_PASS = "contrasena";

    public DBCuenta(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DBCuenta(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CUENTAS + "("
        + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT,"
        + KEY_PASS + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUENTAS);
// Creating tables again
        onCreate(sqLiteDatabase);
    }

    public void addCuenta(Cuenta cuenta){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cuenta.getNombreUsuario());
        values.put(KEY_EMAIL, cuenta.getCorreo());
        values.put(KEY_PASS, cuenta.getContrasena());
// Inserting Row
        db.insert(TABLE_CUENTAS, null, values);
        db.close(); // Closing database connection
    }

    public List<Cuenta> getCuentas() {
        List<Cuenta> listaCuenta = new ArrayList<Cuenta>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CUENTAS;

        SQLiteDatabase db = this.getWritableDatabase();
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
}
