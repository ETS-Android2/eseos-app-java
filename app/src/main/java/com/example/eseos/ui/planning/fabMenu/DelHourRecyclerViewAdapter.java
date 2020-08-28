package com.example.eseos.ui.planning.fabMenu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DelHourRecyclerViewAdapter extends RecyclerView.Adapter<DelHourRecyclerViewAdapter.DelHourRecyclerViewHolder> {

    private final DelHourActivity delHourActivity;
    private List<String> date;
    private List<String> hoursStart;
    private List<String> hoursEnd;

    //add this as a new field

    private List<Integer> expandedPositions;
    ArrayList dataList = new ArrayList<>();

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    public DelHourRecyclerViewAdapter(DelHourActivity delHourActivity, ArrayList dataList) {
        this.delHourActivity = delHourActivity;
        this.dataList = dataList;

        //TODO: The following lines will be replaced

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
        holder.hourStart.setText(hoursStart.get(position));
        holder.hourEnd.setText(hoursEnd.get(position));

        if(!checkBoxStateArray.get(position,false)) {
            //checkbox unchecked
            holder.date.setChecked(false);
        } else {
            //checkbox checked.
            holder.date.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        /** Doit absolument retourner le nombre d'items à afficher ET PAS UN VIEUX 0 DE SES MORTS (Nathan ce BG de la street) **/
        return date.size();
    }

    public void setDate(ArrayList<String> dates) {
        this.date = dates;
        notifyDataSetChanged();
        Log.d("DONE","HOUR DONE");
    }

    public void setHour(ArrayList<String> hours) {
        ArrayList hourEnd = new ArrayList();
        String res;
        for (String i : hours) {
            if(i.endsWith(":00")) {
                res = i.split(":")[0] + ":30";
            } else {
                int hr = Integer.parseInt(i.split(":")[0])+1;
                res = hr + ":00";
            }
            hourEnd.add(res);
        }

        this.hoursStart = hours;
        this.hoursEnd = hourEnd;
    }

    public SparseBooleanArray getCheckBoxStateArray() {
        return checkBoxStateArray;
    }

    class DelHourRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox date;
        private final TextView hourStart;
        private final TextView hourEnd;

        public DelHourRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.checkBoxDate);
            hourStart = itemView.findViewById(R.id.hour_start);
            hourEnd = itemView.findViewById(R.id.hour_end);

            date.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //getAdapterPosition returns clicked item position
                    int position = getAdapterPosition();
                    if(!checkBoxStateArray.get(position,false))
                    {
                        //checkbox checked
                        date.setChecked(true);
                        //checkbox state stored.
                        checkBoxStateArray.put(position,true);
                    }
                    else
                    {
                        //checkbox unchecked.
                        date.setChecked(false);
                        //checkbox state stored
                        checkBoxStateArray.put(position,false);
                    }
                    Log.d("DELHOUR", checkBoxStateArray.toString());
                }
            });
        }
    }
}
