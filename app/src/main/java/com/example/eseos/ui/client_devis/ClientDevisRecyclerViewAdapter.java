package com.example.eseos.ui.client_devis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eseos.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ClientDevisRecyclerViewAdapter extends RecyclerView.Adapter<ClientDevisRecyclerViewAdapter.ClientDevisRecyclerViewHolder> {

    private final ClientDevisFragment clientDevisFragment;
    private List<String> state;
    private List<String> customerName;
    private List<String> devisName;
    //add this as a new field

    private List<Integer> expandedPositions;



    public ClientDevisRecyclerViewAdapter(ClientDevisFragment clientDevisFragment){
        this.clientDevisFragment = clientDevisFragment;

        //Instanciation des listes
        devisName = new ArrayList<>();
        customerName = new ArrayList<>();
        state = new ArrayList<>();
        for(int i = 0; i < 6; i++){
           devisName.add(Integer.toString(i));
           customerName.add("");
           state.add("");
        }
        //In the constructor create a new ArrayList instance

        expandedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClientDevisRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View sujetView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_devis_item,parent,false);

        return new ClientDevisRecyclerViewHolder(sujetView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClientDevisRecyclerViewHolder holder, final int position) {

        holder.devisName.setText(devisName.get(position));
        holder.customerName.setText(customerName.get(position));
        holder.state.setText((state.get(position)));

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

    class ClientDevisRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView devisName;
        private final TextView customerName;
        private final TextView state;


        public ClientDevisRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            devisName = itemView.findViewById(R.id.textViewNameDevis);
            customerName = itemView.findViewById(R.id.textViewNameCustomer);
            state = itemView.findViewById(R.id.textViewState);
        }
    }
}
