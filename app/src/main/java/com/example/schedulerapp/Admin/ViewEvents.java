package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.AdminRetrieve.AdminWedding;
import com.example.schedulerapp.R;

public class ViewEvents extends AppCompatActivity {
    private Button Adduser, Wedding,Baptism,Funeral,Adoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        Wedding = findViewById(R.id.R_wedding);
        Baptism = findViewById(R.id.R_baptism);
        Funeral = findViewById(R.id.R_funeral);
        Adoration = findViewById(R.id.R_Adoration);
        Wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminWedding.class);
                startActivity(intent);
            }
        });
        Adoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.AdminRetrieve.Ad_Adoration.class);
                startActivity(intent);
            }
        });
        Baptism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.AdminRetrieve.Ad_Baptism.class);
                startActivity(intent);
            }
        });
       Funeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.AdminRetrieve.Ad_Funeral.class);
                startActivity(intent);
            }
        });
    }
}
