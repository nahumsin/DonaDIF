package com.example.nahumsin.donadif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CrearCuenta extends AppCompatActivity {

    EditText email;
    EditText usr;
    EditText pass;
    //DBCuenta db;
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
            //obtenerCuentas();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void crearCuenta(){
        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
        db.insertarCuenta(new Cuenta(usr.getText().toString(),email.getText().toString(),pass.getText().toString(),0));
        Log.d("Done!","WriteSuccesful");
        Toast.makeText(getApplicationContext(),"Cuenta creada",Toast.LENGTH_LONG).show();

    }
    /*public void obtenerCuentas(){

        Log.d("Reading: ", "Reading all accounts..");
        List<Cuenta> cuentas = db.getCuentas();

        for (Cuenta cuenta : cuentas) {
            String log = "Nombre de usuario: " + cuenta.getNombreUsuario() + " ,Correo: " + cuenta.getCorreo() + " ,Contraseña: " + cuenta.getContrasena();
// Writing shops to log
            Log.d("Cuenta: : ", log);
        }
    }*/
}
