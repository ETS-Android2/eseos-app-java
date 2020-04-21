//A utiliser pour les requêtes à l'API
//A moins que vous n'ayez une meilleure solution
//Solution viable, en attente de l'API de Renan

package com.example.eseos.tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<Context, Void, String> {

    private Exception exception;

    protected void onPreExecute() {
    }

    protected String doInBackground(Context ... appContext) {
        SharedPreferences pref = appContext[0].getSharedPreferences("MyPref", 0); // 0 - for private mode
        String mail = pref.getString("mail","errorNoEmail");
        String password = pref.getString("password", "ErrorNoPassword");
        // Do some validation here

        Log.i("INFO2","Bonswar");
        //http://localhost/db_Connect_V2.php
        try {
            Log.i("INFO","I'm alive");
            String url = new String("http://api-eseos.herokuapp.com/login?identifiant=" + mail + "&hash=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            Log.i("INFO","I'm fucking alive");

            try {
                Log.i("INFO","I'm still alive");
                JSONObject json = new JSONObject();
                //String pagename = jobj.getJSONObject().getString();
                //JSONParser jsonparser = new JSONParser();
                //jobj = jsonparser.makeHttpRequest(url);
                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                Log.i("INFO","Mission passed");
                JsonReader aled = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.i("INFO","Résultat : " + aled.toString());
                /*while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                    Log.i("INFO", stringBuilder.toString());
                }
                bufferedReader.close();*/
                return aled.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }

        Log.i("INFO", response);
        //responseView.setText(response);
    }
}

/*class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;

    protected void onPreExecute() {
    }

    protected String doInBackground(Void... urls) {
        String email = emailText.getText().toString();
        // Do some validation here

        try {
            URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }

        Log.i("INFO", response);
        responseView.setText(response);
    }
}
*/