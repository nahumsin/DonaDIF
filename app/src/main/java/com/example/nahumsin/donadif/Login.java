package com.example.nahumsin.donadif;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnCrearCuenta;
    TextView email;
    LoginButton btnConectFB;
    ConectionDB db;
    int id_usuario;
    CallbackManager callbackManager;
    String nombreUsuariFace, emailFace;
    List<Cuenta> listaCuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        txtUsuario.requestFocus();
        txtContrasena.setText("");
        txtUsuario.setText("");
        db = new ConectionDB(this);

        //========FACEBOOK=============================

        callbackManager = CallbackManager.Factory.create();
        btnConectFB = (LoginButton) findViewById(R.id.btnConectFB);
        btnConectFB.setReadPermissions("email");

        btnConectFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFacebookProfileDetails(loginResult.getAccessToken());
                //id_usuario = Integer.parseInt(db.getLogedUser().getId());
                Intent intent = new Intent(Login.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               // Toast.makeText(getBaseContext(), "id: " + id_usuario, Toast.LENGTH_LONG).show();

                intent.putExtra("id_usuario", getId_usuario() + "");
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //=============================================

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CrearCuenta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }

    //=========================Métodos de FACEBOOK=========================================
    private void getFacebookProfileDetails(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //object retorna lo indicado en paramters.putString("fields", "email") en este caso, solo contiene el email

            public void onCompleted(final JSONObject object, GraphResponse response) {
                try {
                    //Profile clase que contiene las características báscias de la cuenta de facebook (No retorna email)
                    Profile profileDefault = Profile.getCurrentProfile();
                    nombreUsuariFace = "" + profileDefault.getFirstName();
                    emailFace = object.getString("email");
                    if (!db.emailUsuarioExiste(emailFace)){
                        db.insertarCuenta(new Cuenta(nombreUsuariFace," ",emailFace,"0"));
                        listaCuentas =db.getCuentas();
                        for (Cuenta cuenta:listaCuentas) {
                            if (cuenta.getCorreo().equals(emailFace)) {
                                setId_usuario( Integer.parseInt(cuenta.getId()));
                                //Toast.makeText(getBaseContext(), "id: " + id_usuario, Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        listaCuentas =db.getCuentas();
                        for (Cuenta cuenta:listaCuentas) {
                            if (cuenta.getCorreo().equals(emailFace)) {
                                setId_usuario( Integer.parseInt(cuenta.getId()));
                                //Toast.makeText(getBaseContext(), "id: " + id_usuario, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                   // Toast.makeText(getBaseContext(), "User: " + nombreUsuariFace + "\nEmail: " + emailFace, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("E-MainActivity", "getFaceBook" + e.toString());
                }
            }
        });
        Bundle parameters = new Bundle();
        //solicitando el campo email
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();

    }
    //========================================================================================0

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            if (txtUsuario.getText().toString().equals("") || txtContrasena.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Ingrese los datos", Toast.LENGTH_LONG).show();
            } else {
                if (db.buscarUsuario(txtUsuario.getText().toString())) {
                    if (db.getLogedUser().getContrasena().equals(txtContrasena.getText().toString())) {
                        if (db.getLogedUser().getPrivilegio().equals("0")) {
                            id_usuario = Integer.parseInt(db.getLogedUser().getId());
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("id_usuario", id_usuario + "");
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent1 = new Intent(getBaseContext(), MainActivity_Admin.class);
                            startActivity(intent1);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "El Usuario NO EXISTE", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }
}