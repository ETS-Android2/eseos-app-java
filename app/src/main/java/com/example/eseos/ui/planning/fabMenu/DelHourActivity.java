package com.example.eseos.ui.planning.fabMenu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eseos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DelHourActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DelHourRecyclerViewAdapter delHourRecyclerViewAdapter;
    RecyclerView recyclerViewDelHour;
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> checked_dates = new ArrayList<>();
    ArrayList<String> hours = new ArrayList<>();
    ArrayList<String> checked_hours = new ArrayList<>();
    ProgressBar progressBarHours;
    ProgressBar progressBarValidate;

    ArrayList dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_hour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarHours = findViewById(R.id.progressBarHours);
        progressBarValidate = findViewById(R.id.progressBarDelHour);


        recyclerViewDelHour = findViewById(R.id.recycler_view_del_hour);
        recyclerViewDelHour.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerViewDelHour.setLayoutManager(llm);

        delHourRecyclerViewAdapter = new DelHourRecyclerViewAdapter(this, dataList);

        new AsyncGetPlanningMember().execute();

        /* Idée du spinner
        Spinner spinner = findViewById(R.id.spinner_del_hour);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,date);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);*/

        FloatingActionButton fabValidate = findViewById(R.id.fabValidate);
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checked_cases = delHourRecyclerViewAdapter.getCheckBoxStateArray();
                for (int i = 0; i < dates.size(); i++) {
                    if (checked_cases.get(i, false)) {
                        String[] date_element = dates.get(i).split("-");
                        String formated_date = date_element[1] + "/" + date_element[2] + "/" + date_element[0];
                        checked_dates.add(formated_date);
                        checked_hours.add(hours.get(i) + ":00");
                    }
                }
                new AsyncDelHour().execute(checked_dates, checked_hours);

                //Si un jour je me chauffe à faire des messages d'erreur
                // Snackbar.make(view, R.string.error_snackbar, Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        Log.d("DELHOUR", position + " SELECTIONNE");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    class AsyncGetPlanningMember extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarHours.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String urlStart = "https://api-eseos.herokuapp.com/getPlanningMember?idUser=";
            URL url;
            JSONArray jsonArray;

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
            String ID_user = pref.getString("ID","ErrorNoID");

            try {

                url = new URL(urlStart + ID_user);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer, "UTF-8");
                String result = writer.toString();
                jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++) {
                    dates.add(jsonArray.getJSONObject(i).getString("date_planning"));
                    hours.add(jsonArray.getJSONObject(i).getString("creneau").replaceAll("'","").substring(0,5));
                }

                delHourRecyclerViewAdapter.setDate(dates);
                delHourRecyclerViewAdapter.setHour(hours);

            } catch (
                    JSONException e) {
                e.printStackTrace();
            } catch (
                    MalformedURLException e) {
                e.printStackTrace();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBarHours.setVisibility(View.GONE);
            recyclerViewDelHour.setAdapter(delHourRecyclerViewAdapter);
        }
    }

    class AsyncDelHour extends AsyncTask<ArrayList<String>, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarValidate.setVisibility(View.VISIBLE);
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(ArrayList<String>... array_strings) {
            String urlStart = "https://api-eseos.herokuapp.com/deleteHour?";
            URL url;
            JSONObject jsonObject;
            ArrayList<String> deleted_dates = array_strings[0];
            ArrayList<String> deleted_hours = array_strings[1];
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
            String ID_user = pref.getString("ID","ErrorNoID");


            for(int i = 0; i < array_strings[0].size(); i++) {
                try {
                    url = new URL(urlStart + "date=" + deleted_dates.get(i) + "&idUser=" + ID_user + "&hour=" + deleted_hours.get(i));
                    Log.d("DELHOUR", url.toString());
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("DELETE");
                    InputStream in = urlConnection.getInputStream();
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(in, writer, "UTF-8");

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
            progressBarValidate.setVisibility(View.GONE);
            Toast.makeText(DelHourActivity.this, R.string.del_hour_succes, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
