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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private EditText editTextImagen;
    private CheckBox checkBoxEntregado;

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonImage;

    private Uri filePath;

    private String id;
    private ConectionDB db = new ConectionDB(this);

    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;
    private Bitmap bitmapRotated;
    ExifInterface exif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_familia);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.FAM_ID);

        editTextId = (EditText) findViewById(R.id.txtIdFam);
        editTextName = (EditText) findViewById(R.id.txtNombreFam);
        editTextDirec = (EditText) findViewById(R.id.txtDireccionFam);
        editTextDesc = (EditText) findViewById(R.id.txtDescFam);
        editTextImagen = (EditText) findViewById(R.id.txtImgFam);

        checkBoxEntregado = (CheckBox) findViewById(R.id.chbxDonRec);

        buttonUpdate = (Button) findViewById(R.id.btnModificar);
        buttonDelete = (Button) findViewById(R.id.btnEliminar);
        buttonImage = (Button) findViewById(R.id.btnSeleccionarImagen);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonImage.setOnClickListener(this);

        editTextId.setText(id);
        editTextId.setEnabled(false);
        mostrarFamilia();

    }
    public void mostrarFamilia(){
        Familia fam = db.getFamilia(id);
        editTextName.setText(fam.getNombre());
        editTextDirec.setText(fam.getDireccion());
        editTextDesc.setText(fam.getDescripcion());
        editTextImagen.setText(fam.getImagen());
        if(fam.getEntregado().equals("0")){
            checkBoxEntregado.setChecked(false);
        }
        else
            checkBoxEntregado.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonUpdate){
            //uploadImage();
            /*String entregado;
            if(checkBoxEntregado.isChecked())
                entregado = "1";
            else
                entregado = "0";
            Familia fam = new Familia(editTextId.getText().toString(),editTextName.getText().toString(),
                    editTextDirec.getText().toString(),editTextDesc.getText().toString(),
                    editTextImagen.getText().toString(),entregado);
            db.modificarFamilia(fam);
            Toast.makeText(this,"Familia Modificada Correctamente",Toast.LENGTH_LONG).show();
        }
        if(view == buttonDelete){
            db.eliminarFamilia(id);
            Toast.makeText(this,"Familia Eliminada",Toast.LENGTH_LONG).show();
            startActivity(new Intent(VerFamilia.this,MostrarFamilia.class));*/
        }
        if(view == buttonImage){
            showFileChooser();
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

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerFamilia.this, "Cargando...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(Config.UPLOAD_KEY, uploadImage);
                data.put(Config.KEY_FAM_ID, id);
                String result = rh.sendPostRequest(Config.UPLOAD_URL,data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        try{
            ui.execute(bitmap);
        }catch (Exception e){

        }

    }
}