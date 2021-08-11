package com.example.schedulerapp.Christianspackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.schedulerapp.Adapters.AdorationAdapter;
import com.example.schedulerapp.Adapters.Adoration_method;
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

public class Adorationdetails extends AppCompatActivity {
    private FirebaseStorage mStorage;
    private Context mContext;
    List<Adoration_method> adorationlistdata;
    RecyclerView recyclerView;
    private AdorationAdapter adapter;
    DatabaseReference nm;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adorationdetails);
        setTitle("Search here");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContext= Adorationdetails.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adorationlistdata = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        nm = FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails");

        nm.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){

                    Adoration_method fetchadorationdata=snapshot.getValue(Adoration_method.class);
                    adorationlistdata.add(fetchadorationdata);
                    adapter=new AdorationAdapter(mContext,adorationlistdata);
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
