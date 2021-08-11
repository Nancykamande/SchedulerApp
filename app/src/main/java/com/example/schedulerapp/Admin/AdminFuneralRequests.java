package com.example.schedulerapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.schedulerapp.Adapters.Ad_fun_RequestAdapter;
import com.example.schedulerapp.Adapters.AdminFuneralRequests_method;
import com.example.schedulerapp.Adapters.PopweddAdapter;
import com.example.schedulerapp.Adapters.popwedding_method;
import com.example.schedulerapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminFuneralRequests extends AppCompatActivity {
    private FirebaseStorage mStorage;
    private Context mContext;
    List<AdminFuneralRequests_method> datalist;
    RecyclerView recyclerView;
    private Ad_fun_RequestAdapter adapter;
    DatabaseReference nm;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_funeral_requests);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContext= AdminFuneralRequests.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        datalist = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        nm = FirebaseDatabase.getInstance().getReference().child("FuneralReports");
nm.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        if (snapshot.exists()){

            AdminFuneralRequests_method fetchfuneraldata=snapshot.getValue(AdminFuneralRequests_method.class);
            datalist.add(fetchfuneraldata);
            adapter=new Ad_fun_RequestAdapter(mContext,datalist);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }
}
