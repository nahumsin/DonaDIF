package com.example.nahumsin.donadif;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    List<String> itemsIds = new ArrayList<>();
    List<String> selectedItems = new ArrayList<>();
    List<Familia> listaFamilia;
    List<Donativo> listaDonativos;
    ListView lista, listaIds;
    ConectionDB db;
    Button btnHacerDonativo;
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
        btnHacerDonativo.setEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnHacerDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
                id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));

                if (selectedItems.size() == canastas) {
                    for (String item : selectedItems) {
                        String[] id_fam = item.split(" ");
                        db.crearDonativo(new Donativo(Integer.parseInt(id_fam[0]), id_usuario));
                        Intent intent2 = new Intent(seleccionarFamilia.this, MainActivity.class);
                        intent2.putExtra("id_usuario",id_usuario+"");
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
        listaFamilia = db.getFamilias();//db.getFamiliasSinDonativo();

        if (listaFamilia.size() == 0)
            Toast.makeText(getBaseContext(), "No hay familas necesitadas de donativo", Toast.LENGTH_LONG).show();
        else
            for (Familia familia : listaFamilia) {
                if (listaDonativos.size() == 0) {
                    if (familia.getEntregado().equals("0")) {
                        items.add(familia.getId() + " " + familia.getImagen() + " " + familia.getNombre());
                    }
                } else {
                    if (listaDonativos.size() < listaFamilia.size()) {
                        for (Donativo don : listaDonativos) {
                            if (!familia.getId().equals(don.getIdFamila() + "") && familia.getEntregado().equals("0"))
                                items.add(familia.getId() + " " + familia.getImagen() + " " + familia.getNombre());
                        }
                    }else{
                        btnHacerDonativo.setEnabled(false);
                        Toast.makeText(getBaseContext(), "No hay familas para mostrar", Toast.LENGTH_LONG).show();
                    }
                }

            }

        ArrayAdapter adaptador = new ArrayAdapter<String>(this, R.layout.rowlayout, items);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView) view).getText().toString();
                //(id_don = (int )adapterView.getItemIdAtPosition(i);

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