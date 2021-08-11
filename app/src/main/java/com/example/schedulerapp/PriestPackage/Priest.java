package com.example.schedulerapp.PriestPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulerapp.Admin.MainActivity;
import com.example.schedulerapp.R;

public class Priest extends AppCompatActivity {
    Button b1;
    EditText e1,e2;
    int counter=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priest);
        e1= findViewById(R.id.email);
        e2= findViewById(R.id.password);
        b1 = findViewById(R.id.Adminlogin);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals("priest@gmail.com")&&e2.getText().toString().equals("priest")){
                    Intent intent = new Intent(getApplicationContext(), PriestHome.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"wrong Credentials",Toast.LENGTH_SHORT).show();
                    if(counter ==0){
                        b1.setEnabled(false);
                    }

                }
            }
        });

    }
}
