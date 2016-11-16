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
    Button btnCrearCuenta;
    Button btnConectFB;
    ConectionDB db;
    int id_usuario;
    int privilegio_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        txtUsuario.requestFocus();
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
        db = new ConectionDB(this);
        db.abrirConexion();
        db.insertarCuenta(new Cuenta("Pedro","1234","pedro@gmail.com",1));
        db.cerrarConexion();
        db.abrirConexion();
        db.insertarCuenta(new Cuenta("jesus","1234","jesus@gmail.com",0));
        db.cerrarConexion();
        db.abrirConexion();
        db.insertarFamilia(new Familia("Martinez Vazquez","Guadalupe #10","Familia con 5 integrantes","vaz.png"));
        db.cerrarConexion();
        db.abrirConexion();
        db.insertarFamilia(new Familia("Gonzales Ortega","Zacatecas #14","Familia con 2 integrantes","gon.png"));
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
                       if (db.getPrivilegio_cuenta() == 0) {
                           //Toast.makeText(getBaseContext(),"id_usuario: " + db.getId_usuario(),Toast.LENGTH_LONG).show();
                           id_usuario = db.getId_usuario();
                           Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                           Intent intent2 = new Intent(login.this, seleccionarFamilia.class);
                           intent2.putExtra("id_usuario", id_usuario);
                           txtUsuario.setText("");
                           txtContrasena.setText("");
                           txtUsuario.requestFocus();
                           startActivity(intent1);
                       }else{
                           Intent intent1 = new Intent(getBaseContext(), MainActivity_Admin.class);
                           startActivity(intent1);
                       }
                    }else {
                        Toast.makeText(getBaseContext(),"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"El Usuario NO EXISTE",Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        if (id == R.id.action_salir) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}