package com.example.eseos.ui.planning;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eseos.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlanningRecyclerViewAdapter extends RecyclerView.Adapter<PlanningRecyclerViewAdapter.PlanningRecyclerViewHolder> {

    private final com.example.eseos.ui.planning.PlanningPageFragment planningPageFragment;
    private List<String> hour;
    private List<String> nb_members;
    private List<String> state;

    //add this as a new field

    private List<Integer> expandedPositions;


    public PlanningRecyclerViewAdapter(PlanningPageFragment planningPageFragment) {
        this.planningPageFragment = planningPageFragment;
        //TODO: The following lines will be replaced

        //Instanciation des listes
        hour = new ArrayList<>();
        nb_members = new ArrayList<>();
        state = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            hour.add(Integer.toString(i));
            nb_members.add("");
            state.add("");
        }
        //In the constructor create a new ArrayList instance

        expandedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlanningRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sujetView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_planning_item, parent, false);

        return new PlanningRecyclerViewHolder(sujetView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanningRecyclerViewHolder holder, final int position) {
        holder.hour.setText(hour.get(position));
        holder.nb_members.setText(nb_members.get(position));
        holder.state.setText((state.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setHour(ArrayList<String> hour) {
        this.hour = hour;
        notifyDataSetChanged();
        Log.d("DONE","HOUR DONE");
    }

    public void setNbMembers(ArrayList<String> nb_members) {
        this.nb_members = nb_members;
        notifyDataSetChanged();
        Log.d("DONE","NBMEM DONE");
    }

    public void setState(ArrayList<String> state) {
        this.state = state;
        notifyDataSetChanged();
        Log.d("DONE","STATE DONE");
    }

    class PlanningRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView hour;
        private final TextView nb_members;
        private final TextView state;


        public PlanningRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.textViewHour);
            nb_members = itemView.findViewById(R.id.textViewNbMembers);
            state = itemView.findViewById(R.id.textViewState);
        }
    }
}
