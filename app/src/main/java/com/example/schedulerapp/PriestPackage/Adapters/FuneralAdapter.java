package com.example.schedulerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.Christianspackage.Feedback;
import com.example.schedulerapp.R;

import java.util.List;

public class FuneralAdapter extends RecyclerView.Adapter<FuneralAdapter.ViewHolder>{
    private Context mContext;
    private List<Funeral_method> funeraldatalist;
    public FuneralAdapter(Context mContext,List<Funeral_method> funeraldatalist) {
        this.mContext=mContext;
        this.funeraldatalist = funeraldatalist;
    }

    @NonNull
    @Override
    public FuneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.funerallayout,parent,false);
        return new FuneralAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FuneralAdapter.ViewHolder holder, int position) {
       Funeral_method Funeral_method =funeraldatalist.get(position);
        holder.D_name.setText(Funeral_method.getDNAME());
        holder.D_Date.setText(Funeral_method.getUDATE());
        holder.D_Venue.setText(Funeral_method.getDVENUE());
        holder.D_Time.setText(Funeral_method.getDTIME());
        holder.D_Celebrant.setText(Funeral_method.getDCELEBRANT());

    }



    @Override
    public int getItemCount() {return funeraldatalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView D_name,D_Venue,D_Date,D_Time,D_Celebrant;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            D_name= itemView.findViewById(R.id.ShowDeceasedNameTextView);
            D_Time= itemView.findViewById(R.id.ShowBurialtime);
            D_Date= itemView.findViewById(R.id.ShowBurialDate);
            D_Venue= itemView.findViewById(R.id.ShowBurialVenue);
            D_Celebrant= itemView.findViewById(R.id.ShowBurialCelebrant);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Feedback.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
