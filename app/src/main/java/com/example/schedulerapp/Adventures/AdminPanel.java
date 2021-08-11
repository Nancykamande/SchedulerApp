package com.example.schedulerapp.Adventures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.R;

public class AdminPanel extends AppCompatActivity {
    Button fireb, Addmap,Addfeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        fireb = (Button) findViewById(R.id.editusers);
        //Addmap = (Button) findViewById(R.id.addmap);
        Addfeatures = findViewById(R.id.addfeatures);
//        fireb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Opening new user registration activity using intent on button click.
//                Intent intent = new Intent(AdminPanel.this, webviewbase.class);
//                startActivity(intent);
//
//            }
//        });
        /** Addmap.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        Intent intent = new Intent(adminpanel.this, placess.class);
        startActivity(intent);

        }
        });*/
        Addfeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdminPanel.this, featuresActivity.class);
                startActivity(intent);

            }
        });
    }
}
