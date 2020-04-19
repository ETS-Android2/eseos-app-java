package com.example.eseos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;


public class WelcomeScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        //Création du fichier SharedPreferences pour stocker les variables globales (supprimé si l'on quitte l'application)
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //Création de l'éditeur de variables associé à ce fichier
        SharedPreferences.Editor editor = pref.edit();

        //Récupération des dimensions de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        //Implentation des dimensions de l'écran dans le fichier SharedPreferences
        editor.putInt("ScreenHeight",height);
        editor.putInt("ScreenWidth",width);

        editor.apply();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(WelcomeScreen.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }

        }, SPLASH_TIME_OUT);
    }

    public class AsyncPingTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            URL url;
            String str_url = "https://api-eseos.herokuapp.com/users";

            try {
                url = new URL(str_url);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
