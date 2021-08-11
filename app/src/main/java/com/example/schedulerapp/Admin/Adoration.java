package com.example.schedulerapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.schedulerapp.Adapters.CustomOnItemSelectedListener;
import com.example.schedulerapp.R;
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

import android.content.DialogInterface;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


public class Adoration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText  Estarttime,Endtime,  Eintention;
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
    private DatabaseReference mRef,bRef,wRef,fRef;
    private StorageReference storageReference;

    private Boolean validateDate() {
        String val1 = Edate.getText().toString();
        if (val1.isEmpty()) {
            Edate.setError("Field Cannot be Empty");
            return false;
        } else {
            Edate.setError(null);
            return true;
        }
    }

    private Boolean validateStarttime() {
        String val2 = Estarttime.getText().toString();
        if (val2.isEmpty()) {
            Estarttime.setError("Field Cannot be Empty");
            return false;
        } else {
            Estarttime.setError(null);
            return true;
        }
    }


    private Boolean validateIntention() {
        String val4 = Eintention.getText().toString();
        if (val4.isEmpty()) {
            Eintention.setError("Field Cannot be Empty");
            return false;
        } else {
            Eintention.setError(null);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoration);
        Edate = (TextView) findViewById(R.id.AdorationDate);
        Estarttime = (EditText) findViewById(R.id.AdorationTime);
        Eintention = (EditText) findViewById(R.id.MassIntention2);
        Endtime= (EditText) findViewById(R.id.endTime);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        btnSubmit = (Button) findViewById(R.id.Save);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance( Adoration.this, Year, Month, Day);
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
                    if (dayOfWeek == Calendar.SUNDAY ) {
                        Calendar[] disabledDays =  new Calendar[1];
                        disabledDays[0] = loopdate;
                        datePickerDialog.setDisabledDays(disabledDays);
                    }
                }
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(Adoration.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEvent();

            }


        });

        Endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Adoration.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Endtime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });



        Estarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Adoration.this,
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


    }




        private void registerEvent () {
          final  String Date = Edate.getText().toString();
           final String Starttime = Estarttime.getText().toString();
           final String intention = Eintention.getText().toString();
           final String venue = spinner1.getSelectedItem().toString();
           final String celebrant = spinner2.getSelectedItem().toString();
            if (!validateStarttime() | !validateDate()  | !validateIntention()) {
                return;
            }
           mRef= FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails");
            fRef=FirebaseDatabase.getInstance().getReference("FuneralDetails").child("Deceased");
            wRef=FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename");
            bRef=FirebaseDatabase.getInstance().getReference("Baptism").child("Baptismdetails");

            final Query query= mRef.orderByChild("Adorationstarttime").equalTo(Starttime);
            final Query query1= mRef.orderByChild("Adorationdate").equalTo(Date);
            final Query query2= mRef.orderByChild("Adorationvenue").equalTo(venue);
            final Query query3= mRef.orderByChild("Adorationcelebrant").equalTo(celebrant);
          final Query query4=fRef.orderByChild("UDATE").equalTo(Date);
            final Query query9=fRef.orderByChild("DVENUE").equalTo(venue);
            final Query query5=fRef.orderByChild("DTIME").equalTo(Starttime);
            final Query query6=fRef.orderByChild("DCELEBRANT").equalTo(celebrant);
            final Query query10=bRef.orderByChild("Baptismdate").equalTo(Date);
            final Query query11=bRef.orderByChild("Baptismvenue").equalTo(venue);
            final Query query12=bRef.orderByChild("Baptismstarttime").equalTo(Starttime);
            final Query query13=bRef.orderByChild("Baptismcelebrant").equalTo(celebrant);
            final Query query14=wRef.orderByChild("UDate").equalTo(Date);
            final Query query15=wRef.orderByChild("UVenue").equalTo(venue);
            final Query query16=wRef.orderByChild("UStarttime").equalTo(Starttime);
            final Query query17=wRef.orderByChild("Ucelebrant").equalTo(celebrant);
//check date in adoration
           query1.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(!snapshot.exists()){
                       //check date in funeral
                    query4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                //check date in baptism
                             query10.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     if(!snapshot.exists()){
                                         //check date in wedding
                                         query14.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 if(!snapshot.exists()){
                                                     String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                     Map<String, String > map = new HashMap<>();
                                                     map.put("AdorationId",AdorationId);
                                                     map.put("Adorationdate", Edate.getText().toString());
                                                     map.put("Adorationstarttime", Estarttime.getText().toString());
                                                     map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                     map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                     map.put("Adorationintention", Eintention.getText().toString());
                                                     mRef.child(AdorationId).setValue(map)
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     Edate.setText(" ");
                                                                     Estarttime.setText(" ");
                                                                     Eintention.setText(" ");
                                                                     Endtime.setText(" ");
                                                                     Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                 }
                                                             })
                                                             .addOnFailureListener(new OnFailureListener() {
                                                                 @Override
                                                                 public void onFailure(@NonNull Exception e) {
                                                                     Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                 }
                                                             });

                                                 }else{
                                                     query15.addListenerForSingleValueEvent(new ValueEventListener() {
                                                         @Override
                                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                             if(!snapshot.exists()){
                                                                 String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                                 Map<String, String > map = new HashMap<>();
                                                                 map.put("AdorationId",AdorationId);
                                                                 map.put("Adorationdate", Edate.getText().toString());
                                                                 map.put("Adorationstarttime", Estarttime.getText().toString());
                                                                 map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                                 map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                                 map.put("Adorationintention", Eintention.getText().toString());
                                                                 mRef.child(AdorationId).setValue(map)
                                                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                             @Override
                                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                                 Edate.setText(" ");
                                                                                 Estarttime.setText(" ");
                                                                                 Eintention.setText(" ");
                                                                                 Endtime.setText(" ");
                                                                                 Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         })
                                                                         .addOnFailureListener(new OnFailureListener() {
                                                                             @Override
                                                                             public void onFailure(@NonNull Exception e) {
                                                                                 Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         });
                                                             } else {
                                                                 query16.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                     @Override
                                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                         if(!snapshot.exists()){
                                                                             String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                                             Map<String, String > map = new HashMap<>();
                                                                             map.put("AdorationId",AdorationId);
                                                                             map.put("Adorationdate", Edate.getText().toString());
                                                                             map.put("Adorationstarttime", Estarttime.getText().toString());
                                                                             map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                                             map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                                             map.put("Adorationintention", Eintention.getText().toString());
                                                                             mRef.child(AdorationId).setValue(map)
                                                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                         @Override
                                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                                             Edate.setText(" ");
                                                                                             Estarttime.setText(" ");
                                                                                             Eintention.setText(" ");
                                                                                             Endtime.setText(" ");
                                                                                             Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                                         }
                                                                                     })
                                                                                     .addOnFailureListener(new OnFailureListener() {
                                                                                         @Override
                                                                                         public void onFailure(@NonNull Exception e) {
                                                                                             Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                         }
                                                                                     });
                                                                         } else{
                                                                             query17.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                     if(!snapshot.exists()){
                                                                                         String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                                                         Map<String, String > map = new HashMap<>();
                                                                                         map.put("AdorationId",AdorationId);
                                                                                         map.put("Adorationdate", Edate.getText().toString());
                                                                                         map.put("Adorationstarttime", Estarttime.getText().toString());
                                                                                         map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                                                         map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                                                         map.put("Adorationintention", Eintention.getText().toString());
                                                                                         mRef.child(AdorationId).setValue(map)
                                                                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                     @Override
                                                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                                                         Edate.setText(" ");
                                                                                                         Estarttime.setText(" ");
                                                                                                         Eintention.setText(" ");
                                                                                                         Endtime.setText(" ");
                                                                                                         Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                                                     }
                                                                                                 })
                                                                                                 .addOnFailureListener(new OnFailureListener() {
                                                                                                     @Override
                                                                                                     public void onFailure(@NonNull Exception e) {
                                                                                                         Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                                     }
                                                                                                 });
                                                                                     } else {
                                                                                         Toast.makeText(Adoration.this, "dataaaaaaaaaaaaaa is there", Toast.LENGTH_SHORT).show();
                                                                                     }
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
                                     } else {
                                         query11.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             if(!snapshot.exists()){
                                                 String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                 Map<String, String > map = new HashMap<>();
                                                 map.put("AdorationId",AdorationId);
                                                 map.put("Adorationdate", Edate.getText().toString());
                                                 map.put("Adorationstarttime", Estarttime.getText().toString());
                                                 map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                 map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                 map.put("Adorationintention", Eintention.getText().toString());
                                                 mRef.child(AdorationId).setValue(map)
                                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                 Edate.setText(" ");
                                                                 Estarttime.setText(" ");
                                                                 Eintention.setText(" ");
                                                                 Endtime.setText(" ");
                                                                 Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                             }
                                                         })
                                                         .addOnFailureListener(new OnFailureListener() {
                                                             @Override
                                                             public void onFailure(@NonNull Exception e) {
                                                                 Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                             }
                                                         });
                                             }else {
                                                 query12.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         if(!snapshot.exists()){
                                                             String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                             Map<String, String > map = new HashMap<>();
                                                             map.put("AdorationId",AdorationId);
                                                             map.put("Adorationdate", Edate.getText().toString());
                                                             map.put("Adorationstarttime", Estarttime.getText().toString());
                                                             map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                             map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                             map.put("Adorationintention", Eintention.getText().toString());
                                                             mRef.child(AdorationId).setValue(map)
                                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                         @Override
                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                             Edate.setText(" ");
                                                                             Estarttime.setText(" ");
                                                                             Eintention.setText(" ");
                                                                             Endtime.setText(" ");
                                                                             Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                         }
                                                                     })
                                                                     .addOnFailureListener(new OnFailureListener() {
                                                                         @Override
                                                                         public void onFailure(@NonNull Exception e) {
                                                                             Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                         }
                                                                     });
                                                         } else {
                                                             query13.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                 @Override
                                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                     if(!snapshot.exists()){
                                                                         String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                                         Map<String, String > map = new HashMap<>();
                                                                         map.put("AdorationId",AdorationId);
                                                                         map.put("Adorationdate", Edate.getText().toString());
                                                                         map.put("Adorationstarttime", Estarttime.getText().toString());
                                                                         map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                                         map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                                         map.put("Adorationintention", Eintention.getText().toString());
                                                                         mRef.child(AdorationId).setValue(map)
                                                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                     @Override
                                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                                         Edate.setText(" ");
                                                                                         Estarttime.setText(" ");
                                                                                         Eintention.setText(" ");
                                                                                         Endtime.setText(" ");
                                                                                         Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                 })
                                                                                 .addOnFailureListener(new OnFailureListener() {
                                                                                     @Override
                                                                                     public void onFailure(@NonNull Exception e) {
                                                                                         Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                 });
                                                                     }   else {
                                                                         Toast.makeText(Adoration.this, "Baptism event exists", Toast.LENGTH_SHORT).show();
                                                                     }
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
                            } else {
                                // check venue
                                query9.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){
                                            String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                            Map<String, String > map = new HashMap<>();
                                            map.put("AdorationId",AdorationId);
                                            map.put("Adorationdate", Edate.getText().toString());
                                            map.put("Adorationstarttime", Estarttime.getText().toString());
                                            map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                            map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                            map.put("Adorationintention", Eintention.getText().toString());
                                            mRef.child(AdorationId).setValue(map)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Edate.setText(" ");
                                                            Estarttime.setText(" ");
                                                            Eintention.setText(" ");
                                                            Endtime.setText(" ");
                                                            Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        } else {
                                            //check funeral time
                                            query5.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(!snapshot.exists()){
                                                        String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                        Map<String, String > map = new HashMap<>();
                                                        map.put("AdorationId",AdorationId);
                                                        map.put("Adorationdate", Edate.getText().toString());
                                                        map.put("Adorationstarttime", Estarttime.getText().toString());
                                                        map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                        map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                        map.put("Adorationintention", Eintention.getText().toString());
                                                        mRef.child(AdorationId).setValue(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Edate.setText(" ");
                                                                        Estarttime.setText(" ");
                                                                        Eintention.setText(" ");
                                                                        Endtime.setText(" ");
                                                                        Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else{
                                                        //check funeral celebrant
                                                        query6.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if(!snapshot.exists()){
                                                                    String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                                    Map<String, String > map = new HashMap<>();
                                                                    map.put("AdorationId",AdorationId);
                                                                    map.put("Adorationdate", Edate.getText().toString());
                                                                    map.put("Adorationstarttime", Estarttime.getText().toString());
                                                                    map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                                    map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                                    map.put("Adorationintention", Eintention.getText().toString());
                                                                    mRef.child(AdorationId).setValue(map)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    Edate.setText(" ");
                                                                                    Estarttime.setText(" ");
                                                                                    Eintention.setText(" ");
                                                                                    Endtime.setText(" ");
                                                                                    Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                } else {  Toast.makeText(Adoration.this, "DATAAAAA EXISTS", Toast.LENGTH_SHORT).show();}
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

                   } else {
                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(!snapshot.exists()){
                               String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                               Map<String, String > map = new HashMap<>();
                               map.put("AdorationId",AdorationId);
                               map.put("Adorationdate", Edate.getText().toString());
                               map.put("Adorationstarttime", Estarttime.getText().toString());
                               map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                               map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                               map.put("Adorationintention", Eintention.getText().toString());
                               mRef.child(AdorationId).setValue(map)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               Edate.setText(" ");
                                               Estarttime.setText(" ");
                                               Eintention.setText(" ");
                                               Endtime.setText(" ");
                                               Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                           }
                                       })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                           } else {
                               query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       if(!snapshot.exists()){
                                           String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                           Map<String, String > map = new HashMap<>();
                                           map.put("AdorationId",AdorationId);
                                           map.put("Adorationdate", Edate.getText().toString());
                                           map.put("Adorationstarttime", Estarttime.getText().toString());
                                           map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                           map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                           map.put("Adorationintention", Eintention.getText().toString());
                                           mRef.child(AdorationId).setValue(map)
                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           Edate.setText(" ");
                                                           Estarttime.setText(" ");
                                                           Eintention.setText(" ");
                                                           Endtime.setText(" ");
                                                           Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                       }
                                                   })
                                                   .addOnFailureListener(new OnFailureListener() {
                                                       @Override
                                                       public void onFailure(@NonNull Exception e) {
                                                           Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                       }
                                                   });
                                       }  else {
                                           query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   if(!snapshot.exists()){
                                                       String AdorationId=mRef.child("Adoration").child("Adorationdetails").push().getKey();
                                                       Map<String, String > map = new HashMap<>();
                                                       map.put("AdorationId",AdorationId);
                                                       map.put("Adorationdate", Edate.getText().toString());
                                                       map.put("Adorationstarttime", Estarttime.getText().toString());
                                                       map.put("Adorationvenue", spinner1.getSelectedItem().toString());
                                                       map.put("Adorationcelebrant", spinner2.getSelectedItem().toString());
                                                       map.put("Adorationintention", Eintention.getText().toString());
                                                       mRef.child(AdorationId).setValue(map)
                                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                       Edate.setText(" ");
                                                                       Estarttime.setText(" ");
                                                                       Eintention.setText(" ");
                                                                       Endtime.setText(" ");
                                                                       Toast.makeText(Adoration.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               })
                                                               .addOnFailureListener(new OnFailureListener() {
                                                                   @Override
                                                                   public void onFailure(@NonNull Exception e) {
                                                                       Toast.makeText(Adoration.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });
                                                   } else { Toast.makeText(Adoration.this, "Adoration event exists", Toast.LENGTH_SHORT).show();}
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

        Toast.makeText(Adoration.this, date, Toast.LENGTH_LONG).show();

        TextView  Edate = (TextView)findViewById(R.id.AdorationDate);
        Edate.setText(date);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
