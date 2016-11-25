package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by nahumsin on 24/11/16.
 */

public class ModificarCuenta extends AppCompatActivity implements View.OnClickListener{
    private EditText textName;
    private EditText email;
    private EditText password;
    private Button update;
    private Button delete;
    private ConectionDB db;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar_cuenta);

        Intent intent = getIntent();

        id = intent.getStringExtra("id_usuario");
        db = new ConectionDB(this);

        textName = (EditText) findViewById(R.id.txtUsuario);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPass);

        update = (Button) findViewById(R.id.btnModificar);
        delete = (Button) findViewById(R.id.btnEliminar);

        update.setOnClickListener(this);
        delete.setOnClickListener(this);

        mostrarCuenta();
    }
    @Override
    public void onClick(View view) {
        if(view == delete){
            db.eliminarCuenta(id);
            Intent intent = new Intent(ModificarCuenta.this,Login.class);
            startActivity(intent);
        }
    }

    public void mostrarCuenta(){
        Cuenta cuenta = db.getCuenta(id);
        textName.setText(cuenta.getNombreUsuario());
        email.setText(cuenta.getCorreo());
    }
}
