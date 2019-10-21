package com.example.eseos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ImageView profilePicture = (ImageView) findViewById(R.id.imageViewProfilePicture);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);

        int dimenProfilePicture = (int) Math.round(pref.getInt("ScreenWidth",0)*0.3);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dimenProfilePicture, dimenProfilePicture);
        layoutParams.setMargins(30,30,30,30);
        profilePicture.setLayoutParams(layoutParams);


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
            Intent homeIntent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_devis) {
            Intent homeIntent = new Intent(ProfileActivity.this, DevisActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent homeIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_members) {
            Intent homeIntent = new Intent(ProfileActivity.this, MembersActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_apropos) {
            Intent homeIntent = new Intent(ProfileActivity.this, AProposActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_inventory) {
            Intent homeIntent = new Intent(ProfileActivity.this, InventoryActivity.class);
            startActivity(homeIntent);
            finish();
        } else if (id == R.id.nav_management) {
            Intent homeIntent = new Intent(ProfileActivity.this, ManagementActivity.class);
            startActivity(homeIntent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
