package com.example.nahumsin.donadif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {
    Button btnHacerDonativo;
    Button btnVerFamilias;
    String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnVerFamilias = (Button) findViewById(R.id.btnVerFamilias);
        id_usuario= getIntent().getStringExtra("id_usuario");
        btnVerFamilias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getBaseContext(),"Ver Familias",Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this,MostrarFamiliaDonador.class);
                startActivity(intent);
            }
        });
        btnHacerDonativo = (Button) findViewById(R.id.btnHacerDonativo);
        btnHacerDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CanastasBasicas.class);
                intent.putExtra("id_usuario",id_usuario);
                startActivity(intent);
            }
        });

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
            Intent intent = new Intent(this,Login.class);
            LoginManager.getInstance().logOut();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        if (id==R.id.action_modificaCuenta){
            Intent intent = new Intent(this,ModificarCuenta.class);
            intent.putExtra("id_usuario",id_usuario);
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