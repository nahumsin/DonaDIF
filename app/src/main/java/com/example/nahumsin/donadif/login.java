package com.example.nahumsin.donadif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nahumsin.donadif.MainActivity;
import com.example.nahumsin.donadif.R;

public class login extends AppCompatActivity {
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnLogin;
    Button btnCrearCuenta;
    Button btnConectFB;
    ConectionDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CrearCuenta.class);
                startActivity(intent);
            }
        });

        btnConectFB = (Button) findViewById(R.id.btnConectFB);
        btnConectFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Me has presionado",Toast.LENGTH_LONG).show();
                if (txtUsuario.getText().toString().equals("") || txtContrasena.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Ingrese los datos",Toast.LENGTH_LONG).show();
                }else{
                    //Toast.makeText(getBaseContext(),"entre al else",Toast.LENGTH_LONG).show();
                    if (db.buscarUsuario(txtUsuario.getText().toString())){
                        Toast.makeText(getBaseContext(),"El Usuario esta en la base de datos",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getBaseContext(),"El Usuario NO esta en la base de datos",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        db = new ConectionDB(this);
        db.abrirConexion();
        db.insertarCuenta(new Cuenta("Pedro","1234","pedro@gmail.com",0));


    }
}