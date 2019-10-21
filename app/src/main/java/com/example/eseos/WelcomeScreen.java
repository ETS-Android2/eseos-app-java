package com.example.eseos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;


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

        editor.commit();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(WelcomeScreen.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }

        }, SPLASH_TIME_OUT);
    }
}
