package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAnadirFamilia extends AppCompatActivity {
    EditText nombre, direccion, descripcion,imagen;
    Button guardar;
    ConectionDB db;
    Familia familia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_familia);

        nombre = (EditText) findViewById(R.id.editText_Nombre);
        direccion = (EditText) findViewById(R.id.editText_direccion);
        descripcion = (EditText) findViewById(R.id.editText_descripcion);
        imagen = (EditText) findViewById(R.id.editText_descripcion);

        guardar = (Button) findViewById(R.id.button_guardarFam);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnadirFamilia();
            }
        });
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        db = new ConectionDB(this);

    }

    public void AnadirFamilia(){
        db.insertarFamilia(new Familia(nombre.getText().toString(),direccion.getText().toString(),descripcion.getText().toString(),"image.png","0"));
        Intent intent = new Intent(ActivityAnadirFamilia.this,MainActivity_Admin.class);
        startActivity(intent);

    }

}