package com.example.nahumsin.donadif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    int id_usuario;
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

            }
        });
        db = new ConectionDB(this);
        db.abrirConexion();
        db.insertarCuenta(new Cuenta("Pedro","1234","pedro@gmail.com",0));


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_cuenta, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionDone) {
            if (txtUsuario.getText().toString().equals("") || txtContrasena.getText().toString().equals("")){
                Toast.makeText(getBaseContext(),"Ingrese los datos",Toast.LENGTH_LONG).show();
            }else{
                //Toast.makeText(getBaseContext(),"entre al else",Toast.LENGTH_LONG).show();
                if (db.buscarUsuario(txtUsuario.getText().toString())){
                    if (db.getContraseña_usuario().equals(txtContrasena.getText().toString())){
                        //Toast.makeText(getBaseContext(),"id_usuario: " + db.getId_usuario(),Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                        Intent intent2 = new Intent(login.this,seleccionarFamilia.class);
                        intent2.putExtra("id_usuario",id_usuario);
                        startActivity(intent1);
                    }else {
                        Toast.makeText(getBaseContext(),"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"El Usuario NO EXISTE",Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}