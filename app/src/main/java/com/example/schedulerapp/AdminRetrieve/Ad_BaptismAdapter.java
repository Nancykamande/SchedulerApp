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

public class Ad_BaptismAdapter  extends RecyclerView.Adapter<Ad_BaptismAdapter.ViewHolder> {
    private DatabaseReference ref;
    private Context mContext;
    private List<Ad_Baptism_method> baptismdatalist;
    public Ad_BaptismAdapter(Context mContext, List<Ad_Baptism_method> baptismdatalist) {
        this.mContext = mContext;
        this.baptismdatalist = baptismdatalist;
    }
    @NonNull
    @Override
    public Ad_BaptismAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_baptismlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Ad_Baptism_method ad_baptism_method = baptismdatalist.get(position);
        holder.Tdate.setText(ad_baptism_method.getBaptismdate());
        holder.TStarttime.setText(ad_baptism_method.getBaptismstarttime());
        holder.Tvenue.setText(ad_baptism_method.getBaptismvenue());
        holder.Tcelebrant.setText(ad_baptism_method.getBaptismcelebrant());
        holder.Tintention.setText(ad_baptism_method.getBaptismintention());
        holder.Tgroup.setText(ad_baptism_method.getBaptismgroup());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref= FirebaseDatabase.getInstance().getReference("Baptism").child("Baptismdetails").child(ad_baptism_method.getBaptismId());
                ref.removeValue();
                Toast.makeText(mContext,"Data is deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return baptismdatalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tdate,TStarttime,Tvenue,Tcelebrant,Tintention,Tgroup;
        Button Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tdate= itemView.findViewById(R.id.ShowDate);
            TStarttime= itemView.findViewById(R.id.ShowTime);
            Tvenue= itemView.findViewById(R.id.ShowVenue);
            Tcelebrant= itemView.findViewById(R.id.ShowCelebrant);
            Tintention= itemView.findViewById(R.id.ShowIntention);
            Tgroup= itemView.findViewById(R.id.ShowIntention);
            Delete= itemView.findViewById(R.id.Delete);
        }
    }
}
