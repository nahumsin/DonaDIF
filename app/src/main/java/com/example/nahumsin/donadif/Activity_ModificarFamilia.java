package com.example.nahumsin.donadif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_ModificarFamilia extends AppCompatActivity {
    EditText nombreMod, direccionMod, descripcionMod;
    int id;
    String nombre,direccion,descripcion;
    Button modificar;
    ConectionDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__modificar_familia);
        Bundle b = getIntent().getExtras();
        if(b != null){
            id = b.getInt("Id");
            nombre =b.getString("Nombre");
            direccion = b.getString("Direccion");
            descripcion = b.getString("Descripcion");
        }
        nombreMod =(EditText) findViewById(R.id.eT_modNombre);
        direccionMod =(EditText) findViewById(R.id.et_ModDireccion);
        descripcionMod = (EditText) findViewById(R.id.et_ModDireccion);
        modificar = (Button) findViewById(R.id.button);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        nombreMod.setText(nombre);
        direccionMod.setText(direccion);
        descripcionMod.setText(descripcion);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //modificarFamilia();
                onBackPressed();
            }
        });
    }

   /* private void modificarFamilia(){
        db.updateFamilia(new Family(nombreMod.setText(nombre), direccionMod.setText(direccion),descripcionMod.setText(descripcion)));
    }*/
}