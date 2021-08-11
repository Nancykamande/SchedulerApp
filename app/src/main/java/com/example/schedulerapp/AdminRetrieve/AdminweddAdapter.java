package com.example.schedulerapp.AdminRetrieve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminweddAdapter extends RecyclerView.Adapter<AdminweddAdapter.ViewHolder> {
    private DatabaseReference ref;
    private Context mContext;
    private List<AdminWedding_method> datalist;
    public AdminweddAdapter(Context mContext, List<AdminWedding_method> datalist) {
        this.mContext=mContext;
        this.datalist = datalist;}
    @NonNull
    @Override
    public AdminweddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminweddinglayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminweddAdapter.ViewHolder holder, int position) {
        final AdminWedding_method adminWedding_method =datalist.get(position);
        holder.Abridename.setText(adminWedding_method.getUBridename());
        holder.Abridecontact.setText(adminWedding_method.getUBridecontact());
        holder.Agroomname.setText(adminWedding_method.getUGroomname());
        holder.Agroomcontact.setText(adminWedding_method.getUGroomcontact());
        holder.Abridemother.setText(adminWedding_method.getUBridemother());
        holder.Abridefather.setText(adminWedding_method.getUBridfather());
        holder.Agroommother.setText(adminWedding_method.getUGroommother());
        holder.Agroomfather.setText(adminWedding_method.getUGroomfather());
        holder.Aspiritual.setText(adminWedding_method.getUSpititualparent());
        holder.Adate.setText(adminWedding_method.getUDate());
        holder.Atime.setText(adminWedding_method.getUStarttime());
        holder.Avenue.setText(adminWedding_method.getUVenue());
        holder.Acelebrant.setText(adminWedding_method.getUcelebrant());
        holder.Aintention.setText(adminWedding_method.getUIntention());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref= FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename").child(adminWedding_method.getWeddingId());
                ref.removeValue();
                Toast.makeText(mContext,"Data is deleted",Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Abridename,Abridecontact,Agroomname,Agroomcontact,Abridemother,Abridefather,Agroommother,Agroomfather;
       TextView  Aspiritual,Adate,Atime,Avenue,Acelebrant,Aintention;
        Button Delete;
        public ViewHolder(View itemView) {
            super(itemView);
            Abridename = itemView.findViewById(R.id.ShowBridename);
            Abridecontact= itemView.findViewById(R.id.ShowBridecontact);
            Agroomname= itemView.findViewById(R.id.ShowGroomname);
            Agroomcontact= itemView.findViewById(R.id.ShowGroomcontact);
            Abridemother= itemView.findViewById(R.id.ShowBridemother);
            Abridefather= itemView.findViewById(R.id.ShowBridefather);
            Agroommother= itemView.findViewById(R.id.ShowGroomMother);
            Agroomfather= itemView.findViewById(R.id.ShowGroomfather);
            Aspiritual= itemView.findViewById(R.id.ShowSpiritualParent);
            Adate= itemView.findViewById(R.id.ShowDate);
            Atime= itemView.findViewById(R.id.ShowTime);
            Avenue= itemView.findViewById(R.id.ShowVenue);
            Acelebrant= itemView.findViewById(R.id.ShowCelebrant);
            Aintention= itemView.findViewById(R.id.ShowIntention);
            Delete= itemView.findViewById(R.id.Delete);
        }
    }
}
