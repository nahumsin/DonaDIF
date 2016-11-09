package com.example.nahumsin.donadif;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CanastasBasicas extends AppCompatActivity{
    Button btnSiguiente;
    EditText txtCanastas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canastas_basicas);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        txtCanastas = (EditText) findViewById(R.id.txtCanastas);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int canastas = Integer.parseInt(txtCanastas.getText().toString());
                Intent intent = new Intent(CanastasBasicas.this,seleccionarFamilia.class);
                intent.putExtra("canastas",canastas + "");
                startActivity(intent);

            }
        });
    }
}