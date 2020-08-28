package com.example.eseos.ui.planning;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.eseos.R;

import org.apache.commons.io.IOUtils;
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
        Date date = manageCalendar.nextDates(getContext().getResources().getString(R.string.closed_days))[position];

        new AsyncGetHorairesDate(getContext()).execute(date);

        return root;
    }



    class AsyncGetHorairesDate extends AsyncTask<Date, Void, Void>    {
        //TODO Finir la requête
        private String urlStart = "https://api-eseos.herokuapp.com/";
        private Context mContext;

        public AsyncGetHorairesDate (Context context){
            mContext = context;
        }

        protected void onPostExecute(Void res) {
            super.onPostExecute(null);

            progressBar.setVisibility(View.GONE);
            planningRecycler.setAdapter(planningRecyclerViewAdapter);
        }

        @Override
        public Void doInBackground(Date... dates) {
            JSONObject jsonObject;

            URL url;
            try {
                url = new URL(urlStart+"2");    //Remplacer le "2" par le mail ou le token
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

            //Instanciation des listes d'éléments à afficher
            // [Les listes sont bonnes]

            ArrayList<String> hour = new ArrayList<>();
            ArrayList<String> nb_members = new ArrayList<>();
            ArrayList<String> status = new ArrayList<>();
            ArrayList<Integer> color = new ArrayList<>();

            for(int i=0;i<25;i++){

                hour.add(Integer.toString(i)); //jsonObject.getJSONArray("hours").getString(i));


                /**
                 * Modifie la syntaxe en fonction du nombre de membres présents
                 */

                int nombre_membre = i; //jsonObject.getJSONArray("nbMembers").getInt(i);
                String nb_members_syntax;

                switch (nombre_membre) {
                    case 0:
                        nb_members_syntax = mContext.getResources().getString(R.string.any_member);
                        break;
                    case 1:
                        nb_members_syntax = "1 " + mContext.getResources().getString(R.string.member_singular);
                        break;
                    default:
                        nb_members_syntax = nombre_membre + " " + mContext.getResources().getString(R.string.member_pural);
                        break;
                }
                nb_members.add(nb_members_syntax);

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

            }

            planningRecyclerViewAdapter.setHour(hour);
            planningRecyclerViewAdapter.setNbMembers(nb_members);
            planningRecyclerViewAdapter.setState(status, color);

            return null;
        }
    }
}
