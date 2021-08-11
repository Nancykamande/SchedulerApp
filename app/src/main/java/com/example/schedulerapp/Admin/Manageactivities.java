package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import com.example.schedulerapp.R;

public class Manageactivities extends AppCompatActivity {
    private Button Adduser, Wedding,Baptism,Funeral,Adoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageactivities);
        Wedding = findViewById(R.id.wedding);
        Baptism = findViewById(R.id.baptism);
        Funeral = findViewById(R.id.funeral);
        Adoration = findViewById(R.id.adoration);


        Wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Admin.Wedding.class);
                startActivity(intent);
            }
        });


        Funeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Admin.Funeral.class);
                startActivity(intent);
            }
        });
        Baptism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Admin.Baptism.class);
                startActivity(intent);
            }
        });
        Adoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Admin.Adoration.class);
                startActivity(intent);
            }
        });
    }
}
