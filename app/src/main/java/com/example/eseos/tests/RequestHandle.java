package com.example.eseos.tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/* INUTILE */

public class RequestHandle extends AsyncTask<String, Void, JSONObject> {

    private Context activityContext;
    private String urlStart = "https://api-eseos.herokuapp.com/login?identifiant=";
    private String mail;
    private String password;

    public RequestHandle(Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject jsonObject = new JSONObject();

        SharedPreferences pref = activityContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
        mail = pref.getString("mail", "errorNoEmail");
        password = pref.getString("password", "ErrorNoPassword");
        Log.d("LOGIN",mail+"   "+password);
        urlStart += mail + "&hash=" + password;

        URL url = null;
        try {
            url = new URL(urlStart);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            String result = writer.toString();
            jsonObject = new JSONObject(result);
        } catch (JSONException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            Log.d("LOGIN", jsonObject.getString("token"));
            /*if (!jsonObject.getString("token").equals("Erreur : votre mot de passe est erroné")) {
                LoginActivity test = new LoginActivity();
                test.accessConfirmed();
            } else {
                Toast.makeText(activityContext, "Erreur, mauvais identifiant ou mot de passe", Toast.LENGTH_SHORT).show();
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}