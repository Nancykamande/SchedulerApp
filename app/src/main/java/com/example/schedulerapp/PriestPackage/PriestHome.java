package com.example.schedulerapp.PriestPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.R;

public class PriestHome extends AppCompatActivity {
    private Button Adduser, Wedding,Baptism,Funeral,Adoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priest_home);
        Wedding = findViewById(R.id.wedding);
        Baptism = findViewById(R.id.baptism);
        Funeral = findViewById(R.id.funeral);
        Adoration = findViewById(R.id.adoration);
        Wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Christianspackage.MainTrial.class);
                startActivity(intent);
            }
        });
        Funeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Christianspackage.Funeraldetails.class);
                startActivity(intent);
            }
        });
        Baptism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Christianspackage.Baptismdetails.class);
                startActivity(intent);
            }
        });
        Adoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.schedulerapp.Christianspackage.Adorationdetails.class);
                startActivity(intent);
            }
        });
    }
}
