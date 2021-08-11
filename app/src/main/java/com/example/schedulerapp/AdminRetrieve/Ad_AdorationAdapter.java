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

import com.example.schedulerapp.Adapters.PopweddAdapter;
import com.example.schedulerapp.Adapters.popwedding_method;
import com.example.schedulerapp.AdminRetrieve.Ad_Adoration_method;
import com.example.schedulerapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Ad_AdorationAdapter extends RecyclerView.Adapter<Ad_AdorationAdapter.ViewHolder> {
    private DatabaseReference ref;
    private Context mContext;
    private List<Ad_Adoration_method> adorationdatalist;
    public Ad_AdorationAdapter(Context mContext, List<Ad_Adoration_method> adorationdatalist) {
        this.mContext = mContext;
        this.adorationdatalist = adorationdatalist;
    }

    @NonNull
    @Override
    public Ad_AdorationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_adorationlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ad_AdorationAdapter.ViewHolder holder, int position) {
        final Ad_Adoration_method ad_adoration_method = adorationdatalist.get(position);
        holder.Tdate.setText(ad_adoration_method.getAdorationdate());
        holder.TStarttime.setText(ad_adoration_method.getAdorationstarttime());
        holder.Tvenue.setText(ad_adoration_method.getAdorationvenue());
        holder.Tcelebrant.setText(ad_adoration_method.getAdorationcelebrant());
        holder.Tintention.setText(ad_adoration_method.getAdorationintention());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          ref= FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails").child(ad_adoration_method.getAdorationId());
          ref.removeValue();
          Toast.makeText(mContext,"Data is deleted",Toast.LENGTH_SHORT).show();
      }
       });
    }

    @Override
    public int getItemCount() {
        return adorationdatalist.size();
    }
  public class ViewHolder extends RecyclerView.ViewHolder{
      TextView Tdate,TStarttime,Tvenue,Tcelebrant,Tintention ;
       Button Delete;
      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          Tdate= itemView.findViewById(R.id.ShowDate);
          TStarttime= itemView.findViewById(R.id.ShowTime);
          Tvenue= itemView.findViewById(R.id.ShowVenue);
          Tcelebrant= itemView.findViewById(R.id.ShowCelebrant);
          Tintention= itemView.findViewById(R.id.ShowIntention);
          Delete= itemView.findViewById(R.id.Delete);
      }

  }
}
