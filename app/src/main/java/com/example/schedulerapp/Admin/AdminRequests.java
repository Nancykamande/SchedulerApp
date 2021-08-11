package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.Admin.PopWedding;
import com.example.schedulerapp.R;

public class AdminRequests extends AppCompatActivity {
    private Button btn_wedd,btn_funeral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests);
        btn_wedd = findViewById(R.id.R_wedding);
        btn_funeral=findViewById(R.id.R_funeral);

        btn_wedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopWedding.class);
                startActivity(intent);
            }
        });
        btn_funeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminFuneralRequests.class);
                startActivity(intent);
            }
        });

    }
}
