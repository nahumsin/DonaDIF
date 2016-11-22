package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MostrarFamilia extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listView;
    List<Familia> listaFamilias;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    ConectionDB db = new ConectionDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_familia);
        listView = (ListView) findViewById(R.id.listViewFam);
        listView.setOnItemClickListener(this);
        listaFamilias = db.getFamilias();
        for(Familia familia: listaFamilias){
            HashMap<String,String> familias = new HashMap<>();
            familias.put(Config.TAG_FAM_ID,familia.getId());
            familias.put(Config.TAG_FAM_NAME,"Familia: "+familia.getNombre()+
                    " Direccion: "+familia.getDireccion());
            Log.i("Familias",familia.getNombre());
            list.add(familias);
        }

        ListAdapter adapter = new SimpleAdapter(
                MostrarFamilia.this,list, R.layout.list_item,
                new String[]{Config.TAG_FAM_ID,Config.TAG_FAM_NAME},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VerFamilia.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String famId = map.get(Config.TAG_FAM_ID).toString();
        intent.putExtra(Config.FAM_ID, famId);
        startActivity(intent);
    }

}