package com.example.nahumsin.donadif;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
    List<Donativo> listaDonativos;
    List<String> famEnTablaDonativo;
    ListView lista;
    ConectionDB db;
    Button btnHacerDonativo;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_familia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        canastas = Integer.parseInt(getIntent().getStringExtra("canastas"));
        lista = (ListView) findViewById(R.id.listViewLista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnHacerDonativo = (Button) findViewById(R.id.btnHacerDonativo);
        user = getIntent().getStringExtra("id_usuario");
        db = new ConectionDB(this);
        showFamilias();
    }


    void showFamilias() {
        listaDonativos = db.getDonativos();
        listaFamilia = db.getFamiliasSinDonativo();
        boolean famEnDon = false;
        famEnTablaDonativo = new ArrayList<>();

        for(Donativo don: listaDonativos)
            famEnTablaDonativo.add(don.getIdFamila()+"");


        for (Familia familia : listaFamilia) {
            for(String don:famEnTablaDonativo){
                if(familia.getId().equals(don))
                    famEnDon = true;
            }
            if(famEnDon == false)
                items.add(familia.getId() + " " + familia.getImagen() + " " + familia.getNombre());
            famEnDon = false;
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

        if (id == R.id.actionDone) {
            if (selectedItems.size() == canastas) {
                for (String items : selectedItems) {
                    String[] id_fam = items.split(" ");
                    db.crearDonativo(new Donativo(Integer.parseInt(id_fam[0]), Integer.parseInt(user)));
                    Intent intent2 = new Intent(seleccionarFamilia.this, MainActivity.class);
                    intent2.putExtra("id_usuario",user);
                    Toast.makeText(getBaseContext(), "Gracias por su donativo :) en breve recibirá un " +
                            "correo con instrucciones para entregar su donativo", Toast.LENGTH_LONG).show();
                    startActivity(intent2);
                }
            } else {
                donativosNoSeleccionados();
            }
        }
        if(id == android.R.id.home){
            Intent intent = new Intent(seleccionarFamilia.this,CanastasBasicas.class);
            intent.putExtra("id_usuario",getIntent().getStringExtra("id_usuario"));
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_crear_cuenta, menu);
        return true;
    }

    private void donativosNoSeleccionados() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(seleccionarFamilia.this);
        alertDialogBuilder.setMessage("Necesita seleccionar " + canastas + " familias" +
                ", ¿desea que DonaDIF asigne las canastas?");

        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        for (int i = 0; i < canastas; i++) {
                            String[] id_fam = items.get(i).split(" ");
                            db.crearDonativo(new Donativo(Integer.parseInt(id_fam[0]), Integer.parseInt(user)));
                        }
                        Toast.makeText(getBaseContext(), "Gracias por su donativo :) en breve recibirá un " +
                                "correo con instrucciones para entregar su donativo", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(seleccionarFamilia.this,MainActivity.class);
                        intent.putExtra("id_usuario",user);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}