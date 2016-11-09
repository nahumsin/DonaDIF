package com.example.nahumsin.donadif;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SistemasNA on 09/11/2016.
 */

public class DBFamilia extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DonaDIF";
    private static final String TABLE_FAMILIAS = "familias";
    // Shops Table Columns names
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DIRECCION = "direccion";
    private static final String KEY_DESCRIPCION= "descripcion";
    private static final String KEY_IMAGEN= "imagen" ;

    public DBFamilia(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_FAM_TABLE = "CREATE TABLE " + TABLE_FAMILIAS + "("
                + KEY_NOMBRE + " TEXT," + KEY_DIRECCION + " TEXT,"
                + KEY_DESCRIPCION + " TEXT" + KEY_IMAGEN + "BLOB);";
        sqLiteDatabase.execSQL(CREATE_FAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILIAS);
// Creating tables again
        onCreate(sqLiteDatabase);
    }

    public void addFamilia(Family familia){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, familia.getNombre());
        values.put(KEY_DIRECCION, familia.getDireccion());
        values.put(KEY_DESCRIPCION, familia.getDescripcion());
// Inserting Row
        db.insert(TABLE_FAMILIAS, null, values);
        db.close(); // Closing database connection
    }

    public List<Family> getFamilias() {
        List<Family> listaFamilia = new ArrayList<Family>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FAMILIAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Family fam = new Family(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
// Adding contact to list
                listaFamilia.add(fam);
            } while (cursor.moveToNext());
        }

// return contact list
        return listaFamilia;
    }

    public void updateFamilia(){

    }
}
