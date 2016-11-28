package com.example.nahumsin.donadif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MostrarFamilia extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listView;
    List<Familia> listaFamilias;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    ConectionDB db = new ConectionDB(this);
    private ObtenerImagenes obtImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_familia);
        listView = (ListView) findViewById(R.id.listViewFam);
        listView.setOnItemClickListener(this);
        listaFamilias = db.getFamilias();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for(Familia familia: listaFamilias){
            HashMap<String,String> familias = new HashMap<>();
            familias.put(Config.TAG_FAM_ID,familia.getId());
            familias.put(Config.TAG_FAM_NAME,"Familia: "+familia.getNombre());
            familias.put(Config.TAG_FAM_DIR,"Direccion: "+familia.getDireccion());
            list.add(familias);
        }

        ListAdapter adapter = new SimpleAdapter(
                MostrarFamilia.this,list, R.layout.list_item,
                new String[]{Config.TAG_FAM_ID,Config.TAG_FAM_NAME,Config.TAG_FAM_DIR},
                new int[]{R.id.id, R.id.name, R.id.direccion});

        listView.setAdapter(adapter);
        getURLs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VerFamilia.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String famId = map.get(Config.TAG_FAM_ID).toString();
        intent.putExtra(Config.FAM_ID, famId);
        intent.putExtra("Position",position);
        startActivity(intent);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MostrarFamilia.this,"Cargando...","Por favor espere...",true,true);
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
    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MostrarFamilia.this,"Descargando imágenes...","Por favor espere...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
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
}