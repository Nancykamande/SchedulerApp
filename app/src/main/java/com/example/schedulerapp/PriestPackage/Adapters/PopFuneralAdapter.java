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

import com.example.schedulerapp.Admin.ApproveFuneral;
import com.example.schedulerapp.Admin.ApproveWeddings;
import com.example.schedulerapp.R;

import java.util.List;

public class PopFuneralAdapter extends RecyclerView.Adapter<PopFuneralAdapter.ViewHolder> {
    private Context mContext;
    private List<popfuneral_method> funeraldatalist;

    public PopFuneralAdapter(Context mContext, List<popfuneral_method> funeraldatalist) {
        this.mContext = mContext;
        this.funeraldatalist = funeraldatalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popfunerallayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopFuneralAdapter.ViewHolder holder, int position) {
        popfuneral_method popfuneral_method = funeraldatalist.get(position);
        holder.Tname.setText(popfuneral_method.getFName());
        holder.Tage.setText(popfuneral_method.getFAge());
        holder.Tcard.setText(popfuneral_method.getFCard());
        holder.Trelative.setText(popfuneral_method.getFRelative());
        holder.TEmail.setText(popfuneral_method.getFEmail());
        holder.TVenue.setText(popfuneral_method.getFVenue());
        holder.Tcontact.setText(popfuneral_method.getMcontact());
        holder.approve.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                              }
                                          }
        );


    }




    @Override
    public int getItemCount() {

        return funeraldatalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tname,Tage,Tcard,Trelative,TVenue,TEmail,Tcontact;
        Button approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tname= itemView.findViewById(R.id.Showname);
            Tcard= itemView.findViewById(R.id.Showcard);
            Trelative= itemView.findViewById(R.id.Showrelative);
            Tage= itemView.findViewById(R.id.Showage);
            TEmail= itemView.findViewById(R.id.Showemail);
            TVenue= itemView.findViewById(R.id.Showvenue);
            Tcontact= itemView.findViewById(R.id.Showcontact);
            approve= itemView.findViewById(R.id.btn_Approve);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApproveFuneral.class);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}

