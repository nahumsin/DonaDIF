package com.example.nahumsin.donadif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PruebasDataBase extends AppCompatActivity {
    ListView listaDonadores;
    ConectionDB dbDonadores;
    List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas_data_base);
        listaDonadores = (ListView) findViewById(R.id.listaDonadores);
        dbDonadores = new ConectionDB(this);
        dbDonadores.abrirConexion();

        List<Donativo> donativos = dbDonadores.getDonativos();
        for (Donativo donativo:donativos) {
            items.add("Donador: " + donativo.getIdDonador() + " Fam: " + donativo.getIdFamila());
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,R.layout.seleccionarfamilialayoutrow,items);
        listaDonadores.setAdapter(adaptador);
        dbDonadores.cerrarConexion();
    }
}
