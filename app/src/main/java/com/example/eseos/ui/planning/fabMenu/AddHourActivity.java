package com.example.eseos.ui.planning.fabMenu;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.eseos.R;

import java.util.ArrayList;

public class AddHourActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String hourStart, hourEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner start_spinner = findViewById(R.id.spinnerHourStart);
        Spinner end_spinner = findViewById(R.id.spinnerHourEnd);
        start_spinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapterStart = ArrayAdapter.createFromResource(this,
                R.array.horaires_start, R.layout.spinner_hour_item);
        arrayAdapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        start_spinner.setAdapter(arrayAdapterStart);

        end_spinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapterEnd = ArrayAdapter.createFromResource(this,
                R.array.horaires_end, R.layout.spinner_hour_item);
        arrayAdapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        end_spinner.setAdapter(arrayAdapterEnd);

        FloatingActionButton fabValidate = findViewById(R.id.fabValidate);
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Get infos entrées

                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(parent.getId() == R.id.spinnerHourStart) {
            hourStart = parent.getItemAtPosition(position).toString();
        } else {
            hourEnd = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
