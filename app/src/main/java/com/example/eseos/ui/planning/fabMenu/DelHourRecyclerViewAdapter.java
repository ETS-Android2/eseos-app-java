package com.example.eseos.ui.planning.fabMenu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.eseos.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DelHourRecyclerViewAdapter extends RecyclerView.Adapter<DelHourRecyclerViewAdapter.DelHourRecyclerViewHolder> {

    private final DelHourActivity delHourActivity;
    private List<String> date;


    //add this as a new field

    private List<Integer> expandedPositions;


    public DelHourRecyclerViewAdapter(DelHourActivity delHourActivity) {
        this.delHourActivity = delHourActivity;
        //TODO: The following lines will be replaced

        //Instanciation des listes
        date = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            date.add(Integer.toString(i));

        }
        //In the constructor create a new ArrayList instance

        expandedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public DelHourRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sujetView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_del_hour_item, parent, false);

        return new DelHourRecyclerViewHolder(sujetView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DelHourRecyclerViewHolder holder, final int position) {
        holder.date.setText(date.get(position));
    }

    @Override
    public int getItemCount() {
        /** Doit absolument retourner le nombre d'items à afficher ET PAS UN VIEUX 0 DE SES MORTS (Nathan ce BG de la street) **/
        return date.size();
    }

    public void setDate(ArrayList<String> hour) {
        this.date = hour;
        notifyDataSetChanged();
        Log.d("DONE","HOUR DONE");
    }

    class DelHourRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox date;



        public DelHourRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.checkBoxDate);

        }
    }
}
