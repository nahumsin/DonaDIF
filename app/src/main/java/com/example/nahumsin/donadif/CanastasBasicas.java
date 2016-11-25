package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CanastasBasicas extends AppCompatActivity {

    EditText txtCanastas;
    int numMaxDonativo = 0;
    int canastas;
    List<Familia> listaFam;
    List<Donativo> listaDon;
    List<String> famEnTablaDonativo;
    ConectionDB db;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canastas_basicas);
        db = new ConectionDB(this);
        txtCanastas = (EditText) findViewById(R.id.txtCanastas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("id_usuario");
        listaDon = db.getDonativos();
        listaFam = db.getFamiliasSinDonativo();
        famEnTablaDonativo = new ArrayList<>();
        for(Donativo don: listaDon)
            famEnTablaDonativo.add(don.getIdFamila()+"");

        int flag = 0;
        for (Familia familia : listaFam) {
            for(String don: famEnTablaDonativo){
                if(don.equals(familia.getId()))
                    flag=1;
            }
            if(flag == 0)
                numMaxDonativo++;
            flag = 0;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_cuenta, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.actionDone) {

            if (txtCanastas.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Ingrese datos!!", Toast.LENGTH_LONG).show();
            } else {
                if (Integer.parseInt(txtCanastas.getText().toString()) <= 0) {
                    Toast.makeText(getBaseContext(), "Ingrese Número Válido", Toast.LENGTH_LONG).show();
                } else {
                    if (numMaxDonativo == 0) {
                        Toast.makeText(getBaseContext(), "En este momento no hay familias que necesiten donativo, gracias :)", Toast.LENGTH_LONG).show();
                    } else {
                        canastas = Integer.parseInt(txtCanastas.getText().toString());
                        if (canastas > numMaxDonativo)
                            Toast.makeText(getBaseContext(), "El número de canastas que desea donar ("+canastas+") excede el número de familias necesitadas ("+numMaxDonativo+")", Toast.LENGTH_LONG).show();
                        else {
                            Intent intent = new Intent(CanastasBasicas.this, SeleccionarFamilia.class);
                            intent.putExtra("id_usuario", user);
                            intent.putExtra("canastas", canastas+"");
                            startActivity(intent);
                        }
                    }
                }
            }
        }

        if(id == android.R.id.home){
            Intent intent = new Intent(CanastasBasicas.this,MainActivity.class);
            intent.putExtra("id_usuario",getIntent().getStringExtra("id_usuario"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}