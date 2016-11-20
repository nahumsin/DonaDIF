package com.example.nahumsin.donadif;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class seleccionarFamilia extends AppCompatActivity {
    int canastas = 0;
    List<String> items = new ArrayList<>();
    List<String> selectedItems = new ArrayList<>();
    List<Familia> listaFamilia;
    List<Donativo> listaDonativo;
    ListView lista;
    ConectionDB db;
    Button btnHacerDonativo;
    int ids_familias[] = new int[20];
    int id_usuario = 0;

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
                Bundle extras = getIntent().getExtras();
                canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
                id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));
                //Toast.makeText(getBaseContext(), "id_usuario: " + id_usuario + " \nid_familia: " + db.getId_familia(), Toast.LENGTH_LONG).show();
                if (selectedItems.size() == canastas) {
                    for (String item : selectedItems) {
                        String[] nombre = item.split(" ");

                        if (db.buscarFamilia(nombre[1] + " " + nombre[2])) {
                            db.crearDonativo(new Donativo(db.getId_familia(), id_usuario, 0));
                            Intent intent = new Intent(seleccionarFamilia.this, PruebasDataBase.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(), "Donativo Realizado!!", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getBaseContext(), "ID_FAM: " + db.getId_familia(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getBaseContext(), "id_usuario: " + id_usuario + " \nid_fam: " + db.getId_familia() + "\n", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getBaseContext(), "Donativos: " + db.getDonativos().toString() + "\n", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Tiene que seleccionar " + canastas + " familias", Toast.LENGTH_LONG).show();
                }
            }
        });

        db = new ConectionDB(this);
        db.abrirConexion();


        //db.insertarFamilia(new Familia("Gonzales Ortega","Zacatecas #14","Familia con 2 integrantes","gon.png"));
        showFamilias();
    }


    void showFamilias() {
        listaFamilia = db.getFamilias();
        listaDonativo = db.getDonativos();
        //Toast.makeText(getBaseContext(), "lista.size: " + db.getDonativos(), Toast.LENGTH_LONG).show();
        // for (Donativo dona:listaDonativo) {
        //   Toast.makeText(getBaseContext(), "ID_fam_dona: " + dona.getIdFamila(), Toast.LENGTH_LONG).show();
        //}
        for (Familia familia : listaFamilia) {
            //Toast.makeText(getBaseContext(), "Pendientes de entre: " + familia.getId() + " Dona_Recividos: " + familia.getDonativos_recividos(), Toast.LENGTH_LONG).show();
            if (listaDonativo.size() == 0) {
                if ( familia.getDonativos_recividos() == 0) {
                    items.add(familia.getImagen() + " " + familia.getNombre());
                }else{
              //      Toast.makeText(getBaseContext(), "Pendiente de Entragar", Toast.LENGTH_LONG).show();
                }
            }else{
                for (Donativo donativo : listaDonativo) {
                    //Log.d("ID_Familia_fam: ", familia.getId() + "");
                    if (familia.getId() != donativo.getIdFamila() && familia.getDonativos_recividos() != 0) {
                        //Toast.makeText(getBaseContext(), "Entra aqui", Toast.LENGTH_LONG).show();
                        items.add(familia.getImagen() + " " + familia.getNombre());
                    } else {
                        Toast.makeText(getBaseContext(), "Ya existe", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this, R.layout.rowlayout, items);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove((selectedItem));
                } else {
                    selectedItems.add(selectedItem);
                }
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