package com.example.schedulerapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.Admin.Weddingdata;
import com.example.schedulerapp.R;

import java.util.List;

public class MyweddingAdapter extends RecyclerView.Adapter<MyweddingAdapter.ViewHolder>{
    private List<Weddingdata> listData;
    public MyweddingAdapter(List<Weddingdata> listData){
        this.listData= listData;
    }

    @NonNull
    @Override
    public MyweddingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wedd_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyweddingAdapter.ViewHolder holder, int position) {
        Weddingdata Wd=listData.get(position);
        holder.txtBridename.setText(Wd.getBRIDENAME());
        holder.txtGroomname.setText(Wd.getGROOMNAME());
        holder.txtDate.setText(Wd.getDATE());
        holder.txtVenue.setText(Wd.getVENUE());
        holder.txtCelebrant.setText(Wd.getCELEBRANT());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtBridename,txtGroomname,txtDate,txtVenue,txtCelebrant;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txtBridename=(TextView)itemView.findViewById(R.id.Bridename);
            txtGroomname=(TextView)itemView.findViewById(R.id.Groomname);
            txtDate=(TextView)itemView.findViewById(R.id.DATE);
            txtVenue=(TextView)itemView.findViewById(R.id.VENUE);
            txtCelebrant=(TextView)itemView.findViewById(R.id.CELEBRANT);
        }
    }
}
