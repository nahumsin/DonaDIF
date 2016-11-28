package com.example.nahumsin.donadif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MostrarFamiliaDonador extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ConectionDB dbFamilia;

    ListView listaFamilia;

    private ObtenerImagenes obtImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_familia__donador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaFamilia =(ListView) findViewById(R.id.listaFam);
        listaFamilia.setOnItemClickListener(this);
        dbFamilia = new ConectionDB(this);
        getURLs();
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MostrarFamiliaDonador.this,"Descargando imágenes...","Por favor espere...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                ListaImagen customList = new ListaImagen(MostrarFamiliaDonador.this,ObtenerImagenes.imageURLs,ObtenerImagenes.bitmaps);
                listaFamilia.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    obtImg.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MostrarFamiliaDonador.this,"Cargando...","Por favor espere...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                obtImg = new ObtenerImagenes(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(Config.URL_GET_IMAGENES);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, VerFamiliaDonador.class);
        intent.putExtra(Config.BITMAP_ID,i);
        startActivity(intent);
    }

}