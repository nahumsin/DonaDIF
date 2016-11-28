package com.example.nahumsin.donadif;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by nahumsin on 22/11/16.
 */

public class VerFamilia extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDirec;
    private EditText editTextDesc;

    private ImageView imagen;
    private CheckBox checkBoxEntregado;

    private Button buttonUpdate;
    private Button buttonDelete;

    private Uri filePath;

    private String id;
    private ConectionDB db = new ConectionDB(this);

    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;
    private int position;
    private Bitmap bitmapRotated;
    ExifInterface exif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_familia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        id = intent.getStringExtra(Config.FAM_ID);
        position = intent.getIntExtra("Position",0);
        editTextId = (EditText) findViewById(R.id.txtIdFam);
        editTextName = (EditText) findViewById(R.id.txtNombreFam);
        editTextDirec = (EditText) findViewById(R.id.txtDireccionFam);
        editTextDesc = (EditText) findViewById(R.id.txtDescFam);

        imagen = (ImageView) findViewById(R.id.imgModFam);

        checkBoxEntregado = (CheckBox) findViewById(R.id.chbxDonRec);

        buttonUpdate = (Button) findViewById(R.id.btnModificar);
        buttonDelete = (Button) findViewById(R.id.btnEliminar);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        imagen.setOnClickListener(this);

        editTextId.setText(id);
        editTextId.setEnabled(false);
        imagen.setImageBitmap(ObtenerImagenes.bitmaps[position]);
        mostrarFamilia();

    }
    public void mostrarFamilia(){
        Familia fam = db.getFamilia(id);
        editTextName.setText(fam.getNombre());
        editTextDirec.setText(fam.getDireccion());
        editTextDesc.setText(fam.getDescripcion());
        if(fam.getEntregado().equals("0")){
            checkBoxEntregado.setChecked(false);
        }
        else
            checkBoxEntregado.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonUpdate){
            String entregado;
            if(checkBoxEntregado.isChecked())
                entregado = "1";
            else
                entregado = "0";
            Familia fam = new Familia(editTextId.getText().toString(),editTextName.getText().toString(),
                    editTextDirec.getText().toString(),editTextDesc.getText().toString(),"",entregado);
            db.modificarFamilia(fam,bitmap);
        }
        if(view == imagen){
            showFileChooser();
        }
        if(view == buttonDelete){
            db.eliminarFamilia(id);
            startActivity(new Intent(VerFamilia.this,MainActivity_Admin.class));
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una foto"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {
                float degrees = 90;//rotation degree
                Matrix matrix = new Matrix();
                matrix.setRotate(degrees);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //bitmapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
}