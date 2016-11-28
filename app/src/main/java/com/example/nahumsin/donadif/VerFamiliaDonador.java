package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by nahumsin on 25/11/16.
 */

public class VerFamiliaDonador extends AppCompatActivity {
    private ImageView imageView;
    private EditText txtNombre;
    private EditText txtDireccion;
    private EditText txtDescripcion;
    private String famPos;
    private List<Familia> listaFam;
    private ConectionDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_familia_donador);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        db = new ConectionDB(this);
        Intent intent = getIntent();
        int i = intent.getIntExtra(Config.BITMAP_ID,0);
        listaFam = db.getFamilias();
        txtNombre = (EditText) findViewById(R.id.txtNombreFam);
        txtDireccion = (EditText) findViewById(R.id.txtDireccionVer);
        txtDescripcion = (EditText) findViewById(R.id.editText4);
        imageView = (ImageView) findViewById(R.id.imgVerFam);
        imageView.setImageBitmap(ObtenerImagenes.bitmaps[i]);
        txtNombre.setText(listaFam.get(i).getNombre());
        txtDescripcion.setText(listaFam.get(i).getDescripcion());
        txtDireccion.setText(listaFam.get(i).getDireccion());

    }
}
