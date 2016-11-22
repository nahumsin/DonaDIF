package com.example.nahumsin.donadif;


import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class seleccionarFamilia extends AppCompatActivity {
    int canastas = 0;
    List<String> items = new ArrayList<>();
    List<String> selectedItems = new ArrayList<>();
    List<Familia> listaFamilia;
    List<Donativo> listaDonativos;
    ListView lista;
    ConectionDB db;
    Button btnHacerDonativo;
    int ids_familias[] = new int[20];
    int id_usuario = 0;
    int id_pos;
    List<Integer> posList = new ArrayList<>();
    String nombreFamilias[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_familia);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        lista = (ListView) findViewById(R.id.listViewLista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnHacerDonativo = (Button) findViewById(R.id.btnHacerDonativo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnHacerDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
                id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));
                if (selectedItems.size() == canastas) {
                    List<Familia> fams = db.getFamiliasSinDonativo();
                    for (int item : posList) {
                        db.crearDonativo(new Donativo(Integer.parseInt(fams.get(item).getId()), id_usuario));
                        Intent intent2 = new Intent(seleccionarFamilia.this, MainActivity.class);
                        startActivity(intent2);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Tiene que seleccionar " + canastas + " familias", Toast.LENGTH_LONG).show();
                }
            }
        });

        db = new ConectionDB(this);

        showFamilias();
    }


    void showFamilias() {
        listaDonativos = db.getDonativos();
        listaFamilia = db.getFamilias();
        int bandera=0;
        if (listaFamilia.isEmpty())
            Toast.makeText(getBaseContext(), "No hay familas necesitadas de donativo", Toast.LENGTH_LONG).show();
        else
                for (Familia familia : listaFamilia) {
                    bandera = 0;
                    for (Donativo don : listaDonativos) {
                        if (!familia.getId().equals(don.getIdFamila() + "")||familia.getEntregado().equals("0")) {
                            bandera = 1;
                        }
                    }
                    if(bandera == 0)
                        items.add(familia.getImagen() + " " + familia.getNombre());
                }
        if(bandera==1){
            Toast.makeText(getBaseContext(), "No hay familas necesitadas de donativo", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this, R.layout.rowlayout, items);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id_pos = (int) adapterView.getItemIdAtPosition(i);
                if (posList.contains(id_pos))
                    posList.remove(id_pos);
                else
                    posList.add(id_pos);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.btnHacerDonativo) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_cuenta, menu);
        return super.onCreateOptionsMenu(menu);
    }

}