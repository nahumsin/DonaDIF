package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CanastasBasicas extends AppCompatActivity {
    Button btnSiguiente;
    EditText txtCanastas;
    int numMaxDonativo = 0;
    List<Familia> listaFam;
    List<Donativo> listaDon;
    List<String> famEnTablaDonativo;
    ConectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canastas_basicas);
        db = new ConectionDB(this);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        txtCanastas = (EditText) findViewById(R.id.txtCanastas);
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
        Log.i("MaxDonativo",numMaxDonativo+"");
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtCanastas.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Ingrese datos!!", Toast.LENGTH_LONG).show();
                } else {
                    if (Integer.parseInt(txtCanastas.getText().toString()) <= 0) {
                        Toast.makeText(getBaseContext(), "Ingrese Número Válido", Toast.LENGTH_LONG).show();
                    } else {
                        int canastas = Integer.parseInt(txtCanastas.getText().toString());
                        if (numMaxDonativo == 0) {
                            Toast.makeText(getBaseContext(), "En este momento no hay familias que necesiten donativo, gracias :)", Toast.LENGTH_LONG).show();
                        } else {
                            if (canastas > numMaxDonativo)
                                Toast.makeText(getBaseContext(), "El número de canastas que desea donar excede el número de familias necesitadas", Toast.LENGTH_LONG).show();
                            else {
                                Intent intent = new Intent(CanastasBasicas.this, SeleccionarFamilia.class);
                                int id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));
                                intent.putExtra("id_usuario", id_usuario + "");
                                intent.putExtra("canastas", canastas + "");
                                startActivity(intent);
                            }
                        }

                    }
                }

            }
        });
    }
}