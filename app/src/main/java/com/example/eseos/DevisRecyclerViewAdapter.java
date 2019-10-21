package com.example.eseos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DevisRecyclerViewAdapter extends RecyclerView.Adapter<DevisRecyclerViewAdapter.DevisRecyclerViewHolder> {

    private final DevisActivity devisActivity;
    private List<String> state;
    private List<String> customerName;
    private List<String> devisName;
    //add this as a new field

    private List<Integer> expandedPositions;



    public DevisRecyclerViewAdapter(DevisActivity devisActivity){
        this.devisActivity = devisActivity;
        //TODO: The following lines will be replaced

        //Instanciation des listes
        devisName = new ArrayList<>();
        customerName = new ArrayList<>();
        state = new ArrayList<>();
        for(int i = 0; i < 20; i++){
           devisName.add(Integer.toString(i));
           customerName.add("");
           state.add("");
        }
        //In the constructor create a new ArrayList instance

        expandedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public DevisRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View sujetView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_devis_item,parent,false);

        return new DevisRecyclerViewHolder(sujetView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DevisRecyclerViewHolder holder, final int position) {

        holder.devisName.setText(customerName.get(position));
        holder.customerName.setText(state.get(position));

        /*if(expandedPositions.contains(position)){
            holder.sujetResume.setVisibility(View.VISIBLE);
        }
        else{
            holder.sujetResume.setVisibility(View.GONE);
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(holder.sujetResume.getVisibility()==View.VISIBLE){
                    expandedPositions.remove(new Integer(position));
                    holder.sujetResume.setVisibility(View.GONE);
                }
                else{
                    expandedPositions.add(position);
                    holder.sujetResume.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return devisName.size();
    }

    public void setDevisName(List<String> devis) {
        this.devisName = devis;
        notifyDataSetChanged();
    }
    public void setCustomerName(List<String> customer) {
        this.customerName = customer;
        notifyDataSetChanged();
    }

    public void setState(List<String> state) {
        this.state = state;
        notifyDataSetChanged();
    }

    class DevisRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView devisName;
        private final TextView customerName;
        private final TextView state;


        public DevisRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            devisName = itemView.findViewById(R.id.textViewNameDevis);
            customerName = itemView.findViewById(R.id.textViewNameCustomer);
            state = itemView.findViewById(R.id.textViewState);
        }
    }
}
