package com.example.nahumsin.donadif;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConfirmarDonativo extends AppCompatActivity {
    List<String> items = new ArrayList<>();
    List<String> selectedItems = new ArrayList<>();
    List<Donativo> listaDonativo;
    ListView lista;
    ConectionDB db;
    Button btnConfirmarDonativo;
    int id_don;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_donativo);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnConfirmarDonativo = (Button) findViewById(R.id.btnConfirmarDonativo);
        lista = (ListView) findViewById(R.id.listaFamilias);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        db = new ConectionDB(this);
        btnConfirmarDonativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItems.size() == 1) {
                    for (String item : selectedItems) {
                        String emailDonador = db.getEmailDonador();

                        db.confirmarFamiliaConCanasta(id_don);
                        Intent itSend = new Intent(Intent.ACTION_SEND);

                        itSend.setType("plain/text");
                        itSend.putExtra(Intent.EXTRA_EMAIL,new String[]{emailDonador});
                        itSend.putExtra(Intent.EXTRA_SUBJECT,"Donativo");
                        itSend.putExtra(Intent.EXTRA_TEXT,"Muchas gracias por su donativo");

                        if (db.entregadasTodasLasFamilias()){
                            db.reiniciarRecibidos();
                        }
                        startActivity(Intent.createChooser(itSend,"Email ..."));
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Seleccione solo 1", Toast.LENGTH_LONG).show();
                }
            }
        });

        showFamilias();
    }

    void showFamilias() {
        listaDonativo = db.getDonativos();

        for (Donativo donativo : listaDonativo) {
            if (listaDonativo.size() != 0) {
                items.add("Donador: " + db.getNombreDonador(donativo.getIdDonador()+"") + " Familia: " + db.getNombreFamilia(donativo.getIdFamila()+""));
            }else{
                items.add("No hay donativos!!");
            }
        }
        final ArrayAdapter adaptador = new ArrayAdapter<String>(this, R.layout.rowlayout, items);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView) view).getText().toString();
                id_don = (int )adapterView.getItemIdAtPosition(i);

                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove((selectedItem));
                } else {
                    selectedItems.add(selectedItem);
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_cuenta, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.actionDone) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


