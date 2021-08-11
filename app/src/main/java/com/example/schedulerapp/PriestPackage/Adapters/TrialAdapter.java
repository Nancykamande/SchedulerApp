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

public class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.ViewHolder> {
    private Context mContext;
    private List<Fetchweddingdata> weddingdatalist;

    public TrialAdapter(Context mContext,List<Fetchweddingdata> weddingdatalist) {
        this.mContext=mContext;
        this.weddingdatalist = weddingdatalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detaillayout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fetchweddingdata fetchweddingdata =weddingdatalist.get(position);

        holder.TBRIDENAME.setText(fetchweddingdata.getUBridename());
        holder.TGROOMNAME.setText(fetchweddingdata.getUGroomname());
        holder.TDATE.setText(fetchweddingdata.getUDate());
        holder.TVENUE.setText(fetchweddingdata.getUVenue());
        holder.TCELEBRANT.setText(fetchweddingdata.getUcelebrant());
        holder.Ttime.setText(fetchweddingdata.getUStarttime());
        holder.Tintention.setText(fetchweddingdata.getUIntention());
    }

    @Override
    public int getItemCount() {
        return weddingdatalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView TBRIDENAME,TGROOMNAME,TDATE,TVENUE,TCELEBRANT,Ttime,Tintention;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TBRIDENAME= itemView.findViewById(R.id.ShowBridename);
            TGROOMNAME= itemView.findViewById(R.id.ShowGroomname);
            TDATE= itemView.findViewById(R.id.ShowWeddingdate);
            TVENUE= itemView.findViewById(R.id.ShowWeddingVenue);
            TCELEBRANT= itemView.findViewById(R.id.ShowWeddingCelebrant);
            Ttime= itemView.findViewById(R.id.ShowWeddingTime);
            Tintention= itemView.findViewById(R.id.ShowWeddingIntention);
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
