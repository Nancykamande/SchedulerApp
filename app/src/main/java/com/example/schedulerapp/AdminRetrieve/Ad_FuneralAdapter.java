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

public class Ad_FuneralAdapter extends RecyclerView.Adapter<Ad_FuneralAdapter.ViewHolder> {
    private DatabaseReference ref;
    private Context mContext;
    private List<Ad_funeral_method> funeraldatalist;
    public Ad_FuneralAdapter(Context mContext, List<Ad_funeral_method> funeraldatalist) {
        this.mContext = mContext;
        this.funeraldatalist = funeraldatalist;
    }
    @NonNull
    @Override
    public Ad_FuneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_funerallayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Ad_funeral_method ad_funeral_method = funeraldatalist.get(position);
        holder.Tdate.setText(ad_funeral_method.getUDATE());
        holder.Ttime.setText(ad_funeral_method.getDTIME());
        holder.Tvenue.setText(ad_funeral_method.getDVENUE());
        holder.Tcelebrant.setText(ad_funeral_method.getDCELEBRANT());
        holder.Tintention.setText(ad_funeral_method.getDINTENTION());
        holder.Tage.setText(ad_funeral_method.getDAGE());
        holder.Tname.setText(ad_funeral_method.getDNAME());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref= FirebaseDatabase.getInstance().getReference("Funeraldetails").child("Deceased").child(ad_funeral_method.getFuneralId());
                ref.removeValue();
                Toast.makeText(mContext,"Data is deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return funeraldatalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tdate,Ttime,Tvenue,Tcelebrant,Tintention,Tage,Tname;
        Button Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Tdate = itemView.findViewById(R.id.ShowDate);
            Ttime = itemView.findViewById(R.id.ShowTime);
            Tvenue = itemView.findViewById(R.id.ShowVenue);
            Tcelebrant = itemView.findViewById(R.id.ShowCelebrant);
            Tintention = itemView.findViewById(R.id.ShowIntention);
            Tage = itemView.findViewById(R.id.ShowAge);
            Tname = itemView.findViewById(R.id.ShowName);
            Delete= itemView.findViewById(R.id.Delete);

        }
    }
}
