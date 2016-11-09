package com.example.nahumsin.donadif;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnHacerDonativo;
    Button btnVerFamilias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // Intent intent = new Intent(MainActivity.this,)

        btnVerFamilias = (Button) findViewById(R.id.btnVerFamilias);
        btnVerFamilias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Ver Familias",Toast.LENGTH_LONG);
                // Intent intent = new Intent(MainActivity.this,ActivityListaFamilia_Donador.class);
                //startActivity(intent);
            }
        });
        btnHacerDonativo = (Button) findViewById(R.id.btnHacerDonativo);
        btnHacerDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CanastasBasicas.class);
                startActivity(intent);
            }
        });
    }
}