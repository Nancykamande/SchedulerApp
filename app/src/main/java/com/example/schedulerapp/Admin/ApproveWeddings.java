package com.example.schedulerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schedulerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class ApproveWeddings extends AppCompatActivity {
    EditText Bride_name,Bride_contact,Groom_name,Groom_contact,Bride_mother;
    EditText  Bride_father,Groom_mother,Groom_father,Spriritual_parent,Venue,Mass_intention;
    Button save,Delete,Signout;
    TextView Date;
    TimePickerDialog picker;
    DatePickerDialog datePickerDialog;
    private Spinner spinner1, spinner2;
    EditText Starttime,Endtime;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    private FirebaseAuth auth;
    private DatabaseReference rootRef, demoRef;
    private FirebaseStorage mStorage;
    private DatabaseReference mRef,aRef,bRef,fRef;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_weddings);

        Starttime =findViewById(R.id.WeddingTime);
        Bride_name = findViewById(R.id.Bride);
        Bride_contact = findViewById(R.id.BrideContacts);
        Groom_name = findViewById(R.id.Groom);
        Groom_contact= findViewById(R.id.GroomContacts);
        Bride_mother = findViewById(R.id.BrideMother);
        Bride_father = findViewById(R.id.BrideFather);
        Groom_mother = findViewById(R.id.Groommother);
        Groom_father = findViewById(R.id.Groomfather);
        Spriritual_parent = findViewById(R.id.SpritualParent);
        Date = findViewById(R.id.ScheduledDate);
        Venue = findViewById(R.id.venue);
        Mass_intention = findViewById(R.id.MassIntention);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        save = findViewById(R.id.Save);

        String bride =  getIntent().getStringExtra("Bridename");
        String bridecontact =getIntent().getStringExtra("Bridecontact");
        String groom = getIntent().getStringExtra ("Groomname");
        String groomcontact =  getIntent().getStringExtra("Groomcontact");
        String bride_m =  getIntent().getStringExtra("Bridemother");
        String bride_f = getIntent().getStringExtra("Bridefather");
        String groom_m = getIntent().getStringExtra("Groommother");
        String groom_f = getIntent().getStringExtra("Groomfather");
        String spiritual =  getIntent().getStringExtra("Spiritualparent");
        String venue =  getIntent().getStringExtra("Venue");

       Bride_name.setText(bride);
        Bride_contact.setText(bridecontact);
        Groom_name.setText( groom);
        Groom_contact.setText(groomcontact);
        Bride_mother.setText(bride_m);
        Bride_father.setText(bride_f);
        Groom_mother.setText( groom_m);
        Groom_father.setText(groom_f );
        Spriritual_parent.setText(spiritual);
        Venue.setText( venue);

    }
}
