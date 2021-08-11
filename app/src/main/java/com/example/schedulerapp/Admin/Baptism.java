package com.example.schedulerapp.Admin;

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


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class Baptism extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText  Estarttime,Eintention,Eendtime;
    TextView Edate;
    private Spinner spinner1, spinner2, spinner3;
    private Button btnSubmit;
    DatePickerDialog datePickerDialog;
    private FirebaseAuth auth;
    private DatabaseReference mRef, aRef,wRef,fRef;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;


    private Boolean validateDate() {
        String val11 = Edate.getText().toString();
        if (val11.isEmpty()) {
            Edate.setError("Field Cannot be Empty");
            return false;
        } else {
            Edate.setError(null);
            return true;
        }
    }   private Boolean validatetime() {
        String val1 =Estarttime.getText().toString();
        if (val1.isEmpty()) {
            Estarttime.setError("Field Cannot be Empty");
            return false;
        } else {
            Estarttime.setError(null);
            return true;
        }
    }
    private Boolean validateintention() {
        String val4 =Eintention.getText().toString();
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
        setContentView(R.layout.activity_baptism);
        Edate = (TextView) findViewById(R.id.BaptismDate);
        Estarttime = (EditText) findViewById(R.id.BaptismTime);
        Eendtime =(EditText) findViewById(R.id.endTime);
        Eintention=(EditText) findViewById(R.id.MassIntention2);
        mStorage = FirebaseStorage.getInstance();
        btnSubmit=(Button) findViewById(R.id.Save);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        storageReference = mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEvent();

            }


        });
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance( Baptism.this, Year, Month, Day);
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

                //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                    int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);

                }    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(Baptism.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
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
                mTimePicker = new TimePickerDialog(Baptism.this,
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
        Eendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Baptism.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Eendtime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });



    }

    private void registerEvent(){
   final String Date=Edate.getText().toString();
    final String Starttime=Estarttime.getText().toString();
   final String venue = spinner1.getSelectedItem().toString();
    final String celebrant = spinner2.getSelectedItem().toString();
    final String  group = spinner3.getSelectedItem().toString();
    final String BaptismIntention=Eintention.getText().toString();
        if(!validatetime()  |!validateintention()   |!validateDate()) {
            return;
        }


    mRef= FirebaseDatabase.getInstance().getReference("Baptism").child("Baptismdetails");
    fRef= FirebaseDatabase.getInstance().getReference("Funeraldetails").child("Deceased");
    wRef=FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename");
    aRef=FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails");

    final Query query=  mRef.orderByChild("Baptismstarttime").equalTo(Starttime);
    final  Query query1=  mRef.orderByChild("Baptismdate").equalTo(Date);
     final Query query2=  mRef.orderByChild("Baptismvenue").equalTo(venue);
    final Query query3=  mRef.orderByChild("Baptismcelebrant").equalTo(celebrant);
    final Query query14=wRef.orderByChild("UDate").equalTo(Date);
    final Query query16=wRef.orderByChild("UStarttime").equalTo(Starttime);
    final Query query17=wRef.orderByChild("Ucelebrant").equalTo(celebrant);
    final Query query21=wRef.orderByChild("UVenue:").equalTo(venue);
    final Query query18= aRef.orderByChild("Adorationstarttime").equalTo(Starttime);
    final Query query19 = aRef.orderByChild("Adorationdate").equalTo(Date);
    final Query query20 = aRef.orderByChild("Adorationcelebrant").equalTo(celebrant);
    final Query query25 = aRef.orderByChild("Adorationvenue").equalTo(venue);
    final Query query22 = fRef.orderByChild("UDATE").equalTo(Date);
    final Query query23 =fRef.orderByChild("DTIME").equalTo(Starttime);
    final Query query24 =fRef.orderByChild("DCELEBRANT").equalTo(celebrant);

    query1.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(!snapshot.exists()){
                //check wedding data
                query14.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            //check adoration date
                            query19.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //check funeral date
                                    if(!snapshot.exists()){
                                    query22.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(!snapshot.exists()){
                                                String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                Map<String,Object> map=new HashMap<>();
                                                map.put("BaptismId",BaptismId);
                                                map.put( "Baptismdate",Edate.getText().toString());
                                                map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                map.put( "Baptismintention",Eintention.getText().toString());
                                                mRef.child(BaptismId).setValue(map)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Edate.setText(" ");
                                                                Estarttime.setText(" ");
                                                                Eintention.setText(" ");
                                                                Eendtime.setText(" ");

                                                                Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                query23.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(!snapshot.exists()){
                                                            String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                            Map<String,Object> map=new HashMap<>();
                                                            map.put("BaptismId",BaptismId);
                                                            map.put( "Baptismdate",Edate.getText().toString());
                                                            map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                            map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                            map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                            map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                            map.put( "Baptismintention",Eintention.getText().toString());
                                                            mRef.child(BaptismId).setValue(map)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Edate.setText(" ");
                                                                            Estarttime.setText(" ");
                                                                            Eintention.setText(" ");
                                                                            Eendtime.setText(" ");
                                                                            Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }  else {
                                                            query24.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if(!snapshot.exists()){
                                                                        String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                                        Map<String,Object> map=new HashMap<>();
                                                                        map.put("BaptismId",BaptismId);
                                                                        map.put( "Baptismdate",Edate.getText().toString());
                                                                        map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                                        map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                                        map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                                        map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                                        map.put( "Baptismintention",Eintention.getText().toString());
                                                                        mRef.child(BaptismId).setValue(map)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        Edate.setText(" ");
                                                                                        Estarttime.setText(" ");
                                                                                        Eintention.setText(" ");
                                                                                        Eendtime.setText(" ");
                                                                                        Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                })
                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                    } else {
                                                                        Toast.makeText(Baptism.this, "Funeral event exists at this time", Toast.LENGTH_SHORT).show();
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
                                }else {
                                        query20.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(!snapshot.exists()){
                                                    String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                    Map<String,Object> map=new HashMap<>();
                                                    map.put("BaptismId",BaptismId);
                                                    map.put( "Baptismdate",Edate.getText().toString());
                                                    map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                    map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                    map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                    map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                    map.put( "Baptismintention",Eintention.getText().toString());
                                                    mRef.child(BaptismId).setValue(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Edate.setText(" ");
                                                                    Estarttime.setText(" ");
                                                                    Eintention.setText(" ");
                                                                    Eendtime.setText(" ");
                                                                    Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    query25.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if(!snapshot.exists()){
                                                                String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                                Map<String,Object> map=new HashMap<>();
                                                                map.put("BaptismId",BaptismId);
                                                                map.put( "Baptismdate",Edate.getText().toString());
                                                                map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                                map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                                map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                                map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                                map.put( "Baptismintention",Eintention.getText().toString());
                                                                mRef.child(BaptismId).setValue(map)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Edate.setText(" ");
                                                                                Estarttime.setText(" ");
                                                                                Eintention.setText(" ");
                                                                                Eendtime.setText(" ");
                                                                                Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }  else {
                                                                query18.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        if(!snapshot.exists()){
                                                                            String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                                            Map<String,Object> map=new HashMap<>();
                                                                            map.put("BaptismId",BaptismId);
                                                                            map.put( "Baptismdate",Edate.getText().toString());
                                                                            map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                                            map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                                            map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                                            map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                                            map.put( "Baptismintention",Eintention.getText().toString());
                                                                            mRef.child(BaptismId).setValue(map)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            Edate.setText(" ");
                                                                                            Estarttime.setText(" ");
                                                                                            Eintention.setText(" ");
                                                                                            Eendtime.setText(" ");
                                                                                            Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                        } else {
                                                                            Toast.makeText(Baptism.this, "Adoration event exists at this time", Toast.LENGTH_SHORT).show();
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

                        } else{
                            query16.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("BaptismId",BaptismId);
                                        map.put( "Baptismdate",Edate.getText().toString());
                                        map.put( "Baptismstarttime",Estarttime.getText().toString());
                                        map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                        map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                        map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                        map.put( "Baptismintention",Eintention.getText().toString());
                                        mRef.child(BaptismId).setValue(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Edate.setText(" ");
                                                        Estarttime.setText(" ");
                                                        Eintention.setText(" ");
                                                        Eendtime.setText(" ");
                                                        Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else{
                                        query17.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(!snapshot.exists()){
                                                    String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                    Map<String,Object> map=new HashMap<>();
                                                    map.put("BaptismId",BaptismId);
                                                    map.put( "Baptismdate",Edate.getText().toString());
                                                    map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                    map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                    map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                    map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                    map.put( "Baptismintention",Eintention.getText().toString());
                                                    mRef.child(BaptismId).setValue(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Edate.setText(" ");
                                                                    Estarttime.setText(" ");
                                                                    Eintention.setText(" ");
                                                                    Eendtime.setText(" ");
                                                                    Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }  else{
                                                    query21.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if(!snapshot.exists()){
                                                                String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                                Map<String,Object> map=new HashMap<>();
                                                                map.put("BaptismId",BaptismId);
                                                                map.put( "Baptismdate",Edate.getText().toString());
                                                                map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                                map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                                map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                                map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                                map.put( "Baptismintention",Eintention.getText().toString());
                                                                mRef.child(BaptismId).setValue(map)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Edate.setText(" ");
                                                                                Estarttime.setText(" ");
                                                                                Eintention.setText(" ");
                                                                                Eendtime.setText(" ");
                                                                                Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            } else {
                                                                Toast.makeText(Baptism.this, "wedding event exists at this time", Toast.LENGTH_SHORT).show();
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



            }else{query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                        Map<String,Object> map=new HashMap<>();
                        map.put("BaptismId",BaptismId);
                        map.put( "Baptismdate",Edate.getText().toString());
                        map.put( "Baptismstarttime",Estarttime.getText().toString());
                        map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                        map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                        map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                        map.put( "Baptismintention",Eintention.getText().toString());
                        mRef.child(BaptismId).setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Edate.setText(" ");
                                        Estarttime.setText(" ");
                                        Eintention.setText(" ");
                                        Eendtime.setText(" ");
                                        Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }  else {
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!snapshot.exists()){
                                    String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                    Map<String,Object> map=new HashMap<>();
                                    map.put("BaptismId",BaptismId);
                                    map.put( "Baptismdate",Edate.getText().toString());
                                    map.put( "Baptismstarttime",Estarttime.getText().toString());
                                    map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                    map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                    map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                    map.put( "Baptismintention",Eintention.getText().toString());
                                    mRef.child(BaptismId).setValue(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Edate.setText(" ");
                                                    Estarttime.setText(" ");
                                                    Eintention.setText(" ");
                                                    Eendtime.setText(" ");
                                                    Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(!snapshot.exists()){
                                                String BaptismId=mRef.child("Baptism").child("Baptismdetails").push().getKey();
                                                Map<String,Object> map=new HashMap<>();
                                                map.put("BaptismId",BaptismId);
                                                map.put( "Baptismdate",Edate.getText().toString());
                                                map.put( "Baptismstarttime",Estarttime.getText().toString());
                                                map.put( "Baptismvenue",spinner1.getSelectedItem().toString());
                                                map.put( "Baptismcelebrant",spinner2.getSelectedItem().toString());
                                                map.put( "Baptismgroup",spinner3.getSelectedItem().toString());
                                                map.put( "Baptismintention",Eintention.getText().toString());
                                                mRef.child(BaptismId).setValue(map)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Edate.setText(" ");
                                                                Estarttime.setText(" ");
                                                                Eintention.setText(" ");
                                                                Eendtime.setText(" ");
                                                                Toast.makeText(Baptism.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Baptism.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }  else {   Toast.makeText(Baptism.this, "Baptsim event alreday exists", Toast.LENGTH_SHORT).show();}
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
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        String date = +Day+"/"+(Month+1)+"/"+Year;

        Toast.makeText(Baptism.this, date, Toast.LENGTH_LONG).show();

        Edate.setText(date);

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
