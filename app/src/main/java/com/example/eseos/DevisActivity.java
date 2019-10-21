package com.example.eseos;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class DevisActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DevisRecyclerViewAdapter devisRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(17)
            public void onClick(View view) {
                Snackbar.make(view, "Création d'un nouveau devis.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView devisRecycler = (RecyclerView)findViewById(R.id.recycler_view);
        devisRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        devisRecycler.setLayoutManager(llm);
        devisRecyclerViewAdapter = new DevisRecyclerViewAdapter(this);
        devisRecycler.setAdapter(devisRecyclerViewAdapter);

            //TODO : Modifier le mSessionManager avec la liste reçue de notre API
            //TODO Requête à l'API ESE'OS pour récupérer tous les devis


        /*Exemple boucle for
        for(int i=0;i<mSessionManager.getJuryProjectTitleSize();i++){
            devisName.add(mSessionManager.getJuryProjectTitle(i));
         */

            ArrayList<String> devisName = new ArrayList<>();
        for(int i=0;i<20;i++){
                devisName.add(Integer.toString(i));

            }
            ArrayList<String> customerName = new ArrayList<>();
        for(int i=0;i<20;i++){
                customerName.add(Integer.toString(i));
                //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
            }
            ArrayList<String> status = new ArrayList<>();
        for(int i=0;i<20;i++){
                status.add(Integer.toString(i));
                //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
            }

        devisRecyclerViewAdapter.setDevisName(devisName);
        devisRecyclerViewAdapter.setCustomerName(customerName);
        devisRecyclerViewAdapter.setState(status);
        Log.d("TAILLE DES PROJETS JURY",Integer.toString(devisName.size()));


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devis);


        RecyclerView devisRecycler = (RecyclerView)findViewById(R.id.recycler_view);
        devisRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        devisRecycler.setLayoutManager(llm);
        devisRecyclerViewAdapter = new DevisRecyclerViewAdapter(this);
        devisRecycler.setAdapter(devisRecyclerViewAdapter);

        //TODO : Modifier le mSessionManager avec la liste reçue de notre API

        /*Exemple boucle for
        for(int i=0;i<mSessionManager.getJuryProjectTitleSize();i++){
            devisName.add(mSessionManager.getJuryProjectTitle(i));
         *

        ArrayList<String> devisName = new ArrayList<>();
        for(int i=0;i<20;i++){
            devisName.add(Integer.toString(i));

        }
        ArrayList<String> customerName = new ArrayList<>();
        for(int i=0;i<20;i++){
            customerName.add(Integer.toString(i));
            //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
        }
        ArrayList<String> status = new ArrayList<>();
        for(int i=0;i<20;i++){
            status.add(Integer.toString(i));
            //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
        }

        devisRecyclerViewAdapter.setDevisName(devisName);
        devisRecyclerViewAdapter.setCustomerName(customerName);
        devisRecyclerViewAdapter.setState(status);
        Log.d("TAILLE DES PROJETS JURY",Integer.toString(devisName.size()));

    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent homeIntent = new Intent(DevisActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_devis) {
            Intent homeIntent = new Intent(DevisActivity.this, DevisActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent homeIntent = new Intent(DevisActivity.this, ProfileActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_members) {
            Intent homeIntent = new Intent(DevisActivity.this, MembersActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_apropos) {
            Intent homeIntent = new Intent(DevisActivity.this, AProposActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_inventory) {
            Intent homeIntent = new Intent(DevisActivity.this, InventoryActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_management) {
            Intent homeIntent = new Intent(DevisActivity.this, ManagementActivity.class);
            startActivity(homeIntent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
