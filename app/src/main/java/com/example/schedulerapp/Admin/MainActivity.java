package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.schedulerapp.Adventures.Signup;
import com.example.schedulerapp.R;


public class MainActivity extends AppCompatActivity {
    private Button Login,EditDelete,Adduser,Addevent;
    private Button Requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Addevent = findViewById(R.id.events);
            EditDelete=findViewById(R.id.view);
        Requests=findViewById(R.id.requests);
        Adduser=findViewById(R.id.users);

        Adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

        Addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Manageactivities.class);
                startActivity(intent);
            }
        });
        Requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminRequests.class);
                startActivity(intent);
            }
        });
        EditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewEvents.class);
                startActivity(intent);
            }
        });

    }
}
