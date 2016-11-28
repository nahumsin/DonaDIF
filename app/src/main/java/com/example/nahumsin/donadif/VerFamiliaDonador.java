package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

/**
 * Created by nahumsin on 25/11/16.
 */

public class VerFamiliaDonador extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_familia_donador);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int i = intent.getIntExtra(Config.BITMAP_ID,0);

        imageView = (ImageView) findViewById(R.id.imgVerFam);
        imageView.setImageBitmap(ObtenerImagenes.bitmaps[i]);

    }
}
