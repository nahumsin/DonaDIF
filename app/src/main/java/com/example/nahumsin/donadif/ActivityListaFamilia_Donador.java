package com.example.nahumsin.donadif;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ActivityListaFamilia_Donador extends AppCompatActivity {

    private Bundle savedInstanceState;
    ConectionDB dbFamilia;
    ListView listaFamilia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_familia__donador);
        listaFamilia =(ListView) findViewById(R.id.listaFam);
        cargar();
    }

    public void cargar(){
        List<Familia> familias = dbFamilia.getFamilias() ;
        for (Familia familia:familias) {
            String log = "Nombre: " + familia.getNombre() + " ,Direccion: " + familia.getNombre() + " ,Descripcion: " + familia.getDescripcion();
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);
        ListView lista =(ListView) findViewById(R.id.listaFam);
        lista.setAdapter(adapter);*/
    }
}