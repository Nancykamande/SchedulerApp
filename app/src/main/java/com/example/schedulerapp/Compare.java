package com.example.schedulerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.DialogInterface;

import com.example.schedulerapp.Admin.Funeral;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class Compare extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText Estarttime,  Eintention;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    TextView Edate;
    TimePickerDialog picker;
    DatePickerDialog datePickerDialog;
    Button save,Signout,datepicker;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    private FirebaseAuth auth;
    private FirebaseStorage mStorage;
    private DatabaseReference mRef;
    private DatabaseReference fRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        Estarttime = (EditText) findViewById(R.id.BaptismTime);
        Edate = (TextView) findViewById(R.id.BaptismDate);
        btnSubmit = (Button) findViewById(R.id.Save);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        btnSubmit = (Button) findViewById(R.id.Save);
        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance( Compare.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Date Picker");
                // Setting Min Date to today date
                Calendar min_date_c = Calendar.getInstance();
                datePickerDialog.setMinDate(min_date_c);
                // Setting Max Date to next 2 years
                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.YEAR, Year+2);
                datePickerDialog.setMaxDate(max_date_c);
                //Disable all SUNDAYS , SATURDAYS and thursdays between Min and Max Dates
                for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                    int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY  ) {
                        Calendar[] disabledDays =  new Calendar[1];
                        disabledDays[0] = loopdate;
                        datePickerDialog.setDisabledDays(disabledDays);
                    }
                }
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(Compare.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        Estarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Compare.this,
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
    }

    private void registerEvent() {
        final String Date = Edate.getText().toString();
        final String Starttime = Estarttime.getText().toString();
        mRef= FirebaseDatabase.getInstance().getReference("Compare").child("Comparedetails");
        fRef=FirebaseDatabase.getInstance().getReference("Funeraldetails").child("Deceased");

        Query query= mRef.orderByChild("Comparedate").equalTo(Date);
        final Query query1=mRef.orderByChild("Comparestarttime").equalTo(Starttime);
        final Query query2=fRef.orderByChild("UDATE").equalTo(Date);
        final Query query3= fRef.orderByChild("UTIME").equalTo(Starttime);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
               query2.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(!snapshot.exists()){
                           String CompareId=mRef.child("Compare").child("Comparedetails").push().getKey();
                           Map<String, String > map = new HashMap<>();
                           map.put("CompareId",CompareId);
                           map.put("Comparedate", Date);
                           map.put("Comparestarttime", Starttime);
                           mRef.child(CompareId).setValue(map)
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           Edate.setText(" ");
                                           Estarttime.setText(" ");
                                           Toast.makeText(Compare.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(Compare.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                       }
                                   });

                       }  else {  query3.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if(!snapshot.exists()){
                                   String CompareId=mRef.child("Compare").child("Comparedetails").push().getKey();
                                   Map<String, String > map = new HashMap<>();
                                   map.put("CompareId",CompareId);
                                   map.put("Comparedate", Date);
                                   map.put("Comparestarttime", Starttime);
                                   mRef.child(CompareId).setValue(map)
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   Edate.setText(" ");
                                                   Estarttime.setText(" ");
                                                   Toast.makeText(Compare.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                               }
                                           })
                                           .addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                   Toast.makeText(Compare.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                               }
                                           });
                               }else {
                                   Toast.makeText(Compare.this, "Similar event exists", Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       }); }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
                }else {
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){
                                            String CompareId=mRef.child("Compare").child("Comparedetails").push().getKey();
                                            Map<String, String > map = new HashMap<>();
                                            map.put("CompareId",CompareId);
                                            map.put("Comparedate", Date);
                                            map.put("Comparestarttime", Starttime);
                                            mRef.child(CompareId).setValue(map)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Edate.setText(" ");
                                                            Estarttime.setText(" ");
                                                            Toast.makeText(Compare.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(Compare.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {  Toast.makeText(Compare.this, "Startitme exists", Toast.LENGTH_SHORT).show();}
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }else {  Toast.makeText(Compare.this, "Startitme exists", Toast.LENGTH_SHORT).show();}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        Toast.makeText(Compare.this, date, Toast.LENGTH_LONG).show();
        TextView  Edate = (TextView)findViewById(R.id.BaptismDate);
        Edate.setText(date);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
