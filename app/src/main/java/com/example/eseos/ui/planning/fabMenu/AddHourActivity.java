package com.example.eseos.ui.planning.fabMenu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.eseos.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class AddHourActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String date, hourStart, hourEnd;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBarAddHour);

        final CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                date = month + "/" + dayOfMonth + "/" + year;
            }
        });

        FloatingActionButton fabValidate = findViewById(R.id.fabValidate);
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                new AsyncAddHour().execute(date, hourStart, hourEnd);
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

    class AsyncAddHour extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String urlStart = "https://api-eseos.herokuapp.com/addHour?";
            URL url;
            JSONObject jsonObject;

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
            String ID_user = pref.getString("ID","ErrorNoID");
            ArrayList<String> hours = new ArrayList<>();

            //TODO Vérifier que hourEnd est plus grand que hourStart
            //hourEnd.compareTo(hourStart);

            hourStart += ":00";
            hourEnd += ":00";

            while (!hourStart.equals(hourEnd)) {
                hours.add(hourStart);
                if(hourStart.endsWith("00:00")) {
                    hourStart = hourStart.split(":")[0] + ":30:00";
                } else {
                    int hr = Integer.parseInt(hourStart.split(":")[0])+1;
                    hourStart = hr + ":00:00";
                }
            }

            for(String hour : hours) {
                try {

                    url = new URL(urlStart + "date="  + strings[0] + "&idUser=" + ID_user + "&hour=" + hour);
                    Log.d("PLANNING", url.toString());
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    urlConnection.setRequestProperty("Accept","application/json");
                    urlConnection.setDoOutput(true);

                    /*JSONObject jsonParam = new JSONObject();

                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();*/
                    InputStream in = urlConnection.getInputStream();
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(in, writer, "UTF-8");
                    String result = writer.toString();
                    jsonObject = new JSONObject(result);
                } catch (
                        JSONException e) {
                    e.printStackTrace(); //Pas utile pour le moment
                } catch (
                        MalformedURLException e) {
                    e.printStackTrace();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddHourActivity.this, R.string.add_hour_succes, Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
