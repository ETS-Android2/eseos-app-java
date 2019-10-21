package com.example.eseos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textOpeningState = findViewById(R.id.textViewOpeningState);
        //TODO Récupérer la variable dans le SharedPreferences et la remplacer

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

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
            Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_devis) {
            Intent homeIntent = new Intent(HomeActivity.this, DevisActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent homeIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_members) {
            Intent homeIntent = new Intent(HomeActivity.this, MembersActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_apropos) {
            Intent homeIntent = new Intent(HomeActivity.this, AProposActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_inventory) {
            Intent homeIntent = new Intent(HomeActivity.this, InventoryActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_management) {
            Intent homeIntent = new Intent(HomeActivity.this, ManagementActivity.class);
            startActivity(homeIntent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
