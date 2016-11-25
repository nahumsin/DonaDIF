package com.example.nahumsin.donadif;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by jxsxs on 6/11/16.
 */

public class ConectionDB {
    private Context nContext;
    private Cuenta logedUser;
    private String emailDonador;

    public ConectionDB(Context context) {
        nContext = context;
    }

    public Cuenta getLogedUser() {
        return logedUser;
    }

    public void insertarFamilia(Familia fam, Bitmap bitmap) {
        if (!familiaExiste(fam.getDireccion())) {
            class InsertarFamilia extends AsyncTask<Void, Void, String> {
                Familia fam;
                ProgressDialog loading;

                public InsertarFamilia(Familia fam) {
                    this.fam = fam;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(nContext, "Cargando...", null, true, true);

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(nContext, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> maps = new HashMap<>();
                    maps.put(Config.KEY_FAM_NAME, fam.getNombre());
                    maps.put(Config.KEY_FAM_DIR, fam.getDireccion());
                    maps.put(Config.KEY_FAM_DES, fam.getDescripcion());
                    maps.put(Config.KEY_FAM_ENTR, fam.getEntregado());
                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.URL_ADD_FAMILIA, maps);
                    return res;
                }
            }
            InsertarFamilia ae = new InsertarFamilia(fam);
            ae.execute();

            class UploadImage extends AsyncTask<Bitmap, Void, String> {

                ProgressDialog loading;
                RequestHandler rh = new RequestHandler();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(nContext, "Cargando...", null, true, true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(nContext, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Bitmap... params) {
                    Bitmap bitmap = params[0];
                    String uploadImage = getStringImage(bitmap);

                    HashMap<String, String> data = new HashMap<>();

                    data.put(Config.UPLOAD_KEY, uploadImage);
                    data.put(Config.PATH, Config.URL_PROJECT);
                    String result = rh.sendPostRequest(Config.UPLOAD_NEW_IMG, data);
                    return result;
                }
            }
            if(bitmap!=null){
                UploadImage ui = new UploadImage();
                ui.execute(bitmap);
            }
        } else {
            Toast.makeText(nContext, "Ya existe una familia con la dirección " + fam.getDireccion().toUpperCase(), Toast.LENGTH_LONG).show();
        }


    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void insertarCuenta(Cuenta cuen) {
        if (!emailUsuarioExiste(cuen.getCorreo())) {
            class InsertarCuenta extends AsyncTask<Void, Void, String> {
                Cuenta cuen;

                public InsertarCuenta(Cuenta cuen) {
                    this.cuen = cuen;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Config.KEY_CUEN_NAME, cuen.getNombreUsuario());
                    params.put(Config.KEY_CUEN_PASS, cuen.getContrasena());
                    params.put(Config.KEY_CUEN_EMAIL, cuen.getCorreo());
                    params.put(Config.KEY_CUEN_PRIV, cuen.getPrivilegio());
                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.URL_ADD_CUENTA, params);
                    return res;
                }
            }
            InsertarCuenta ae = new InsertarCuenta(cuen);
            ae.execute();
        } else {
            Toast.makeText(nContext, cuen.getCorreo() + "Ya esta asociado a otra cuenta!", Toast.LENGTH_LONG).show();
        }
    }

    public void crearDonativo(Donativo dona) {

        class InsertarDonativo extends AsyncTask<Void, Void, String> {
            Donativo don;

            public InsertarDonativo(Donativo don) {
                this.don = don;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_DON_ID_FAM, don.getIdFamila() + "");
                params.put(Config.KEY_DON_ID_CUEN, don.getIdDonador() + "");
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_DONATIVO, params);
                return res;
            }
        }
        InsertarDonativo ae = new InsertarDonativo(dona);
        ae.execute();
    }

    public List<Familia> getFamilias() {
        class GetJSON extends AsyncTask<Object, Object, Object> {
            String JSON_STRING;
            List<Familia> listaFamilias = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);

            }

            @Override
            protected Object doInBackground(Object... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_FAMILIAS);
                JSON_STRING = s;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        Familia familia = new Familia(jo.getString(Config.TAG_FAM_ID),
                                jo.getString(Config.TAG_FAM_NAME), jo.getString(Config.TAG_FAM_DIR), jo.getString(Config.TAG_FAM_DES),
                                jo.getString(Config.TAG_FAM_IMG), jo.getString(Config.TAG_FAM_ENTR));
                        listaFamilias.add(familia);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaFamilias;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Familia>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buscarUsuario(String usuario) {
        List<Cuenta> listaCuentas = getCuentas();

        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getNombreUsuario().equals(usuario)) {
                logedUser = cuenta;
                return true;
            }
        }
        return false;
    }


    public boolean emailUsuarioExiste(String email) {
        List<Cuenta> listaCuentas = getCuentas();

        for (int i = 0; i < listaCuentas.size(); i++) {
            String obtEmail = listaCuentas.get(i).getCorreo();
            if (email.equalsIgnoreCase(obtEmail))
                return true;
        }
        return false;
    }
    public boolean  usuarioExiste(String user){
        List<Cuenta> listaCuentas = getCuentas();

        for(Cuenta cuenta:listaCuentas){
            if(cuenta.getNombreUsuario().equalsIgnoreCase(user))
                return true;
        }
        return false;
    }

    public boolean familiaExiste(String direccion) {

        List<Familia> listaFamilias = getFamilias();

        for (int i = 0; i < listaFamilias.size(); i++) {
            String obtDireccion = listaFamilias.get(i).getDireccion();
            if (direccion.equalsIgnoreCase(obtDireccion))
                return true;
        }
        return false;
    }

    public List<Donativo> getDonativos() {
        class GetJSON extends AsyncTask<Object, Object, Object> {
            String JSON_STRING;
            List<Donativo> listaDonativos = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);

            }

            @Override
            protected Object doInBackground(Object... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_DONATIVOS);
                JSON_STRING = s;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        Donativo donativo = new Donativo(Integer.parseInt(jo.getString(Config.TAG_DON_ID)), Integer.parseInt(jo.getString(Config.TAG_DON_ID_FAM)),
                                Integer.parseInt(jo.getString(Config.TAG_DON_ID_CUEN)));
                        listaDonativos.add(donativo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaDonativos;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Donativo>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Cuenta> getCuentas() {

        class GetJSON extends AsyncTask<Object, Object, Object> {
            String JSON_STRING;
            List<Cuenta> listaCuentas = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object s) {
                super.onPostExecute(s);

            }

            @Override
            protected Object doInBackground(Object... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_CUENTAS);
                JSON_STRING = s;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        Cuenta cuenta = new Cuenta(jo.getString(Config.TAG_CUEN_ID), jo.getString(Config.TAG_CUEN_NAME),
                                jo.getString(Config.TAG_CUEN_PASS), jo.getString(Config.TAG_CUEN_EMAIL), jo.getString(Config.TAG_CUEN_PRIV));
                        listaCuentas.add(cuenta);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return listaCuentas;
            }

        }
        GetJSON gj = new GetJSON();

        try {
            return (List<Cuenta>) gj.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNombreDonador(String idDonador) {
        List<Cuenta> listaCuentas = getCuentas();
        for (Cuenta cuenta : listaCuentas)
            if (cuenta.getId().equals(idDonador)) {
                emailDonador = cuenta.getCorreo();
                return cuenta.getNombreUsuario();
            }
        return null;
    }

    public String getNombreFamilia(String idFam) {
        List<Familia> listaFamilias = getFamilias();
        for (Familia familia : listaFamilias)
            if (familia.getId().equals(idFam))
                return familia.getNombre();
        return null;
    }

    public String getEmailDonador() {
        return emailDonador;
    }

    public Familia getFamilia(String id) {
        List<Familia> familias = getFamilias();
        for (Familia fam : familias)
            if (fam.getId().equals(id))
                return fam;
        return null;
    }

    public void confirmarFamiliaConCanasta(int pos) {
        List<Donativo> listaDonativos = getDonativos();
        List<Familia> listaFamilias = getFamilias();

        Donativo don = listaDonativos.get(pos);
        for (Familia fam : listaFamilias)
            if (don.getIdFamila() == Integer.parseInt(fam.getId())) {
                modificarEntregado(fam);
                eliminarDonativo(don);
            }
    }

    public void modificarEntregado(Familia fami) {

        class ActualizarFamilia extends AsyncTask<Void, Void, String> {
            Familia fam;

            public ActualizarFamilia(Familia fam) {
                this.fam = fam;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FAM_ID, fam.getId());
                hashMap.put(Config.KEY_FAM_ENTR, "1");

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_UPDATE_ENTREGA_FAMILIA, hashMap);
                return s;
            }
        }

        ActualizarFamilia ue = new ActualizarFamilia(fami);
        ue.execute();
    }

    public void modificarFamilia(Familia fami) {

        class ActualizarFamilia extends AsyncTask<Void, Void, String> {
            Familia fam;

            public ActualizarFamilia(Familia fam) {
                this.fam = fam;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FAM_ID, fam.getId());
                hashMap.put(Config.KEY_FAM_NAME, fam.getNombre());
                hashMap.put(Config.KEY_FAM_DIR, fam.getDireccion());
                hashMap.put(Config.KEY_FAM_DES, fam.getDescripcion());
                hashMap.put(Config.KEY_FAM_IMG, fam.getImagen());
                hashMap.put(Config.KEY_FAM_ENTR, fam.getEntregado());

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_UPDATE_FAMILIA, hashMap);
                return s;
            }
        }

        ActualizarFamilia ue = new ActualizarFamilia(fami);
        ue.execute();
    }

    public boolean entregadasTodasLasFamilias() {
        List<Familia> listaFamillias = getFamilias();
        for (Familia fam : listaFamillias)
            if (fam.getEntregado().equals("0"))
                return false;
        return true;
    }

    public void reiniciarRecibidos() {
        class ActualizarFamilia extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FAM_ENTR, "0");

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_UPDATE_ENTREGA_FAM_REST, hashMap);
                return s;
            }
        }

        ActualizarFamilia ue = new ActualizarFamilia();
        ue.execute();
    }

    public List<Familia> getFamiliasSinDonativo() {
        List<Familia> familiasSinDon = new ArrayList<>();
        List<Familia> familias = getFamilias();
        for (Familia fam : familias) {
            if (fam.getEntregado().equals("0"))
                familiasSinDon.add(fam);
        }
        return familiasSinDon;
    }

    public void eliminarDonativo(Donativo dona) {
        class EliminarDonativo extends AsyncTask<Void, Void, String> {
            Donativo don;

            public EliminarDonativo(Donativo don) {
                this.don = don;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_DON_ID, don.getIdDonativo() + "");
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_DELETE_DONATIVO, hashMap);
                return s;
            }
        }

        EliminarDonativo de = new EliminarDonativo(dona);
        de.execute();
    }

    public void eliminarFamilia(String id) {
        class ElimniarFamilia extends AsyncTask<Void, Void, String> {
            String id;

            public ElimniarFamilia(String id) {
                this.id = id;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FAM_ID, id);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_DELETE_FAMILIA, hashMap);
                Log.i("S", s);
                return s;
            }
        }

        ElimniarFamilia de = new ElimniarFamilia(id);
        de.execute();
    }
}