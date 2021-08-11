package com.example.schedulerapp.Christianspackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.schedulerapp.Adapters.TrialAdapter;
import com.example.schedulerapp.Adapters.Fetchweddingdata;
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

public class MainTrial extends AppCompatActivity {
    private Context mContext;
    List<Fetchweddingdata> Listdata;
    RecyclerView recyclerView;
    Button feedback;
    private TrialAdapter adapter;
    DatabaseReference nm;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_trial);

        mContext=MainTrial.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       Listdata = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();


        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        nm = FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename");
        nm.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (snapshot.exists()){

                Fetchweddingdata fetchweddingdata=snapshot.getValue(Fetchweddingdata.class);
                Listdata.add(fetchweddingdata);
                adapter=new TrialAdapter(mContext,Listdata);
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
    } );
    }
}