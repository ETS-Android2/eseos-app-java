package com.example.eseos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonValidate = findViewById(R.id.buttonValidate);

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextMail = findViewById(R.id.editTextMail);
                EditText editTextPassword = findViewById(R.id.editTextPassword);

                String mail = editTextMail.getText().toString();

                if(mail.equals("")){
                    Snackbar.make(v, "Entrez une adresse mail.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    String password = editTextPassword.getText().toString();
                    if(password.equals("")) {
                        Snackbar.make(v, "Entrez un mot de passe.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    else {
                        //TODO Requête à la base de données pour récupérer l'ID de l'utilisateur



                    }
                }

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Log.i("RANK", mail);

                editor.putString("rank", mail);
                editor.apply();


                Log.i("RANK", pref.getString("rank", "bug"));

                //TODO A déplacer dans le bloc conditionnel une fois le système de login terminé
                //setPermissions(mail, menu_admin, menu_member);
                Intent homeIntent = new Intent(LoginActivity.this, DrawerActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}