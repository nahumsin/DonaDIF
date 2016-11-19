package com.example.nahumsin.donadif;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by nahumsin on 18/11/16.
 */

public class SigninActivityPrueba extends AsyncTask {
    private Context context;
    private int byGetOrPost = 1;

    //flag 0 means get and 1 means post.(By default it is get.)
    public SigninActivityPrueba(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            String username = (String) objects[0];
            String password = (String) objects[1];

            String link = "http://localhost/conect.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
    protected void onPostExecute(String result){

    }
}
