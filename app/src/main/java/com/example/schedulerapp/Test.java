package com.example.schedulerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.schedulerapp.Adapters.CustomOnItemSelectedListener;
import com.example.schedulerapp.Admin.Wedding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity {
    EditText Edate, Estarttime;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    DatePickerDialog datePickerDialog;
    private FirebaseAuth auth;
    private FirebaseStorage mStorage;
    private DatabaseReference mRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Edate = (EditText) findViewById(R.id.StackDate);
        Estarttime = (EditText) findViewById(R.id.StackTime);
        btnSubmit = (Button) findViewById(R.id.submit);

        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Test.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                Edate.setText(dayOfMonth + "/"
                                        + (month + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        Estarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Test.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Estarttime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEvent();

            }


        });
        addListenerOnSpinnerItemSelection();
    }
    private void addListenerOnSpinnerItemSelection () {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private void registerEvent() {

       final String time=Estarttime.getText().toString();
       final String date=Edate.getText().toString();
        final String venue=spinner1.getSelectedItem().toString();
       final  String celebrant=spinner2.getSelectedItem().toString();
        mRef= FirebaseDatabase.getInstance().getReference("Test").child("fullname");
        final String StartTime = Estarttime.getText().toString().trim();
        Query query = mRef.orderByChild("date").equalTo(date);
        final  Query query1 = mRef.orderByChild("test").equalTo(time);



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    final int count = (int) snapshot1.getChildrenCount();

                    if (count>0){

                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot snapshot2: snapshot.getChildren()){

                                    int tcount = (int) snapshot2.getChildrenCount();

                                    if (tcount >0){
                                        //toast mass cant have same start time
                                        return;
                                    }else if (tcount-1 >0){
                                        //check if there was start time 1 hour before
                                    }
                                    else if (tcount-2 >0){
                                        //check if there was start time 2 hour before
                                    }
                                    else {
                                        //write your saving code
                                        String TesId=mRef.push().getKey();
                                        Map<String,Object> map=new HashMap<>();
                                        map.put( "test",Estarttime.getText().toString());
                                        map.put( "date",Edate.getText().toString());
                                        map.put( "Venue",spinner1.getSelectedItem().toString());
                                        map.put( "Celebrant",spinner2.getSelectedItem().toString());
                                        mRef.child(TesId).setValue(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Estarttime.setText(" ");
                                                        Edate.setText(" ");

                                                        Toast.makeText(Test.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Test.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }else{

                        //write your saving code
                        String TesId=mRef.push().getKey();
                        Map<String,Object> map=new HashMap<>();
                        map.put( "test",Estarttime.getText().toString());
                        map.put( "date",Edate.getText().toString());
                        map.put( "Venue",spinner1.getSelectedItem().toString());
                        map.put( "Celebrant",spinner2.getSelectedItem().toString());
                        mRef.child(TesId).setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Estarttime.setText(" ");
                                        Edate.setText(" ");

                                        Toast.makeText(Test.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Test.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
