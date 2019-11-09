package com.example.eseos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

                MenuItem menu_admin = (MenuItem) findViewById(R.id.menu_admin);
                MenuItem menu_member = (MenuItem) findViewById(R.id.menu_member);

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

                //TODO A déplacer dans le bloc conditionnel une fois le système de login terminé
                //setPermissions(mail, menu_admin, menu_member);
                Intent homeIntent = new Intent(LoginActivity.this, DrawerActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    private void setPermissions(String rank, MenuItem menu_admin, MenuItem menu_member) {


        Log.i("TITRE",menu_admin.getTitle().toString());

        switch (rank) {
            case "1":
                break;
            case "2":
                menu_member.setVisible(true);
                menu_admin.setVisible(false);
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                menu_member.setVisible(true);
                menu_admin.setVisible(true);
                break;
            default :
                menu_member.setVisible(false);
                menu_admin.setVisible(false);
                break;
        }
    }
}