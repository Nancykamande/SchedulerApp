package com.example.schedulerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Dailymass extends AppCompatActivity {
    EditText EBridename,Ephone,EBridemother,EBridefather,EGroom;
    EditText  Ephone2,EGroommother,EGroomfather,EspiritualParent,Evenue,EDate;
    Button Save,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailymass);
        EBridename = findViewById(R.id.Bridename);
        Ephone = findViewById(R.id.bride_phone);
        EBridemother = findViewById(R.id.Bridemother);
        EBridefather = findViewById(R.id.Bridefather);
        EGroom = findViewById(R.id.Groomname);
        Ephone2 = findViewById(R.id.Groom_phone);
        EGroommother = findViewById(R.id.Groommother);
        EGroomfather = findViewById(R.id.Groomfather);
        EspiritualParent = findViewById(R.id.Spiritualparent);
        Evenue = findViewById(R.id.Venue);
        EDate = findViewById(R.id.ScheduledDate);
    }
}
