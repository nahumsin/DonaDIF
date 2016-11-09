package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity_Admin extends AppCompatActivity implements View.OnClickListener {
    Button anadir, modificar,eliminar,confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__admin);
        anadir = (Button) findViewById(R.id.button_añadirFam);
        modificar = (Button) findViewById(R.id.button_modificarFam);
        confirmar = (Button) findViewById(R.id.button_confirmarDon);
        eliminar = (Button) findViewById(R.id.button_eliminarFam);

        anadir.setOnClickListener(this);
        modificar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_añadirFam:
                Intent intent = new Intent(this, ActivityAnadirFamilia.class);
                startActivity(intent);
                break;
            case R.id.button_modificarFam:
                startActivity(new Intent(MainActivity_Admin.this, ListadoModFamilia.class));
                break;
            case R.id.button_eliminarFam:
                break;
            case R.id.button_confirmarDon:
                break;
        }
    }
}