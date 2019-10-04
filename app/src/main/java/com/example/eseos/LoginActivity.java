package com.example.eseos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonValidate = (Button) findViewById(R.id.buttonValidate);

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextMail = (EditText) findViewById(R.id.editTextMail);
                EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

                String mail = editTextMail.getText().toString();

                if(mail.equals("")){
                    //Message d'erreur : entrez une adresse mail
                }
                else {
                    String password = editTextPassword.getText().toString();
                    if(password.equals("")) {
                        //Message d'erreur : entrez un mot de passe
                    }

                    else {
                        //Requête à la base de données pour récupérer l'ID de l'utilisateur
                    }
                }

                //A déplacer dans le bloc conditionnel une fois le système de login terminé
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}