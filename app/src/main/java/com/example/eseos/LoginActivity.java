package com.example.eseos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    Activity mActivity;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Activity mActivity = this;
        final Context mContext = getApplicationContext();

        Button buttonValidate = findViewById(R.id.buttonValidate);
        progressBar = findViewById(R.id.progressBarLogin);

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(v);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                EditText editTextMail = findViewById(R.id.editTextMail);
                EditText editTextPassword = findViewById(R.id.editTextPassword);

                String mail = editTextMail.getText().toString();

                if (mail.equals("")) {
                    Snackbar.make(v, "Entrez une adresse mail.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    String password = editTextPassword.getText().toString();
                    if (password.equals("")) {
                        Snackbar.make(v, "Entrez un mot de passe.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("mail", mail);
                        editor.putString("password", password);
                        editor.apply();

                        new AsyncLoginTask(mActivity).execute();
                    }
                }
            }
        });
    }

    public void accessConfirmed() {
        new AsyncInfoUserTask(getApplicationContext()).execute();
    }

    public void accessDenied() {
        Snackbar.make(findViewById(R.id.buttonValidate), "Mauvais identifiants.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public class AsyncLoginTask extends AsyncTask<Void, Void, Void> {
        private String urlStart = "https://api-eseos.herokuapp.com/login?identifiant=";


        public AsyncLoginTask (Activity activity){
            mActivity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOGIN","AsyncLogin commencé !");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject jsonObject = new JSONObject();
            URL url;
            StringWriter writer = new StringWriter();

            /**
             * Récupération des infos nécessaires à la connexion :
             *  mail    +   password
             */
            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            String mail = pref.getString("mail", "errorNoEmail");
            String password = pref.getString("password", "ErrorNoPassword");

            urlStart += mail + "&hash=" + password; //On forme l'URL pour la requête
            Log.d("LOGIN",urlStart);                        //TEST

            try {

                url = new URL(urlStart);    //Instanciation de l'URL
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();   //Connexion
                InputStream in = urlConnection.getInputStream();    //Récupération du JSONObject
                IOUtils.copy(in, writer, "UTF-8");  //Transfert de InputStream vers StringWriter au format UTF_8
                String result = writer.toString();  //Conversion en String
                jsonObject = new JSONObject(result);    //Conversion en JSONObject
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (!jsonObject.getString("token").equals("Erreur : votre mot de passe est erroné")) {
                    //editor.putString("token",jsonObject.getString("token"));      Inutile pour le moment
                    editor.putString("ID",jsonObject.getString("id"));
                    editor.apply();
                    accessConfirmed();
                } else {
                    accessDenied();
                    Log.d("LOGIN","Un problème est survenu");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(null);
            Log.d("LOGIN","AsyncLogin terminé !");
            progressBar.setVisibility(View.GONE);
        }
    }

    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public class AsyncInfoUserTask extends AsyncTask<Void, Void, Void> {
        private String urlStart = "https://api-eseos.herokuapp.com/user?id=";

        public AsyncInfoUserTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOGIN","AsyncUserInfo commencé !");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject jsonObject = new JSONObject();

            URL url;
            try {
                url = new URL(urlStart+"2");    //TODO Remplacer le "2" par le mail ou le token
                Log.d("LOGIN",url.toString());
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer, "UTF-8");
                String result = writer.toString();
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            try {
                editor.putString("username",jsonObject.getString("prenom") + " " + jsonObject.getString("nom"));
                editor.putString("role", jsonObject.getString("rang"));
                editor.putString("rank", jsonObject.getString("rang"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            editor.putString("rank", "5"); //TODO Mode développeur. A supprimer une fois fini
            editor.apply();

            Intent homeIntent = new Intent(mContext, DrawerActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Nécessaire pour lancer une autre activité depuis l'extérieur de celle existante
            mContext.startActivity(homeIntent);
            mActivity.finish();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("LOGIN","AsyncUserInfo terminé !");
            progressBar.setVisibility(View.GONE);


        }
    }
}