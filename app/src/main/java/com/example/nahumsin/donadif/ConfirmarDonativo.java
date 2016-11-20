package com.example.nahumsin.donadif;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConfirmarDonativo extends AppCompatActivity {
    List<String> items = new ArrayList<>();
    List<String> selectedItems = new ArrayList<>();
    List<Familia> listaFamilia;
    List<Donativo> listaDonativo;
    ListView lista;
    ConectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_donativo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView chl = (ListView) findViewById((R.id.checable_list));
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<String> items = new ArrayList<>();
        items.add("Donador: Roberto Garcia, Familia: Sánchez Pérez");
        items.add("Donador: Aracelí Arámbula, Familia: Carrillo Castañeda");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.rowlayout,items);
        chl.setAdapter(adapter);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem)){
                    selectedItems.remove((selectedItem));
                }
                else
                    selectedItems.add(selectedItem);
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
        int contadorFamilias = 0;
        //Toast.makeText(getBaseContext(), "lista.size: " + db.getDonativos(), Toast.LENGTH_LONG).show();
        // for (Donativo dona:listaDonativo) {
        //   Toast.makeText(getBaseContext(), "ID_fam_dona: " + dona.getIdFamila(), Toast.LENGTH_LONG).show();
        //}
        for (Familia familia : listaFamilia) {
            //Toast.makeText(getBaseContext(), "Pendientes de entre: " + familia.getId() + " Dona_Recividos: " + familia.getDonativos_recividos(), Toast.LENGTH_LONG).show();
            if (listaDonativo.size() == 0) {
                if ( familia.getDonativos_recividos() == 0) {
                    items.add(familia.getNombre());
                }else{
                    contadorFamilias +=1;
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

        if (listaFamilia.size() == contadorFamilias){
            //Ponemos todas las familias en cero
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_cuenta, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.actionDone) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
