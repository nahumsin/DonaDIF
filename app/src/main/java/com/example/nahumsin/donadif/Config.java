package com.example.nahumsin.donadif;

/**
 * Created by nahumsin on 19/11/16.
 */

public class Config {
    //Address of our scripts of the CRUD cuenta
    public static final String URL_ADD_CUENTA="http://192.168.0.15/phpDonaDIF/insertarCuenta.php";
    public static final String URL_GET_ALL_CUENTAS = "http://192.168.0.15/phpDonaDIF/obtenerCuentas.php";
    public static final String URL_GET_CUENTA = "http://192.168.0.15/phpDonaDIF/obtenerCuenta.php?id=";
    public static final String URL_UPDATE_CUENTA = "http://192.168.0.15/phpDonaDIF/modificarCuenta.php";
    public static final String URL_DELETE_CUENTA = "http://192.168.0.15/phpDonaDIF/eliminarCuenta.php?id=";

    //Address of our scripts of the CRUD familia
    public static final String URL_ADD_FAMILIA="http://192.168.0.15/phpDonaDIF/insertarFamilia.php";
    public static final String URL_GET_ALL_FAMILIAS = "http://192.168.0.15/phpDonaDIF/obtenerFamilias.php";
    public static final String URL_GET_FAMILIA = "http://192.168.0.15/phpDonaDIF/obtenerFamilia.php?id=";
    public static final String URL_UPDATE_FAMILIA = "http://192.168.0.15/phpDonaDIF/modificarFamilia.php";
    public static final String URL_UPDATE_ENTREGA_FAMILIA = "http://192.168.0.15/phpDonaDIF/modificarEntregadoFamilia.php";
    public static final String URL_UPDATE_ENTREGA_FAM_REST = "http://192.168.0.15/phpDonaDIF/modificarTodosEntregado.php";
    public static final String URL_DELETE_FAMILIA = "http://192.168.0.15/phpDonaDIF/eliminarFamilia.php?id=";

    //Address of our scripts of the CRUD donativo
    public static final String URL_ADD_DONATIVO="http://192.168.0.15/phpDonaDIF/insertarDonativo.php";
    public static final String URL_GET_ALL_DONATIVOS = "http://192.168.0.15/phpDonaDIF/obtenerDonativos.php";
    public static final String URL_GET_DONATIVO = "http://192.168.0.15/phpDonaDIF/obtenerDonativo.php?id=";
    public static final String URL_UPDATE_DONATIVO = "http://192.168.0.15/phpDonaDIF/modificarDonativo.php";
    public static final String URL_DELETE_DONATIVO = "http://192.168.0.15/phpDonaDIF/eliminarDonativo.php?id=";

    //Keys that will be used to send the request to php scripts cuenta
    public static final String KEY_CUEN_ID = "id_cuenta";
    public static final String KEY_CUEN_NAME = "nombre_usuario";
    public static final String KEY_CUEN_PASS = "contra_usuario";
    public static final String KEY_CUEN_EMAIL = "email";
    public static final String KEY_CUEN_PRIV = "privilegio";

    //Keys that will be used to send the request to php scripts familia
    public static final String KEY_FAM_ID = "id_familia";
    public static final String KEY_FAM_NAME = "nombre_familia";
    public static final String KEY_FAM_DIR = "direccion_familia";
    public static final String KEY_FAM_DES = "desc_familia";
    public static final String KEY_FAM_IMG = "imagen";
    public static final String KEY_FAM_ENTR = "donativo_recibido";

    //Keys that will be used to send the request to php scripts donativo
    public static final String KEY_DON_ID = "id_donativo";
    public static final String KEY_DON_ID_FAM = "id_familia";
    public static final String KEY_DON_ID_CUEN = "id_cuenta";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_CUEN_ID = "id_cuenta";
    public static final String TAG_CUEN_NAME = "nombre_usuario";
    public static final String TAG_CUEN_PASS = "contra_usuario";
    public static final String TAG_CUEN_EMAIL = "email";
    public static final String TAG_CUEN_PRIV = "privilegio";


    public static final String TAG_FAM_ID = "id_familia";
    public static final String TAG_FAM_NAME = "nombre_familia";
    public static final String TAG_FAM_DIR = "direccion_familia";
    public static final String TAG_FAM_DES = "desc_familia";
    public static final String TAG_FAM_IMG = "imagen";
    public static final String TAG_FAM_ENTR = "donativo_recibido";

    public static final String TAG_DON_ID = "id_donativo";
    public static final String TAG_DON_ID_FAM = "id_familia";
    public static final String TAG_DON_ID_CUEN = "id_cuenta";
    //id to pass with intent
    public static final String CUEN_ID = "id_cuenta";
    public static final String FAM_ID = "id_familia";
    public static final String DON_ID = "id_donativo";

    public static final String UPLOAD_URL = "http://192.168.0.15/phpDonaDIF/upload.php";
    public static final String UPLOAD_KEY = "imagen";
}