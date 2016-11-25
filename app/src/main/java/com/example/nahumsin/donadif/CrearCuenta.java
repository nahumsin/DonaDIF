package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CrearCuenta extends AppCompatActivity {

    EditText email;
    EditText usr;
    EditText pass;
    EditText confPass;
    ConectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        db= new ConectionDB(this);

        email = (EditText)  findViewById(R.id.emailTxt);
        usr = (EditText) findViewById(R.id.usrTxt);
        pass = (EditText) findViewById(R.id.pswdTxt);
        confPass = (EditText)findViewById(R.id.confPswdTxt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
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
            crearCuenta();
            return true;
        }

        if(id == android.R.id.home){
            startActivity(new Intent(CrearCuenta.this,Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(CrearCuenta.this,Login.class));
    }
    public void crearCuenta(){

       if (!email.getText().toString().contains("@")){
           Toast.makeText(getApplicationContext(),"Ingrese un correo valido!!",Toast.LENGTH_LONG).show();
       } else {
            if (!pass.getText().toString().equals(confPass.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Las Contraseñas no coinciden!!", Toast.LENGTH_LONG).show();
            } else {
                db.insertarCuenta(new Cuenta(usr.getText().toString(), pass.getText().toString(), email.getText().toString(), "0"));
                Intent intent = new Intent(CrearCuenta.this, Login.class);
                startActivity(intent);
            }
        }
    }

}
