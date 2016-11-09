package com.example.nahumsin.donadif;


import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class seleccionarFamilia extends AppCompatActivity {
    int canastas = 0;
    List<String> familias = new ArrayList<>();
    List<Familia> listaFamilia;
    ListView lista;
    ConectionDB db;
    Button btnHacerDonativo;

    String nombreFamilias[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_familia);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        lista = (ListView) findViewById(R.id.listViewLista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnHacerDonativo = (Button)findViewById(R.id.btnHacerDonativo) ;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnHacerDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray seleccionados = lista.getCheckedItemPositions();
                if (seleccionados != null){
                    canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
                    lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String lisChoice = lista.getItemAtPosition(i).toString();



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });
        db = new ConectionDB(this);
        db.abrirConexion();

        //db.insertarFamilia(new Familia("Villa Sigg","Guadalupe #10","Familia con 5 integrantes","maria.png"));

    }

    void insertarFamilia(){

    }

    void showFamilias(){
        listaFamilia = db.getFamilias();

        for (Familia familia: listaFamilia) {
            familias.add(familia.getImagen() + " " + familia.getNombre());
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,R.layout.seleccionarfamilialayoutrow,familias);
        lista.setAdapter(adaptador);

    }
}