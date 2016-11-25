package com.example.nahumsin.donadif;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nahumsin on 24/11/16.
 */

public class ModificarCuenta extends AppCompatActivity implements View.OnClickListener {
    private EditText textName;
    private EditText email;
    private EditText password;
    private Button update;
    private Button delete;
    private ConectionDB db;
    private Cuenta cuenta;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar_cuenta);

        Intent intent = getIntent();

        id = intent.getStringExtra("id_usuario");
        db = new ConectionDB(this);
        cuenta = db.getCuenta(id);
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
        if (view == delete) {
            verificarUsuario();
        }
    }

    public void mostrarCuenta() {

        textName.setText(cuenta.getNombreUsuario());
        email.setText(cuenta.getCorreo());
    }

    private void verificarUsuario() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ide) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                String user_text = (userInput.getText()).toString();

                                /** CHECK FOR USER'S INPUT **/
                                if (user_text.equals(cuenta.getContrasena())) {
                                    asegurarAccion();
                                } else {
                                    String message = "La contraseña introducida es incorrecta." + " \n \n" + "Por favor vuelva a intentar!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModificarCuenta.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancelar", null);
                                    builder.setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            verificarUsuario();
                                        }
                                    });
                                    builder.create().show();
                                }
                            }
                        })
                .setPositiveButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
    private void asegurarAccion() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas realizar esta acción?");

        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.eliminarCuenta(id);
                        Intent intent = new Intent(ModificarCuenta.this,Login.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
