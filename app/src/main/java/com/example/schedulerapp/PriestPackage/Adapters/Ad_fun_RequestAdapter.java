package com.example.schedulerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.Admin.ApproveFuneral;
import com.example.schedulerapp.R;

import java.util.List;

public class Ad_fun_RequestAdapter extends RecyclerView.Adapter<Ad_fun_RequestAdapter.ViewHolder> {
    private Context mContext;
    private List<AdminFuneralRequests_method> datalist;

    public Ad_fun_RequestAdapter(Context mContext, List<AdminFuneralRequests_method> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.funeralrequestlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ad_fun_RequestAdapter.ViewHolder holder, int position) {
        AdminFuneralRequests_method adminFuneralRequests_method = datalist.get(position);
        holder.Tname.setText(adminFuneralRequests_method.getFName());
        holder.Tage.setText(adminFuneralRequests_method.getFAge());
        holder.Tcard.setText(adminFuneralRequests_method.getFCard());
        holder.Trelative.setText(adminFuneralRequests_method.getFRelative());
        holder.TEmail.setText(adminFuneralRequests_method.getFEmail());
        holder.TVenue.setText(adminFuneralRequests_method.getFVenue());
        holder.Tcontact.setText(adminFuneralRequests_method.getMcontact());
    }



    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tname,Tage,Tcard,Trelative,TVenue,TEmail,Tcontact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tname= itemView.findViewById(R.id.Showname);
            Tcard= itemView.findViewById(R.id.Showcard);
            Trelative= itemView.findViewById(R.id.Showrelative);
            Tage= itemView.findViewById(R.id.Showage);
            TEmail= itemView.findViewById(R.id.Showemail);
            TVenue= itemView.findViewById(R.id.Showvenue);
            Tcontact= itemView.findViewById(R.id.Showcontact);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApproveFuneral.class);
                    intent.putExtra(" name",Tname.getText().toString());
                    mContext.startActivity(intent);
                }
            });
        }
    }


}
