package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class MainActivity_Admin extends AppCompatActivity implements View.OnClickListener {
    Button anadir, modificar,confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__admin);
        anadir = (Button) findViewById(R.id.button_añadirFam);
        modificar = (Button) findViewById(R.id.button_modificarFam);
        confirmar = (Button) findViewById(R.id.button_confirmarDon);

        anadir.setOnClickListener(this);
        modificar.setOnClickListener(this);
        confirmar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_añadirFam:
                Intent intent = new Intent(this, AnadirFamilia.class);
                startActivity(intent);
                break;
            case R.id.button_modificarFam:
                startActivity(new Intent(MainActivity_Admin.this, MostrarFamilia.class));
                break;
            case R.id.button_confirmarDon:
                Intent in = new Intent(this, ConfirmarDonativo.class);
                startActivity(in);
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sesionClose) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getBaseContext(),"Cierre Sesión",Toast.LENGTH_SHORT).show();
    }
}