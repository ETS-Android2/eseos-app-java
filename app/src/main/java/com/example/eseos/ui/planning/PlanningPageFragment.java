package com.example.eseos.ui.planning;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.eseos.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlanningPageFragment extends Fragment {

    private PlanningRecyclerViewAdapter planningRecyclerViewAdapter;
    ProgressBar progressBar;
    RecyclerView planningRecycler;


    public static PlanningPageFragment newInstance(int position) {

        Bundle args = new Bundle();
        PlanningPageFragment fragment = new PlanningPageFragment();
        args.putInt("PAGE_NUMBER",position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_planning,container,false);

        //Initialisation du loading logo
        progressBar = root.findViewById(R.id.progressBarPlanning);

        //Définit la taille et la position du RecyclerView
        planningRecycler = root.findViewById(R.id.recycler_view_planning);
        planningRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        planningRecycler.setLayoutManager(llm);

        //Instanciation du modèle (Adapter)
        planningRecyclerViewAdapter = new PlanningRecyclerViewAdapter(this);

        //On récupère les infos du jour à afficher
        int position = this.getArguments().getInt("PAGE_NUMBER");

        ManageCalendar manageCalendar = new ManageCalendar();
        String date = manageCalendar.nextDates(getContext().getResources().getString(R.string.closed_days))[position];

        new AsyncGetHorairesDate(getContext()).execute(date);

        return root;
    }



    class AsyncGetHorairesDate extends AsyncTask<String, Void, Void>    {
        //TODO Finir la requête
        private String urlStart = "https://api-eseos.herokuapp.com/getPlanningDate?date=";
        private Context mContext;

        public AsyncGetHorairesDate (Context context){
            mContext = context;
        }

        protected void onPostExecute(Void res) {
            super.onPostExecute(null);

            progressBar.setVisibility(View.GONE);
            planningRecycler.setAdapter(planningRecyclerViewAdapter);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public Void doInBackground(String... dates) {
            JSONArray jsonArray = new JSONArray();

            URL url;
            try {
                url = new URL(urlStart+dates[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer, "UTF-8");
                String result = writer.toString();
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Instanciation des listes d'éléments à afficher
            // [Les listes sont bonnes]

            ArrayList<String> hour = new ArrayList<>();
            ArrayList<String> nb_members = new ArrayList<>();
            ArrayList<String> status = new ArrayList<>();
            ArrayList<Integer> color = new ArrayList<>();

            Log.d("PLANNING",jsonArray.toString());



            for(String i : getContext().getResources().getStringArray(R.array.horaires_start)){
                String hour_DB = "";
                String nb_members_syntax = mContext.getResources().getString(R.string.any_member);
                int nombre_membre = 0;

                //Log.d("PLANNING",i + " AND " + hour_DB);
                hour.add(i); //On ajoute la première heure à la liste
                for(int j = 0; j < jsonArray.length(); j++) { //On teste tous les créneaux de la DB pour voir si l'un d'entre eux correspond à l'horaire entrée au dessus
                    try {
                        hour_DB = jsonArray.getJSONObject(j).getString("creneau").replaceAll("'", "").substring(0,5); //On récupère l'horaire de la DB
                        if (hour_DB.startsWith("0")) {
                            hour_DB = hour_DB.substring(1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("PLANNING",i + " AND " + hour_DB);
                    if (i.equals(hour_DB)) { //Si on trouve une correspondance :
                        try {
                            nombre_membre = jsonArray.getJSONObject(j).getInt("nbmembres"); //On récupère le nombre de membres présents sur ce créneau
                            jsonArray.remove(j); //On supprime le créneau une fois qu'on l'a utilisé pour limiter le nombre d'itérations dans la prochaine boucle
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /**
                         * Modifie la syntaxe en fonction du nombre de membres présents
                         */
                        //On associe la bonne grammaire en fonction du nombre de membres présents
                        if (nombre_membre == 1) {
                            nb_members_syntax = "1 " + mContext.getResources().getString(R.string.member_singular);
                        } else {
                            nb_members_syntax = nombre_membre + " " + mContext.getResources().getString(R.string.member_pural);
                        }
                    }
                }

                /**
                 * Modifie l'état affiché si des membres sont présents ou non
                 */

                if (nombre_membre != 0) {
                    status.add(mContext.getResources().getString(R.string.state_open));
                    color.add(mContext.getResources().getColor(R.color.GREEN));
                } else {
                    status.add(mContext.getResources().getString(R.string.state_closed));
                    color.add(mContext.getResources().getColor(R.color.RED));
                }

                nb_members.add(nb_members_syntax);
                Log.d("PLANNING", nb_members_syntax);
            }

            planningRecyclerViewAdapter.setHour(hour);
            planningRecyclerViewAdapter.setNbMembers(nb_members);
            planningRecyclerViewAdapter.setState(status, color);

            return null;
        }
    }
}
