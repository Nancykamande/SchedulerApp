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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Stackover extends AppCompatActivity {
    EditText Edate, Estarttime;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    DatePickerDialog datePickerDialog;
    private FirebaseAuth auth;
    private FirebaseStorage mStorage;
    private DatabaseReference categoryRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackover);
        Edate = (EditText) findViewById(R.id.StackDate);
        Estarttime = (EditText) findViewById(R.id.StackTime);
        btnSubmit = (Button) findViewById(R.id.submit);
       // storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");

        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Stackover.this,
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
                mTimePicker = new TimePickerDialog(Stackover.this,
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
        final String StackDate = Edate.getText().toString().trim();
        final String StackTime = Estarttime.getText().toString().trim();
        final String StackText1 = spinner1.getSelectedItem().toString();
        final String StackText2 = spinner2.getSelectedItem().toString();
        categoryRef= FirebaseDatabase.getInstance().getReference("Stack").child("fullname");
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(!(snapshot.child("stackdate").hasChild(StackDate))){
                    categoryRef.child("stackdate").setValue(StackDate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Edate.setText(" ");
                                    Estarttime.setText(" ");
                                    Toast.makeText(Stackover.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Stackover.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                }
                            });


                } else  Toast.makeText(Stackover.this, "date already allocated", Toast.LENGTH_SHORT).show();
                if(!(snapshot.child("stacktime").exists())){
                    categoryRef.child("stacktime").setValue(StackTime)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Edate.setText(" ");
                                    Estarttime.setText(" ");
                                    Toast.makeText(Stackover.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Stackover.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else  Toast.makeText(Stackover.this, "time already allocated", Toast.LENGTH_SHORT).show();
                if(!(snapshot.child("stackcelebrant").exists())){
                    categoryRef.child("stackcelebrant").setValue(StackText2)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Edate.setText(" ");
                                    Estarttime.setText(" ");
                                    Toast.makeText(Stackover.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Stackover.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else  Toast.makeText(Stackover.this, "celebrant already allocated", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        }}
