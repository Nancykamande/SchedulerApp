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

public class EventsRequests extends AppCompatActivity {
    private Button btnView,btnWedd,btnfuneral,save;

    private FirebaseAuth auth;
    private DatabaseReference reff;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_requests);
        btnView = (Button) findViewById(R.id.R_view);
        btnWedd = (Button) findViewById(R.id.R_wedding);
        btnfuneral = (Button) findViewById(R.id.R_funeral);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),com.example.schedulerapp.Christianspackage.Christians.class);
                startActivity(intent);
            }
        });
        btnWedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Hopewedding.class);
                startActivity(intent);
            }
        });

        btnfuneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reportfuneral.class);
                startActivity(intent);
            }
        });


    }
}
