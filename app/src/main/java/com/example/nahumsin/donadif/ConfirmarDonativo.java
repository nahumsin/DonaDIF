package com.example.nahumsin.donadif;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmarDonativo extends AppCompatActivity implements ListView.OnItemClickListener{
    List<Donativo> listaDonativo;
    ListView lista;
    ConectionDB db;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_donativo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lista = (ListView) findViewById(R.id.listaFamilias);
        lista.setOnItemClickListener(this);
        db = new ConectionDB(this);

        showFamilias();
    }

    void showFamilias() {
        listaDonativo = db.getDonativos();

        for (Donativo donativo : listaDonativo) {
            HashMap<String,String> donativos = new HashMap<>();
            donativos.put(Config.TAG_DON_ID,donativo.getIdDonativo()+"");
            donativos.put(Config.TAG_DON_ID_CUEN,"Donador: "+db.getNombreDonador(donativo.getIdDonador()+""));
            donativos.put(Config.TAG_DON_ID_FAM,"Familia: "+db.getNombreFamilia(donativo.getIdFamila()+""));
            list.add(donativos);

        }
        ListAdapter adapter = new SimpleAdapter(
                ConfirmarDonativo.this,list, R.layout.list_item,
                new String[]{Config.TAG_DON_ID,Config.TAG_DON_ID_CUEN,Config.TAG_DON_ID_FAM},
                new int[]{R.id.id, R.id.name, R.id.direccion});

        lista.setAdapter(adapter);


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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MainActivity_Admin.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        db.confirmarFamiliaConCanasta(position);
        if (db.entregadasTodasLasFamilias()){
            db.reiniciarRecibidos();
        }
        startActivity(intent);
        Toast.makeText(this,"Donativo confirmado con éxito",Toast.LENGTH_SHORT).show();
    }
}