package com.example.schedulerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.Christianspackage.Feedback;
import com.example.schedulerapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class BaptismAdapter extends RecyclerView.Adapter<BaptismAdapter.ViewHolder> {
    private DatabaseReference ref;
    private Context mContext;
    private List<Baptism_method> baptismdatalist;
    private Object String;

    public BaptismAdapter(Context mContext, List<Baptism_method> baptismdatalist) {
        this.mContext=mContext;
        this.baptismdatalist = baptismdatalist;
    }
    @NonNull
    @Override
    public BaptismAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.baptismlayout,parent,false);
        return new BaptismAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaptismAdapter.ViewHolder holder, int position) {

        final Baptism_method Baptism_method = this.baptismdatalist.get(position);
        holder.Bapdate.setText(Baptism_method.getBaptismdate());
        holder.Bapstarttime.setText(Baptism_method.getBaptismstarttime());
        holder.Bapvenue.setText(Baptism_method.getBaptismvenue());
        holder.Bapcelebrant.setText(Baptism_method.getBaptismcelebrant());
        holder.Bapgroup.setText(Baptism_method.getBaptismgroup());


    }

    @Override
    public int getItemCount() {

        return baptismdatalist.size();
            }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Bapdate,Bapstarttime,Bapvenue,Bapcelebrant,Bapgroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Bapdate= itemView.findViewById(R.id.ShowBaptsimdate);
            Bapstarttime= itemView.findViewById(R.id.Showstarttime);
            Bapvenue= itemView.findViewById(R.id.ShowBaptismVenue);
            Bapcelebrant= itemView.findViewById(R.id.ShowBaptismcelebrant);
            Bapgroup= itemView.findViewById(R.id.ShowBaptismGroup);




        }
    }
}
