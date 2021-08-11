package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.schedulerapp.R;

public class ApproveFuneral extends AppCompatActivity {
    EditText Deceased_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_funeral);
        Deceased_name=(EditText)findViewById(R.id.DECEASEDNAME);
        String deceased =  getIntent().getStringExtra("name");
        Deceased_name.setText(deceased);
    }
}
