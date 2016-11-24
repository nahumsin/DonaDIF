package com.example.nahumsin.donadif;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.nahumsin.donadif.R.id.imageView;

public class AnadirFamilia extends AppCompatActivity {
    EditText nombre, direccion, descripcion,imagen;
    Button guardar;
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
        imagen = (EditText) findViewById(R.id.editText_descripcion);

        guardar = (Button) findViewById(R.id.button_guardarFam);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnadirFamilia();
            }
        });
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        db = new ConectionDB(this);

    }

    public void AnadirFamilia(){
        db.insertarFamilia(new Familia(nombre.getText().toString(),direccion.getText().toString(),descripcion.getText().toString(),"image.png","0"));
        Intent intent = new Intent(AnadirFamilia.this,MainActivity_Admin.class);
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
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}