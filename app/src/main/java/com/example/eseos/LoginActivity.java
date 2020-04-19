package com.example.eseos;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonValidate = findViewById(R.id.buttonValidate);

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(v);

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

                        JSONObject result = null;
                        try {
                            result = new AsyncLoginTask().execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (!result.getString("token").equals("Erreur : votre mot de passe est erroné")) {
                                accessConfirmed();
                            } else {
                                accessDenied();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //TODO A déplacer dans le bloc conditionnel une fois le système de login terminé
                //setPermissions(mail, menu_admin, menu_member);

            }
        });
    }

    public void accessConfirmed() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("rank", "5");
        editor.apply();

        Intent homeIntent = new Intent(this, DrawerActivity.class);
        LoginActivity.this.startActivity(homeIntent);
        finish();
    }

    public void accessDenied() {
        Snackbar.make(findViewById(R.id.buttonValidate), "Mauvais identifiants.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public class AsyncLoginTask extends AsyncTask<String, Void, JSONObject> {
        private String urlStart = "https://api-eseos.herokuapp.com/login?identifiant=";

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject = new JSONObject();

            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
            String mail = pref.getString("mail", "errorNoEmail");
            String password = pref.getString("password", "ErrorNoPassword");
            Log.d("LOGIN", mail + "   " + password);
            urlStart += mail + "&hash=" + password;
            Log.d("LOGIN",urlStart);

            URL url;
            try {
                url = new URL(urlStart);
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

            return jsonObject;
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
}