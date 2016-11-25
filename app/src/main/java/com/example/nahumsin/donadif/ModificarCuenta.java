package com.example.nahumsin.donadif;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nahumsin on 24/11/16.
 */

public class ModificarCuenta extends AppCompatActivity implements View.OnClickListener {
    private EditText textName;
    private EditText email;
    private Button update;
    private Button delete;
    private Button modpass;
    private ConectionDB db;
    private Cuenta cuenta;
    private String nuevoPass;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar_cuenta);

        Intent intent = getIntent();

        id = intent.getStringExtra("id_usuario");
        db = new ConectionDB(this);
        cuenta = db.getCuenta(id);
        Toast.makeText(this, cuenta.getId(), Toast.LENGTH_SHORT).show();
        textName = (EditText) findViewById(R.id.txtUsuario);
        email = (EditText) findViewById(R.id.txtEmail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        update = (Button) findViewById(R.id.btnModificar);
        delete = (Button) findViewById(R.id.btnEliminar);
        modpass = (Button) findViewById(R.id.btnModPass);

        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        modpass.setOnClickListener(this);

        mostrarCuenta();

    }

    @Override
    public void onClick(View view) {
        if (view == delete) {
            verificarUsuario("eliminar");
        }
        if (view == update) {
            if (textName.getText().toString().equals("") || email.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show();
            } else if (textName.getText().toString().equalsIgnoreCase(cuenta.getNombreUsuario()) &&
                    email.getText().toString().equalsIgnoreCase(cuenta.getCorreo())) {
            } else if (textName.getText().toString().equalsIgnoreCase(cuenta.getNombreUsuario()) && !email.getText().toString().equalsIgnoreCase(cuenta.getCorreo())) {
                if (!db.isValidEmail(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                } else if (db.emailUsuarioExiste(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Este e-mail ya está asociado a una cuenta", Toast.LENGTH_SHORT).show();
                } else {
                    verificarUsuario("modificar");
                }
            } else if (email.getText().toString().equalsIgnoreCase(cuenta.getCorreo()) && !textName.getText().toString().equalsIgnoreCase(cuenta.getNombreUsuario())) {
                if (db.usuarioExiste(textName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Este nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show();
                else
                    verificarUsuario("modificar");
            } else
                verificarUsuario("modificar");
        }

        if (view == modpass){
            verificarUsuario("modpass");
        }

    }


    public void mostrarCuenta() {

        textName.setText(cuenta.getNombreUsuario());
        email.setText(cuenta.getCorreo());
    }

    private void verificarUsuario(final String accion) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ide) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                String user_text = (userInput.getText()).toString();

                                /** CHECK FOR USER'S INPUT **/
                                if (user_text.equals(cuenta.getContrasena())) {
                                    if(accion.equals("modpass")){
                                        modificarPassw();
                                    }
                                    else
                                        asegurarAccion(accion);
                                } else {
                                    String message = "La contraseña introducida es incorrecta." + " \n \n" + "Por favor vuelva a intentar!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModificarCuenta.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancelar", null);
                                    builder.setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            verificarUsuario(accion);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                    Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                    b.setTextColor(getResources().getColor(R.color.colorAccent));
                                    a.setTextColor(getResources().getColor(R.color.colorAccent));
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

        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b.setTextColor(getResources().getColor(R.color.colorAccent));
        a.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void asegurarAccion(final String accion) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas realizar esta acción?");

        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (accion.equals("eliminar")) {
                            eliminarCuenta();
                        } else if (accion.equals("modificar")) {
                            modificarCuenta();
                        }
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
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b.setTextColor(getResources().getColor(R.color.colorAccent));
        a.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void eliminarCuenta() {
        db.eliminarCuenta(id);
        Intent intent = new Intent(ModificarCuenta.this, Login.class);
        startActivity(intent);
    }

    public void modificarCuenta() {
        cuenta.setNombreUsuario(textName.getText().toString());
        cuenta.setCorreo(email.getText().toString());
        db.modificarCuenta(cuenta);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void modificarPassw(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setView(promptsView);

        TextView text = (TextView) promptsView.findViewById(R.id.textDialog);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        text.setText("Nueva contraseña:");
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ide) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                String user_text = (userInput.getText()).toString();

                                /** CHECK FOR USER'S INPUT **/
                                if (userInput.getText().toString().length()<5||userInput.getText().toString().length()>13) {
                                    String message = "La contraseña debe tener una longitud mayor a 4 y menor a 14 caracteres." + " \n \n" + "Por favor vuelva a intentar!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModificarCuenta.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancelar", null);
                                    builder.setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            modificarPassw();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                    Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                    b.setTextColor(getResources().getColor(R.color.colorAccent));
                                    a.setTextColor(getResources().getColor(R.color.colorAccent));
                                }
                                else{
                                    nuevoPass = userInput.getText().toString();
                                    confirmPass();
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
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b.setTextColor(getResources().getColor(R.color.colorAccent));
        a.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void confirmPass(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ModificarCuenta.this);
        alertDialogBuilder.setView(promptsView);

        TextView text = (TextView) promptsView.findViewById(R.id.textDialog);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        text.setText("Confirmar contraseña:");
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ide) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                String user_text = (userInput.getText()).toString();

                                /** CHECK FOR USER'S INPUT **/
                                if (userInput.getText().toString().equals(nuevoPass)) {
                                    cuenta.setContrasena(nuevoPass);
                                    db.modificarCuenta(cuenta);
                                }
                                else {
                                    String message = "Las contraseñas no coinciden." + " \n \n" + "Por favor vuelva a intentar!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModificarCuenta.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancelar", null);
                                    builder.setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            modificarPassw();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                    Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                    b.setTextColor(getResources().getColor(R.color.colorAccent));
                                    a.setTextColor(getResources().getColor(R.color.colorAccent));
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
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b.setTextColor(getResources().getColor(R.color.colorAccent));
        a.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}
