package com.example.schedulerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.R;
import com.example.schedulerapp.UpdatedeleteAdoration;

import java.util.List;

public class AdorationAdapter extends RecyclerView.Adapter<AdorationAdapter.ViewHolder> {
    private Context mContext;
    private List<Adoration_method> adorationdatalist;
    public AdorationAdapter(Context mContext, List<Adoration_method> adorationdatalist) {
        this.mContext=mContext;
        this.adorationdatalist = adorationdatalist;}

        @NonNull
    @Override
    public AdorationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adorationlayout,parent,false);
            return new AdorationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdorationAdapter.ViewHolder holder, final int position) {
        final Adoration_method Adoration_method =adorationdatalist.get(position);
        holder.ADdate.setText(Adoration_method.getAdorationdate());
        holder.ADstarttime.setText(Adoration_method.getAdorationstarttime());
        holder.ADvenue.setText(Adoration_method.getAdorationvenue());
        holder.ADcelebrant.setText(Adoration_method.getAdorationcelebrant());



    }

    @Override
    public int getItemCount() {
        return adorationdatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ADdate,ADstarttime,ADendtime,ADvenue,ADcelebrant;
        Button  ADdelete,ADedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ADdate= itemView.findViewById(R.id.ShowAdorationdate);
            ADstarttime= itemView.findViewById(R.id.ShowAdorationstarttime);
            ADvenue= itemView.findViewById(R.id.ShowAdorationVenue);
            ADcelebrant= itemView.findViewById(R.id.ShowAdorationcelebrant);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdatedeleteAdoration.class);
                    intent.putExtra("Adorationdate",ADdate.getText().toString());
                    intent.putExtra("Adorationvenue",ADvenue.getText().toString());
                    intent.putExtra("Adorationcelebrant",ADcelebrant.getText().toString());
                    mContext.startActivity(intent);
                }
            });

          
        }
    }

}
