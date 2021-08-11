package com.example.schedulerapp.Christianspackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Christians extends AppCompatActivity {
    private Button btnProfile;
    Button Wedding,Baptism,funeral,adoration;
    private FirebaseAuth auth;
    private DatabaseReference reff;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christians);
        auth = FirebaseAuth.getInstance();

        Wedding = (Button) findViewById(R.id.wedding);
        Baptism = (Button) findViewById(R.id.baptism);
        funeral = (Button) findViewById(R.id.funeral);
        adoration = (Button) findViewById(R.id.adoration);



        Wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainTrial.class);
                startActivity(intent);
            }
        });
        Baptism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Baptismdetails.class);
                startActivity(intent);
            }
        });
        funeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Funeraldetails.class);
                startActivity(intent);
            }
        });
        adoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Adorationdetails.class);
                startActivity(intent);
            }
        });


    }
}
