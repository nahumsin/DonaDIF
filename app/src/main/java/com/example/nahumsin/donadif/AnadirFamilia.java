package com.example.nahumsin.donadif;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.nahumsin.donadif.R.id.imageView;

public class AnadirFamilia extends AppCompatActivity {
    EditText nombre, direccion, descripcion;
    ImageView imagen;
    ConectionDB db;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_familia);

        nombre = (EditText) findViewById(R.id.editText_Nombre);
        direccion = (EditText) findViewById(R.id.editText_direccion);
        descripcion = (EditText) findViewById(R.id.editText_descripcion);
        imagen = (ImageView) findViewById(R.id.imgFamilaAdd);

        imagen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        db = new ConectionDB(this);

    }

    public void AnadirFamilia() {
        db.insertarFamilia(new Familia(nombre.getText().toString(), direccion.getText().toString(), descripcion.getText().toString(), "0"), bitmap);
        Intent intent = new Intent(AnadirFamilia.this, MainActivity_Admin.class);
        startActivity(intent);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionDone) {
            if (nombre.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "El campo de Nombre es Obligatorio", Toast.LENGTH_LONG).show();
            else if (direccion.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "El campo de Dirección es Obligatorio", Toast.LENGTH_LONG).show();
            else
                AnadirFamilia();
        }
        return super.onOptionsItemSelected(item);
    }
}