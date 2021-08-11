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


public class Funeral extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText Deceased_name,Deceased_Age,D_Venue,D_Time,D_Intention;
    Spinner  spinner1;
    TextView D_Date;
    TimePickerDialog picker;
    EditText Endtime;
    DatePickerDialog datePickerDialog;
    Button save,Signout,datepicker;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;

    private FirebaseAuth auth;
    private DatabaseReference rootRef, demoRef;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private DatabaseReference mRef,wRef,bRef,fRef,aRef;
    private FirebaseAuth mAuth;
    private Boolean validateName() {
        String val1 = Deceased_name.getText().toString();

        if (val1.isEmpty()) {
            Deceased_name.setError("Field Cannot be Empty");
            return false;
        } else if (val1.length() <= 2) {
            Deceased_name.setError("name too short");
            return false;
        } else if (val1.length() >= 10) {
            Deceased_name.setError("Name too long");
            return false;
        } else {
            Deceased_name.setError(null);
            return true;
        }
    }
        private Boolean validateAge(){
            String val2=  Deceased_Age.getText().toString();

            if(val2.isEmpty()){
                Deceased_Age.setError("Field Cannot be Empty");
                return false;
            }else if(val2.length()<0){
                Deceased_Age.setError("Age invalid");
                return false;
            }else  if(val2.length()>3){
                Deceased_Age.setError("Age invalid");
                return false;
            }
            else {
                Deceased_Age.setError(null);
                return true;
            }
        }
    private Boolean validateVenue() {
        String val10 = D_Venue.getText().toString();
        if (val10.isEmpty()) {
            D_Venue.setError("Field Cannot be Empty");
            return false;
        } else {
            D_Venue.setError(null);
            return true;
        }
    }
    private Boolean validateDate() {
        String val11 = D_Date.getText().toString();
        if (val11.isEmpty()) {
            D_Date.setError("Field Cannot be Empty");
            return false;
        } else {
            D_Date.setError(null);
            return true;
        }
    }
    private Boolean validateTime() {
        String val11 = D_Time.getText().toString();
        if (val11.isEmpty()) {
            D_Time.setError("Field Cannot be Empty");
            return false;
        } else {
            D_Time.setError(null);
            return true;
        }
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funeral);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Deceased_name=(EditText)findViewById(R.id.DECEASEDNAME);
        Deceased_Age=(EditText)findViewById(R.id.DECEASEDAGE);
        D_Venue=(EditText)findViewById(R.id.VENUE);
            Endtime =(EditText)findViewById(R.id.endTime);
        D_Date=findViewById(R.id.ScheduledDate);
        D_Time=(EditText)findViewById(R.id.ScheduledTime);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        D_Intention=(EditText)findViewById(R.id.MassIntention);
        save=(Button)findViewById(R.id.SAVE);
        Signout=(Button)findViewById(R.id.LOGOUT);
            calendar = Calendar.getInstance();

            Year = calendar.get(Calendar.YEAR) ;
            Month = calendar.get(Calendar.MONTH);
            Day = calendar.get(Calendar.DAY_OF_MONTH);
            Hour = calendar.get(Calendar.HOUR_OF_DAY);
            Minute = calendar.get(Calendar.MINUTE);
            final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
            button_datepicker.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    datePickerDialog = DatePickerDialog.newInstance( Funeral.this, Year, Month, Day);
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

                            Toast.makeText(Funeral.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                }
            });





            Endtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Funeral.this,
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

            D_Time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Funeral.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    D_Time.setText(hourOfDay + ":" + minute);
                                }
                            }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }

            });
            mStorage= FirebaseStorage.getInstance();
           storageReference=mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerFuneral();

                }


            });
    }
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        String date = +Day+"/"+(Month+1)+"/"+Year;

        Toast.makeText(Funeral.this, date, Toast.LENGTH_LONG).show();

        TextView  D_Date = (TextView)findViewById(R.id.ScheduledDate);
        D_Date.setText(date);

    }
    private void registerFuneral() {
       final String Deceasedname=Deceased_name.getText().toString();
        final String DeceasedAge=Deceased_Age.getText().toString();
       final  String Venue=D_Venue.getText().toString();
       final String Date=D_Date.getText().toString();
       final String Time=D_Time.getText().toString();
       final String Celebrant = spinner1.getSelectedItem().toString();
       final String Intention=D_Intention.getText().toString();
        if(!validateName()  |!validateAge() |!validateTime() |!validateVenue() |!validateDate()  ) {
            return;

        }
        mRef= FirebaseDatabase.getInstance().getReference("Funeraldetails").child("Deceased");
        wRef=FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename");
        bRef=FirebaseDatabase.getInstance().getReference("Baptism").child("Baptismdetails");
        aRef=FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails");

        Query query= mRef.orderByChild("UDATE").equalTo(Date);
        final Query query1=mRef.orderByChild("DTIME").equalTo(Time);
        final Query query2=mRef.orderByChild("DCELEBRANT").equalTo(Celebrant);
        final Query query10=bRef.orderByChild("Baptismdate").equalTo(Date);
        final Query query12=bRef.orderByChild("Baptismstarttime").equalTo(Time);
        final Query query13=bRef.orderByChild("Baptismcelebrant").equalTo(Celebrant);
        final Query query14=wRef.orderByChild("UDate").equalTo(Date);
        final Query query16=wRef.orderByChild("UStarttime").equalTo(Time);
        final Query query17=wRef.orderByChild("Ucelebrant").equalTo(Celebrant);
        final Query query18= aRef.orderByChild("Adorationstarttime").equalTo(Time);
        final Query query19 = aRef.orderByChild("Adorationdate").equalTo(Date);
        final Query query20 = aRef.orderByChild("Adorationcelebrant").equalTo(Celebrant);
       query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    //check baptism date
                     query10.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             if(!snapshot.exists()){
                                 //chech wedding date
                                 query14.addListenerForSingleValueEvent(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                         if(!snapshot.exists()){
                                         //check adoration date
                                             query19.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     //check adoration date
                                                     if(!snapshot.exists()){
                                                         String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                         Map<String,Object> map=new HashMap<>();
                                                         map.put("FuneralId",FuneralId);
                                                         map.put( "DNAME",Deceased_name.getText().toString());
                                                         map.put( "DAGE",Deceased_Age.getText().toString());
                                                         map.put( "DVENUE",D_Venue.getText().toString());
                                                         map.put( "UDATE",D_Date.getText().toString());
                                                         map.put( "DTIME",D_Time.getText().toString());
                                                         map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                         map.put( "DINTENTION",D_Intention.getText().toString());
                                                         mRef.child(FuneralId).setValue(map)
                                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                         Deceased_name.setText(" ");
                                                                         Deceased_Age.setText(" ");
                                                                         D_Venue.setText(" ");
                                                                         D_Date.setText(" ");
                                                                         D_Time.setText(" ");
                                                                         Endtime.setText(" ");
                                                                         D_Intention.setText(" ");


                                                                         Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 })
                                                                 .addOnFailureListener(new OnFailureListener() {
                                                                     @Override
                                                                     public void onFailure(@NonNull Exception e) {
                                                                         Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 });
                                                     }  else {
                                                         //check adoration starttime
                                                         query18.addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                 if(!snapshot.exists()){
                                                                     String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                                     Map<String,Object> map=new HashMap<>();
                                                                     map.put("FuneralId",FuneralId);
                                                                     map.put( "DNAME",Deceased_name.getText().toString());
                                                                     map.put( "DAGE",Deceased_Age.getText().toString());
                                                                     map.put( "DVENUE",D_Venue.getText().toString());
                                                                     map.put( "UDATE",D_Date.getText().toString());
                                                                     map.put( "DTIME",D_Time.getText().toString());
                                                                     map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                                     map.put( "DINTENTION",D_Intention.getText().toString());
                                                                     mRef.child(FuneralId).setValue(map)
                                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                 @Override
                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                     Deceased_name.setText(" ");
                                                                                     Deceased_Age.setText(" ");
                                                                                     D_Venue.setText(" ");
                                                                                     D_Date.setText(" ");
                                                                                     D_Time.setText(" ");
                                                                                     D_Intention.setText(" ");
                                                                                     Endtime.setText(" ");


                                                                                     Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                 }
                                                                             })
                                                                             .addOnFailureListener(new OnFailureListener() {
                                                                                 @Override
                                                                                 public void onFailure(@NonNull Exception e) {
                                                                                     Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                 }
                                                                             });

                                                                 } else {
                                                                     //check adoration
                                                                     query20.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                         @Override
                                                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                             if(!snapshot.exists()){
                                                                                 String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                                                 Map<String,Object> map=new HashMap<>();
                                                                                 map.put("FuneralId",FuneralId);
                                                                                 map.put( "DNAME",Deceased_name.getText().toString());
                                                                                 map.put( "DAGE",Deceased_Age.getText().toString());
                                                                                 map.put( "DVENUE",D_Venue.getText().toString());
                                                                                 map.put( "UDATE",D_Date.getText().toString());
                                                                                 map.put( "DTIME",D_Time.getText().toString());
                                                                                 map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                                                 map.put( "DINTENTION",D_Intention.getText().toString());
                                                                                 mRef.child(FuneralId).setValue(map)
                                                                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                             @Override
                                                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                                                 Deceased_name.setText(" ");
                                                                                                 Deceased_Age.setText(" ");
                                                                                                 D_Venue.setText(" ");
                                                                                                 D_Date.setText(" ");
                                                                                                 D_Time.setText(" ");
                                                                                                 D_Intention.setText(" ");
                                                                                                 Endtime.setText(" ");


                                                                                                 Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                             }
                                                                                         })
                                                                                         .addOnFailureListener(new OnFailureListener() {
                                                                                             @Override
                                                                                             public void onFailure(@NonNull Exception e) {
                                                                                                 Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                             }
                                                                                         });
                                                                             } else{
                                                                                 Toast.makeText(Funeral.this, "Adoration event exists at this time", Toast.LENGTH_SHORT).show();
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
                                             //check  wedding starttime
                                             query16.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 if(!snapshot.exists()){
                                                     String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                     Map<String,Object> map=new HashMap<>();
                                                     map.put("FuneralId",FuneralId);
                                                     map.put( "DNAME",Deceased_name.getText().toString());
                                                     map.put( "DAGE",Deceased_Age.getText().toString());
                                                     map.put( "DVENUE",D_Venue.getText().toString());
                                                     map.put( "UDATE",D_Date.getText().toString());
                                                     map.put( "DTIME",D_Time.getText().toString());
                                                     map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                     map.put( "DINTENTION",D_Intention.getText().toString());
                                                     mRef.child(FuneralId).setValue(map)
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     Deceased_name.setText(" ");
                                                                     Deceased_Age.setText(" ");
                                                                     D_Venue.setText(" ");
                                                                     D_Date.setText(" ");
                                                                     D_Time.setText(" ");
                                                                     D_Intention.setText(" ");
                                                                     Endtime.setText(" ");


                                                                     Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                 }
                                                             })
                                                             .addOnFailureListener(new OnFailureListener() {
                                                                 @Override
                                                                 public void onFailure(@NonNull Exception e) {
                                                                     Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                 }
                                                             });
                                                 } else {
                                                     //check wedding celebrant
                                                     query17.addListenerForSingleValueEvent(new ValueEventListener() {
                                                         @Override
                                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                             if(!snapshot.exists()){
                                                                 String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                                 Map<String,Object> map=new HashMap<>();
                                                                 map.put("FuneralId",FuneralId);
                                                                 map.put( "DNAME",Deceased_name.getText().toString());
                                                                 map.put( "DAGE",Deceased_Age.getText().toString());
                                                                 map.put( "DVENUE",D_Venue.getText().toString());
                                                                 map.put( "UDATE",D_Date.getText().toString());
                                                                 map.put( "DTIME",D_Time.getText().toString());
                                                                 map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                                 map.put( "DINTENTION",D_Intention.getText().toString());
                                                                 mRef.child(FuneralId).setValue(map)
                                                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                             @Override
                                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                                 Deceased_name.setText(" ");
                                                                                 Deceased_Age.setText(" ");
                                                                                 D_Venue.setText(" ");
                                                                                 D_Date.setText(" ");
                                                                                 D_Time.setText(" ");
                                                                                 D_Intention.setText(" ");
                                                                                 Endtime.setText(" ");


                                                                                 Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         })
                                                                         .addOnFailureListener(new OnFailureListener() {
                                                                             @Override
                                                                             public void onFailure(@NonNull Exception e) {
                                                                                 Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         });
                                                             }  else{
                                                                 Toast.makeText(Funeral.this, "Wedding events exists at this time", Toast.LENGTH_SHORT).show();
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
                                 //check baptism starttime
                                 query12.addListenerForSingleValueEvent(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                         if(!snapshot.exists()){
                                             String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                             Map<String,Object> map=new HashMap<>();
                                             map.put("FuneralId",FuneralId);
                                             map.put( "DNAME",Deceased_name.getText().toString());
                                             map.put( "DAGE",Deceased_Age.getText().toString());
                                             map.put( "DVENUE",D_Venue.getText().toString());
                                             map.put( "UDATE",D_Date.getText().toString());
                                             map.put( "DTIME",D_Time.getText().toString());
                                             map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                             map.put( "DINTENTION",D_Intention.getText().toString());
                                             mRef.child(FuneralId).setValue(map)
                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             Deceased_name.setText(" ");
                                                             Deceased_Age.setText(" ");
                                                             D_Venue.setText(" ");
                                                             D_Date.setText(" ");
                                                             D_Time.setText(" ");
                                                             D_Intention.setText(" ");
                                                             Endtime.setText(" ");


                                                             Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                         }
                                                     })
                                                     .addOnFailureListener(new OnFailureListener() {
                                                         @Override
                                                         public void onFailure(@NonNull Exception e) {
                                                             Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                         }
                                                     });
                                         }  else {
                                             //check  baptism celebrant
                                             query13.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     if(!snapshot.exists()){
                                                         String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                                         Map<String,Object> map=new HashMap<>();
                                                         map.put("FuneralId",FuneralId);
                                                         map.put( "DNAME",Deceased_name.getText().toString());
                                                         map.put( "DAGE",Deceased_Age.getText().toString());
                                                         map.put( "DVENUE",D_Venue.getText().toString());
                                                         map.put( "UDATE",D_Date.getText().toString());
                                                         map.put( "DTIME",D_Time.getText().toString());
                                                         map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                                         map.put( "DINTENTION",D_Intention.getText().toString());
                                                         mRef.child(FuneralId).setValue(map)
                                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                         Deceased_name.setText(" ");
                                                                         Deceased_Age.setText(" ");
                                                                         D_Venue.setText(" ");
                                                                         D_Date.setText(" ");
                                                                         D_Time.setText(" ");
                                                                         D_Intention.setText(" ");
                                                                         Endtime.setText(" ");


                                                                         Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 })
                                                                 .addOnFailureListener(new OnFailureListener() {
                                                                     @Override
                                                                     public void onFailure(@NonNull Exception e) {
                                                                         Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 });
                                                     } else {
                                                         Toast.makeText(Funeral.this, "Baptism Event exists at this time", Toast.LENGTH_SHORT).show();
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











                }else{
                   query1.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(!snapshot.exists()){
                               String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                               Map<String,Object> map=new HashMap<>();
                               map.put("FuneralId",FuneralId);
                               map.put( "DNAME",Deceased_name.getText().toString());
                               map.put( "DAGE",Deceased_Age.getText().toString());
                               map.put( "DVENUE",D_Venue.getText().toString());
                               map.put( "UDATE",D_Date.getText().toString());
                               map.put( "DTIME",D_Time.getText().toString());
                               map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                               map.put( "DINTENTION",D_Intention.getText().toString());
                               mRef.child(FuneralId).setValue(map)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               Deceased_name.setText(" ");
                                               Deceased_Age.setText(" ");
                                               D_Venue.setText(" ");
                                               D_Date.setText(" ");
                                               D_Time.setText(" ");
                                               D_Intention.setText(" ");
                                               Endtime.setText(" ");


                                               Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                           }
                                       })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                           } else {
                               query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       if(!snapshot.exists()){
                                           String FuneralId=mRef.child("Funeraldetails").child("Deceased").push().getKey();
                                           Map<String,Object> map=new HashMap<>();
                                           map.put("FuneralId",FuneralId);
                                           map.put( "DNAME",Deceased_name.getText().toString());
                                           map.put( "DAGE",Deceased_Age.getText().toString());
                                           map.put( "DVENUE",D_Venue.getText().toString());
                                           map.put( "UDATE",D_Date.getText().toString());
                                           map.put( "DTIME",D_Time.getText().toString());
                                           map.put( "DCELEBRANT",spinner1.getSelectedItem().toString());
                                           map.put( "DINTENTION",D_Intention.getText().toString());
                                           mRef.child(FuneralId).setValue(map)
                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           Deceased_name.setText(" ");
                                                           Deceased_Age.setText(" ");
                                                           D_Venue.setText(" ");
                                                           D_Date.setText(" ");
                                                           D_Time.setText(" ");
                                                           D_Intention.setText(" ");
                                                           Endtime.setText(" ");


                                                           Toast.makeText(Funeral.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                       }
                                                   })
                                                   .addOnFailureListener(new OnFailureListener() {
                                                       @Override
                                                       public void onFailure(@NonNull Exception e) {
                                                           Toast.makeText(Funeral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                       }
                                                   });
                                       } else {Toast.makeText(Funeral.this, "Event already exists" , Toast.LENGTH_SHORT).show();}
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
