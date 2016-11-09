package com.example.nahumsin.donadif;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoModFamilia extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listado;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //cargarFamilias();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mod_familia);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.listaFam);
       // cargarFamilias();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int clave =Integer.parseInt(listado.get(position).split(" ")[0]);
                String nombre = listado.get(position).split(" ")[1];
                String direccion = listado.get(position).split(" ")[2];
                String descripcion = listado.get(position).split(" ")[3];

                Intent intent = new Intent(ListadoModFamilia.this,Activity_ModificarFamilia.class);

                intent.putExtra("Id",clave);
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Direccion",direccion);
                intent.putExtra("Descripcion",descripcion);
                startActivity(intent);


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.action_listar){
            startActivity(new Intent(this,Activity_ModificarFamilia.class));
        }*/
        return super.onOptionsItemSelected(item);

    }






}

