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
            int heure = 7 + (30*i)/60;
            int minute = (30*i)%60;
            hour.add(heure +":"+ minute);
        }

        ArrayList<String> nb_members = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        for(int i=0;i<25;i++){
            //TODO getMember()
            nb_members.add(i + " membres");
            int nombre_membre = 0;
            if (nombre_membre != 0) {
                status.add("Ouvert");
            } else {
                status.add("Fermé");
            }
        }

        planningRecyclerViewAdapter.setHour(hour);
        planningRecyclerViewAdapter.setNbMembers(nb_members);
        planningRecyclerViewAdapter.setState(status);

        return root;
    }
}
