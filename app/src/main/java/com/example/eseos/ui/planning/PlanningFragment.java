package com.example.eseos.ui.planning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.eseos.R;
import com.example.eseos.ui.planning.fabMenu.AddHourActivity;
import com.example.eseos.ui.planning.fabMenu.DelHourActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class PlanningFragment extends Fragment {

    private PlanningRecyclerViewAdapter planningRecyclerViewAdapter;

    public static PlanningFragment newInstance() {
        return new PlanningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final FloatingActionButton fab, fabAdd, fabDel;
        final Animation fab_open_add, fab_close_add, fab_open_del, fab_close_del;
        Calendar calendar;

        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_planning, container, false);
/**
 *
 * RECUPERATION DES PERMANENCES
 *
 */

        calendar = Calendar.getInstance();
        Log.d("CALENDAR",calendar.getTime().toString().split(" ")[0]);

        JSONObject result = null;

        /*try {
            result = new AsyncPlanningTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/



/**
 *
 * MENU DEROULANT
 *
 */
        final boolean[] isFABOpen = new boolean[1];

        fab = root.findViewById(R.id.fab);
        fabAdd = root.findViewById(R.id.fabAdd);
        fabDel = root.findViewById(R.id.fabDel);

        fab_open_add = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open_add);
        fab_open_del = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open_del);
        fab_close_add = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_add);
        fab_close_del = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_del);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen[0]){
                    showFABMenu();
                }else{
                    closeFABMenu();
            }
        }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }

            private void showFABMenu() {
                isFABOpen[0]=true;

                fabAdd.startAnimation(fab_open_add);
                fabDel.startAnimation(fab_open_del);

                fabAdd.setClickable(true);
                fabDel.setClickable(true);
                fabAdd.animate().translationY(-350);
                fabDel.animate().translationY(-180);
            }


            });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();

                Intent homeIntent = new Intent(getContext(), AddHourActivity.class);
                startActivity(homeIntent);

            }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }
        });

        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();

                Intent homeIntent = new Intent(getContext(), DelHourActivity.class);
                startActivity(homeIntent);
            }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }
        });

/**
 *
 * INITIALISATION DU FRAGMENT
 *
 */

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }

    public class AsyncPlanningTask extends AsyncTask<Date, Void, JSONObject> {
        private String urlStart = "https://api-eseos.herokuapp.com/planning?date=";

        @Override
        protected JSONObject doInBackground(Date... dates) {
            JSONObject jsonObject = new JSONObject();
            urlStart += dates[0].toString();

            URL url;
            try {
                url = new URL(urlStart);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer, "UTF-8");
                String result = writer.toString();
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

    }

}
