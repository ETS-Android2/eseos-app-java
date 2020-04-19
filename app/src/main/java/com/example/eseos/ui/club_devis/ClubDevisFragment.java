package com.example.eseos.ui.club_devis;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eseos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClubDevisFragment extends Fragment {

    private ClubDevisRecyclerViewAdapter clubDevisRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_club_devis, container, false);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "T'as cru que ça faisait quelque chose mdr ?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView devisRecycler = (RecyclerView) root.findViewById(R.id.recycler_view);
        devisRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        devisRecycler.setLayoutManager(llm);
        clubDevisRecyclerViewAdapter = new ClubDevisRecyclerViewAdapter(this);
        devisRecycler.setAdapter(clubDevisRecyclerViewAdapter);

        //TODO : Modifier le mSessionManager avec la liste reçue de notre API
        //TODO Requête à l'API ESE'OS pour récupérer tous les devis


        /*Exemple boucle for
        for(int i=0;i<mSessionManager.getJuryProjectTitleSize();i++){
            devisName.add(mSessionManager.getJuryProjectTitle(i));
         */

        ArrayList<String> devisName = new ArrayList<>();
        for(int i=0;i<20;i++){
            devisName.add(Integer.toString(i));

        }
        ArrayList<String> customerName = new ArrayList<>();
        for(int i=0;i<20;i++){
            customerName.add(Integer.toString(i));
            //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
        }
        ArrayList<String> status = new ArrayList<>();
        for(int i=0;i<20;i++){
            status.add(Integer.toString(i));
            //Log.d("POSITION",mSessionManager.getJuryProjectPosition(i));      Debug
        }

        clubDevisRecyclerViewAdapter.setDevisName(devisName);
        clubDevisRecyclerViewAdapter.setCustomerName(customerName);
        clubDevisRecyclerViewAdapter.setState(status);
        Log.d("TAILLE DES PROJETS JURY",Integer.toString(devisName.size()));


        return root;
    }
}