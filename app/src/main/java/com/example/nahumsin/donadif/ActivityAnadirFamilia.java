package com.example.nahumsin.donadif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAnadirFamilia extends AppCompatActivity {
    EditText nombre, direccion, descripcion;
    Button guardar;
    DBFamilia db;
    Family familia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_familia);

        nombre = (EditText) findViewById(R.id.editText_Nombre);
        direccion = (EditText) findViewById(R.id.editText_direccion);
        descripcion = (EditText) findViewById(R.id.editText_descripcion);
        guardar = (Button) findViewById(R.id.button_guardarFam);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnadirFamilia();
            }
        });
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }

    public void AnadirFamilia(){
        db.addFamilia(new Family(nombre.getText().toString(), direccion.getText().toString(), descripcion.getText().toString()));
        Log.d("Se añadio la familia", "WriteSuccesful");
        Toast.makeText(getApplicationContext(), "Familia creada", Toast.LENGTH_LONG).show();
    }

}
