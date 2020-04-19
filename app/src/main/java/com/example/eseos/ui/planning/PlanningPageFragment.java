package com.example.eseos.ui.planning;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eseos.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlanningPageFragment extends Fragment {

    public static PlanningPageFragment newInstance(int position) {

        Bundle args = new Bundle();
        PlanningPageFragment fragment = new PlanningPageFragment();
        args.putInt("PAGE_NUMBER",position);
        fragment.setArguments(args);
        return fragment;
    }

    private PlanningRecyclerViewAdapter planningRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_planning,container,false);

        //Définit la taille et la position du RecyclerView
        RecyclerView planningRecycler = root.findViewById(R.id.recycler_view_planning);
        planningRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        planningRecycler.setLayoutManager(llm);

        //Instanciation du modèle (Adapter)
        planningRecyclerViewAdapter = new PlanningRecyclerViewAdapter(this);
        planningRecycler.setAdapter(planningRecyclerViewAdapter);


        //Instanciation des listes d'éléments à afficher
        // [Les listes sont bonnes]

        ArrayList<String> hour = new ArrayList<>();
        for(int i=0;i<25;i++){

            int hr = 7 + (30*i)/60;
            String heure = Integer.toString(hr);

            String minute;

            if ((30*i)%60 == 0) {
                minute = "00";
            } else {
                minute = "30";
            }

            hour.add(heure +":"+ minute);
        }

        ArrayList<String> nb_members = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> color = new ArrayList<>();
        for(int i=0;i<25;i++){


            /**
             * Modifie la syntaxe en fonction du nombre de membres présents
             */

            //TODO getMember()
            int nombre_membre = i; //Temporaire
            String nb_members_syntax;
            switch (nombre_membre) {
                case 0:
                    nb_members_syntax = getResources().getString(R.string.any_member);
                    break;
                case 1:
                    nb_members_syntax = "1 " + getResources().getString(R.string.member_singular);
                    break;
                default:
                    nb_members_syntax = nombre_membre + " " + getResources().getString(R.string.member_pural);
                    break;
            }
            nb_members.add(nb_members_syntax);

            /**
             * Modifie l'état affiché si des membres sont présents ou non
             */

            if (nombre_membre != 0) {
                status.add(getResources().getString(R.string.state_open));
                color.add(getResources().getColor(R.color.GREEN));
            } else {
                status.add(getResources().getString(R.string.state_closed));
                color.add(getResources().getColor(R.color.RED));
            }
        }

        planningRecyclerViewAdapter.setHour(hour);
        planningRecyclerViewAdapter.setNbMembers(nb_members);
        planningRecyclerViewAdapter.setState(status, color);

        return root;
    }
}
