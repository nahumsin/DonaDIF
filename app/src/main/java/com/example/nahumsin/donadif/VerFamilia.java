package com.example.nahumsin.donadif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by nahumsin on 22/11/16.
 */

public class VerFamilia {
   /* private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDirec;
    private EditText editTextDesc;
    private EditText editTextImagen;
    private CheckBox checkBoxEntregado;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;
    private ConectionDB db = new ConectionDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_familia);

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

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextId.setText(id);
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

    }*/
}