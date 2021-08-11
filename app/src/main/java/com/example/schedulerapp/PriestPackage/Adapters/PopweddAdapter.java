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

import com.example.schedulerapp.Admin.ApproveWeddings;
import com.example.schedulerapp.R;

import java.util.List;

public class PopweddAdapter extends RecyclerView.Adapter<PopweddAdapter.ViewHolder> {
    private Context mContext;
    private List<popwedding_method> popwedddatalist;

    public PopweddAdapter(Context mContext, List<popwedding_method> popwedddatalist) {
        this.mContext = mContext;
        this.popwedddatalist = popwedddatalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popweddinglayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final popwedding_method popwedding_method = popwedddatalist.get(position);
        holder.TBridename.setText(popwedding_method.getRBridename());
        holder.TGroomname.setText(popwedding_method.getRGroomname());
        holder.TBridecontact.setText(popwedding_method.getRBridecontact());
        holder.TGroomcontact.setText(popwedding_method.getRGroomcontact());
        holder.TEmail.setText(popwedding_method.getREmail());
        holder.TVenue.setText(popwedding_method.getRVenue());
        holder.Tbridemother.setText(popwedding_method.getRBridemother());
        holder.Tbridefather.setText(popwedding_method.getRBridefather());
        holder.Tgroommother.setText(popwedding_method.getRGroommother());
        holder.Tgroomfather.setText(popwedding_method.getRGroomfather());
        holder.Tspiritualparent.setText(popwedding_method.getRSpiritualparent());
        holder.Tbrideid.setText(popwedding_method.getRBrideId());
        holder.Tgroomid.setText(popwedding_method.getRGroomId());
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ApproveWeddings.class);
                intent.putExtra(" Bridename",popwedding_method.getRBridename());
                intent.putExtra(" Groomname", popwedding_method.getRGroomname());
                intent.putExtra(" Bridecontact", popwedding_method.getRBridecontact());
                intent.putExtra(" Groomcontact", popwedding_method.getRGroomcontact());
                intent.putExtra(" Email",     popwedding_method.getREmail());
                intent.putExtra(" Bridemother", popwedding_method.getRBridemother());
                intent.putExtra(" Bridefather", popwedding_method.getRBridefather());
                intent.putExtra(" Groommother",  popwedding_method.getRGroommother());
                intent.putExtra(" Groomfather",   popwedding_method.getRGroomfather());
                intent.putExtra(" Venue",      popwedding_method.getRVenue());
                intent.putExtra(" Spiritualparent",   popwedding_method.getRSpiritualparent());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        {
            return popwedddatalist.size();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TBridename,TGroomname,TBridecontact,TGroomcontact,TVenue,TEmail;
        TextView     Tbridemother,Tbridefather,Tgroommother,Tgroomfather,Tspiritualparent;
        Button approve;
TextView Tbrideid,Tgroomid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TBridename= itemView.findViewById(R.id.ShowBridename);
            TGroomname= itemView.findViewById(R.id.ShowGroomname);
            TBridecontact= itemView.findViewById(R.id.ShowBridecontact);
            TGroomcontact= itemView.findViewById(R.id.ShowGroomcontact);
            TEmail= itemView.findViewById(R.id.ShowEmail);
            Tbridemother= itemView.findViewById(R.id.ShowBridemother);
            Tbridefather= itemView.findViewById(R.id.ShowBridefather);
            Tgroommother= itemView.findViewById(R.id.ShowGroommother);
            Tgroomfather= itemView.findViewById(R.id.ShowGroomfather);
            TVenue= itemView.findViewById(R.id.ShowVenue);
            Tspiritualparent= itemView.findViewById(R.id.ShowSpiritualParent);
            Tbrideid= itemView.findViewById(R.id.ShowBrideId);
            Tgroomid= itemView.findViewById(R.id.ShowGroomId);
            approve= itemView.findViewById(R.id.btn_Approve);
            //approve.setVisibility(View.GONE);

        }
    }
}
