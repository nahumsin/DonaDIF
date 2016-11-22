package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CanastasBasicas extends AppCompatActivity {
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

                if (txtCanastas.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Ingrese datos!!", Toast.LENGTH_LONG).show();
                } else {
                    if (Integer.parseInt(txtCanastas.getText().toString()) <= 0) {
                        Toast.makeText(getBaseContext(), "Ingrese Número Válido", Toast.LENGTH_LONG).show();
                    } else {
                        int canastas = Integer.parseInt(txtCanastas.getText().toString());
                        Intent intent = new Intent(CanastasBasicas.this, seleccionarFamilia.class);
                        int id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));
                        intent.putExtra("id_usuario", id_usuario + "");
                        intent.putExtra("canastas", canastas + "");
                        startActivity(intent);
                    }
                }

            }
        });
    }
}