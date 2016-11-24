package com.example.nahumsin.donadif;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MostrarFamiliaDonador extends AppCompatActivity {

    private Bundle savedInstanceState;
    ConectionDB dbFamilia;
    ListView listaFamilia;
    List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_familia__donador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaFamilia =(ListView) findViewById(R.id.listaFam);
        dbFamilia = new ConectionDB(this);
        cargar();
    }

    public void cargar(){
        List<Familia> familias = dbFamilia.getFamilias() ;
        for (Familia familia:familias) {
            String log = "Nombre: " + familia.getNombre() + " ,Direccion: " + familia.getNombre() + " ,Descripcion: " + familia.getDescripcion();
        }

        for (Familia familia: familias) {
            items.add(familia.getImagen() + " " + familia.getNombre());
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,R.layout.seleccionarfamilialayoutrow,items);
        listaFamilia.setAdapter(adaptador);
        listaFamilia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=((TextView)view).getText().toString();

            }
        });

    }
}