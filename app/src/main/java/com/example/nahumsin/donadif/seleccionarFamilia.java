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
    ListView lista;
    ConectionDB db;
    Button btnHacerDonativo;
    int ids_familias [] = new int[20];
    int id_usuario = 0;

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
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
                id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));

                if (selectedItems.size() == canastas) {
                    for (String item : selectedItems) {
                        String[] nombre = item.split(" ");
                        //Toast.makeText(getBaseContext(),"nombre: " + nombre[1] + " " + nombre[2] + "\n",Toast.LENGTH_LONG).show();
                        if (db.buscarFamilia(nombre[1] + " " + nombre[2])) {
                            if (db.getId_familia() != 0) {
                                db.crearDonativo(new Donativo(db.getId_familia(), id_usuario, 0));
                                Intent intent2 = new Intent(seleccionarFamilia.this, PruebasDataBase.class);
                                startActivity(intent2);
                                //Toast.makeText(getBaseContext(), "Donativo Realizado!!", Toast.LENGTH_LONG).show();
                                //Toast.makeText(getBaseContext(), "ID_FAM: " + db.getId_familia(), Toast.LENGTH_LONG).show();

                               // Toast.makeText(getBaseContext(), "id_usuario: " + id_usuario + " \nid_fam: " + db.getId_familia() + "\n", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Tiene que seleccionar " + canastas + " familias", Toast.LENGTH_LONG).show();
                }
            }
        });

        db = new ConectionDB(this);
        db.abrirConexion();


        //db.insertarFamilia(new Familia("Gonzales Ortega","Zacatecas #14","Familia con 2 integrantes","gon.png"));
        showFamilias();
    }


    void showFamilias(){
        listaFamilia = db.getFamilias();

        for (Familia familia: listaFamilia) {
            items.add(familia.getImagen() + " " + familia.getNombre());
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,R.layout.rowlayout,items);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem)){
                    selectedItems.remove((selectedItem));
                }
                else {
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