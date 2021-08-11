package com.example.schedulerapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import static com.wdullaer.materialdatetimepicker.date.DatePickerDialog.*;


public class Wedding extends AppCompatActivity implements OnDateSetListener {
    EditText Bride_name,Bride_contact,Groom_name,Groom_contact,Bride_mother;
      EditText  Bride_father,Groom_mother,Groom_father,Spriritual_parent,Mass_intention;
    EditText  Eendtime;
    Button save,Signout;
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
    private Boolean validateName(){
        String val1=  Bride_name.getText().toString();

        if(val1.isEmpty()){
            Bride_name.setError("Field Cannot be Empty");
            return false;
        }else if(val1.length()<=2){
            Bride_name.setError("name too short");
            return false;
        }else  if(val1.length()>=19){
            Bride_name.setError("Name too long");
            return false;
        }
        else {
            Bride_name.setError(null);
            return true;
        }
    }
    private Boolean validateBridecontact(){
        String val2=  Bride_contact.getText().toString();

        if(val2.isEmpty()){
            Bride_contact.setError("Field Cannot be Empty");
            return false;
        }else if(val2.length()<10){
            Bride_contact.setError("Phone invalid");
            return false;
        }else  if(val2.length()>10){
            Bride_contact.setError("Phone invalid");
            return false;
        }
        else {
            Bride_contact.setError(null);
            return true;
        }
    }
    private Boolean validateGroom(){
        String val3= Groom_name.getText().toString();

        if(val3.isEmpty()){
            Groom_name.setError("Field Cannot be Empty");
            return false;
        }else if(val3.length()<=2){
            Groom_name.setError("name too short");
            return false;
        }else  if(val3.length()>=10){
          Groom_name.setError("Name too long");
            return false;
        }
        else {
            Groom_name.setError(null);
            return true;
        }
    }
    private Boolean validateGroomcontact(){
        String val4=  Groom_contact.getText().toString();

        if(val4.isEmpty()){
            Groom_contact.setError("Field Cannot be Empty");
            return false;
        }else if(val4.length()<10){
            Groom_contact.setError("Phone invalid");
            return false;
        }else  if(val4.length()>10){
            Groom_contact.setError("phone invalid");
            return false;
        }
        else {
            Groom_contact.setError(null);
            return true;
        }
    }
    private Boolean validateBridemother(){
        String val5=  Bride_mother.getText().toString();

        if(val5.isEmpty()){
            Bride_mother.setError("Field Cannot be Empty");
            return false;
        }else if(val5.length()<=2){
            Bride_mother.setError("name too short");
            return false;
        }else  if(val5.length()>=10){
           Bride_mother.setError("Name too long");
            return false;
        }
        else {
           Bride_mother.setError(null);
            return true;
        }
    }

    private Boolean validateBridefather(){
        String val6=  Bride_father.getText().toString();

        if(val6.isEmpty()){
            Bride_father.setError("Field Cannot be Empty");
            return false;
        }else if(val6.length()<=2){
            Bride_father.setError("name too short");
            return false;
        }else  if(val6.length()>=10){
            Bride_father.setError("Name too long");
            return false;

        }
        else {
            Bride_father.setError(null);
            return true;
        }
    }
    private Boolean validateGroommother(){
        String val7=  Groom_mother.getText().toString();

        if(val7.isEmpty()){
            Groom_mother.setError("Field Cannot be Empty");
            return false;
        }else if(val7.length()<=2){
            Groom_mother.setError("Name too short");
            return false;
        }else  if(val7.length()>=10){
            Groom_mother.setError("names too long");
            return false;

        }
        else {
           Groom_mother.setError(null);
            return true;
        }
    }
    private Boolean validateGroomfather(){
        String val8=  Groom_father.getText().toString();

        if(val8.isEmpty()){
            Groom_father.setError("Field Cannot be Empty");
            return false;
        }else if(val8.length()<=2){
            Groom_father.setError("Name too short");
            return false;
        }else  if(val8.length()>=10){
            Groom_father.setError("Name too long");
            return false;

        }
        else {
            Groom_father.setError(null);
            return true;
        }
    }
    private Boolean validateSpriritualparent(){
        String val9=  Spriritual_parent.getText().toString();

        if(val9.isEmpty()){
            Spriritual_parent.setError("Field Cannot be Empty");
            return false;
        }else if(val9.length()<=2){
            Spriritual_parent.setError("Name too short");
            return false;
        }else  if(val9.length()>=15){
            Spriritual_parent.setError("Names too long");
            return false;

        }
        else {
            Spriritual_parent.setError(null);
            return true;
        }
    }


    private Boolean validateDate() {
        String val11 = Date.getText().toString();
        if (val11.isEmpty()) {
           Date.setError("Field Cannot be Empty");
            return false;
        } else {
            Date.setError(null);
            return true;
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_wedding);
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
        Mass_intention = findViewById(R.id.MassIntention);
        Eendtime= findViewById(R.id.endTime);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        save = findViewById(R.id.Save);
       /* rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("Weddings");*/
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog = newInstance( Wedding.this, Year, Month, Day);
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

                }
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(Wedding.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        Eendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Wedding.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Starttime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });


        Starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Wedding.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Starttime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });




        mStorage=FirebaseStorage.getInstance();
        storageReference=mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEvent();

            }


        });
    }


    private void registerEvent() {
       final String Bridename=Bride_name.getText().toString();
        final String Bridecontact=Bride_contact.getText().toString();
        final String Groomname=Groom_name.getText().toString();
        final String Groomcontact=Groom_contact.getText().toString();
        final String Bridemother=Bride_mother.getText().toString();
        final String Bridefather=Bride_father.getText().toString();
        final String Groommother=Groom_mother.getText().toString();
        final String GroomFather=Groom_father.getText().toString();
        final  String SpiritualParent=Spriritual_parent.getText().toString();
        final  String starttime=Starttime.getText().toString();
        final String venue = spinner1.getSelectedItem().toString();
        final String celebrant = spinner2.getSelectedItem().toString();
        final String date=Date.getText().toString();
        final String Intention=Mass_intention.getText().toString();
        if(!validateName()  |!validateBridecontact() | !validateGroom() |!validateBridecontact() |!validateGroomcontact() |!validateBridemother() |!validateBridefather() |!validateGroommother() |!validateGroomfather() |!validateSpriritualparent()  |!validateDate()  ) {
            return;
        }


        mRef= FirebaseDatabase.getInstance().getReference("weddingdetails").child("UBridename");
        bRef= FirebaseDatabase.getInstance().getReference("Baptism").child("Baptismdetails");
        aRef=FirebaseDatabase.getInstance().getReference("Adoration").child("Adorationdetails");
        fRef= FirebaseDatabase.getInstance().getReference("Funeraldetails").child("Deceased");


        final Query query=mRef.orderByChild("UStarttime").equalTo(starttime);
        final Query query1=mRef.orderByChild("UDate").equalTo(date);
        final Query query2=mRef.orderByChild("Ucelebrant").equalTo(celebrant);
        final Query query3=mRef.orderByChild("UVenue").equalTo(venue);
       final Query query6=  bRef.orderByChild("Baptismstarttime").equalTo(starttime);
        final  Query query7=  bRef.orderByChild("Baptismdate").equalTo(date);
        final Query query8=  bRef.orderByChild("Baptismvenue").equalTo(venue);
        final Query query9=  bRef.orderByChild("Baptismcelebrant").equalTo(celebrant);
        final Query query18= aRef.orderByChild("Adorationstarttime").equalTo(starttime);
        final Query query19 = aRef.orderByChild("Adorationdate").equalTo(date);
        final Query query20 = aRef.orderByChild("Adorationcelebrant").equalTo(celebrant);
        final Query query25 = aRef.orderByChild("Adorationvenue").equalTo(venue);
        final Query query22 = fRef.orderByChild("UDATE").equalTo(date);
        final Query query23 =fRef.orderByChild("DTIME").equalTo(starttime);
        final Query query24 =fRef.orderByChild("DCELEBRANT").equalTo(celebrant);



        //compare date
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    query7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                query19.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){
                                            query22.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(!snapshot.exists()){
                                                        String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                        Map<String,Object> map=new HashMap<>();
                                                        map.put("WeddingId",WeddingId);
                                                        map.put( "UBridename",Bride_name.getText().toString());
                                                        map.put( "UBridecontact",Bride_contact.getText().toString());
                                                        map.put( "UGroomname",Groom_name.getText().toString());
                                                        map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                        map.put( "UBridemother",Bride_mother.getText().toString());
                                                        map.put( "UBridfather",Bride_father.getText().toString());
                                                        map.put( "UGroommother",Groom_mother.getText().toString());
                                                        map.put( "UGroomfather",Groom_father.getText().toString());
                                                        map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                        map.put( "UStarttime",Starttime.getText().toString());
                                                        map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                        map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                        map.put( "UDate",Date.getText().toString());
                                                        map.put( "UIntention",Mass_intention.getText().toString());
                                                        mRef.child(WeddingId).setValue(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Bride_name.setText(" ");
                                                                        Bride_contact.setText(" ");
                                                                        Groom_name.setText(" ");
                                                                        Groom_contact.setText(" ");
                                                                        Bride_mother.setText(" ");
                                                                        Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                        Groom_father.setText(" ");
                                                                        Spriritual_parent.setText(" ");
                                                                        Starttime.setText(" ");
                                                                        Date.setText(" ");
                                                                        Mass_intention.setText(" ");
                                                                        Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        query23.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if(!snapshot.exists()){
                                                                    String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                                    Map<String,Object> map=new HashMap<>();
                                                                    map.put("WeddingId",WeddingId);
                                                                    map.put( "UBridename",Bride_name.getText().toString());
                                                                    map.put( "UBridecontact",Bride_contact.getText().toString());
                                                                    map.put( "UGroomname",Groom_name.getText().toString());
                                                                    map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                                    map.put( "UBridemother",Bride_mother.getText().toString());
                                                                    map.put( "UBridfather",Bride_father.getText().toString());
                                                                    map.put( "UGroommother",Groom_mother.getText().toString());
                                                                    map.put( "UGroomfather",Groom_father.getText().toString());
                                                                    map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                                    map.put( "UStarttime",Starttime.getText().toString());
                                                                    map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                                    map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                                    map.put( "UDate",Date.getText().toString());
                                                                    map.put( "UIntention",Mass_intention.getText().toString());
                                                                    mRef.child(WeddingId).setValue(map)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    Bride_name.setText(" ");
                                                                                    Bride_contact.setText(" ");
                                                                                    Groom_name.setText(" ");
                                                                                    Groom_contact.setText(" ");
                                                                                    Bride_mother.setText(" ");
                                                                                    Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                                    Groom_father.setText(" ");
                                                                                    Spriritual_parent.setText(" ");
                                                                                    Starttime.setText(" ");
                                                                                    Date.setText(" ");
                                                                                    Mass_intention.setText(" ");
                                                                                    Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                } else {
                                                                    query24.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            if(!snapshot.exists()){
                                                                                String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                                                Map<String,Object> map=new HashMap<>();
                                                                                map.put("WeddingId",WeddingId);
                                                                                map.put( "UBridename",Bride_name.getText().toString());
                                                                                map.put( "UBridecontact",Bride_contact.getText().toString());
                                                                                map.put( "UGroomname",Groom_name.getText().toString());
                                                                                map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                                                map.put( "UBridemother",Bride_mother.getText().toString());
                                                                                map.put( "UBridfather",Bride_father.getText().toString());
                                                                                map.put( "UGroommother",Groom_mother.getText().toString());
                                                                                map.put( "UGroomfather",Groom_father.getText().toString());
                                                                                map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                                                map.put( "UStarttime",Starttime.getText().toString());
                                                                                map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                                                map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                                                map.put( "UDate",Date.getText().toString());
                                                                                map.put( "UIntention",Mass_intention.getText().toString());
                                                                                mRef.child(WeddingId).setValue(map)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                Bride_name.setText(" ");
                                                                                                Bride_contact.setText(" ");
                                                                                                Groom_name.setText(" ");
                                                                                                Groom_contact.setText(" ");
                                                                                                Bride_mother.setText(" ");
                                                                                                Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                                                Groom_father.setText(" ");
                                                                                                Spriritual_parent.setText(" ");
                                                                                                Starttime.setText(" ");
                                                                                                Date.setText(" ");
                                                                                                Mass_intention.setText(" ");
                                                                                                Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        })
                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }  else {
                                                                                Toast.makeText(Wedding.this, "funeral event exists", Toast.LENGTH_SHORT).show();
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
                                        }else {//adoration
                                            query18.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(!snapshot.exists()){
                                                        String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                        Map<String,Object> map=new HashMap<>();
                                                        map.put("WeddingId",WeddingId);
                                                        map.put( "UBridename",Bride_name.getText().toString());
                                                        map.put( "UBridecontact",Bride_contact.getText().toString());
                                                        map.put( "UGroomname",Groom_name.getText().toString());
                                                        map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                        map.put( "UBridemother",Bride_mother.getText().toString());
                                                        map.put( "UBridfather",Bride_father.getText().toString());
                                                        map.put( "UGroommother",Groom_mother.getText().toString());
                                                        map.put( "UGroomfather",Groom_father.getText().toString());
                                                        map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                        map.put( "UStarttime",Starttime.getText().toString());
                                                        map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                        map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                        map.put( "UDate",Date.getText().toString());
                                                        map.put( "UIntention",Mass_intention.getText().toString());
                                                        mRef.child(WeddingId).setValue(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Bride_name.setText(" ");
                                                                        Bride_contact.setText(" ");
                                                                        Groom_name.setText(" ");
                                                                        Groom_contact.setText(" ");
                                                                        Bride_mother.setText(" ");
                                                                        Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                        Groom_father.setText(" ");
                                                                        Spriritual_parent.setText(" ");
                                                                        Starttime.setText(" ");
                                                                        Date.setText(" ");
                                                                        Mass_intention.setText(" ");
                                                                        Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        query20.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if(!snapshot.exists()){
                                                                    String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                                    Map<String,Object> map=new HashMap<>();
                                                                    map.put("WeddingId",WeddingId);
                                                                    map.put( "UBridename",Bride_name.getText().toString());
                                                                    map.put( "UBridecontact",Bride_contact.getText().toString());
                                                                    map.put( "UGroomname",Groom_name.getText().toString());
                                                                    map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                                    map.put( "UBridemother",Bride_mother.getText().toString());
                                                                    map.put( "UBridfather",Bride_father.getText().toString());
                                                                    map.put( "UGroommother",Groom_mother.getText().toString());
                                                                    map.put( "UGroomfather",Groom_father.getText().toString());
                                                                    map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                                    map.put( "UStarttime",Starttime.getText().toString());
                                                                    map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                                    map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                                    map.put( "UDate",Date.getText().toString());
                                                                    map.put( "UIntention",Mass_intention.getText().toString());
                                                                    mRef.child(WeddingId).setValue(map)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    Bride_name.setText(" ");
                                                                                    Bride_contact.setText(" ");
                                                                                    Groom_name.setText(" ");
                                                                                    Groom_contact.setText(" ");
                                                                                    Bride_mother.setText(" ");
                                                                                    Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                                    Groom_father.setText(" ");
                                                                                    Spriritual_parent.setText(" ");
                                                                                    Starttime.setText(" ");
                                                                                    Date.setText(" ");
                                                                                    Mass_intention.setText(" ");
                                                                                    Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }  else {
                                                                    query25.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            if(!snapshot.exists()){
                                                                                String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                                                Map<String,Object> map=new HashMap<>();
                                                                                map.put("WeddingId",WeddingId);
                                                                                map.put( "UBridename",Bride_name.getText().toString());
                                                                                map.put( "UBridecontact",Bride_contact.getText().toString());
                                                                                map.put( "UGroomname",Groom_name.getText().toString());
                                                                                map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                                                map.put( "UBridemother",Bride_mother.getText().toString());
                                                                                map.put( "UBridfather",Bride_father.getText().toString());
                                                                                map.put( "UGroommother",Groom_mother.getText().toString());
                                                                                map.put( "UGroomfather",Groom_father.getText().toString());
                                                                                map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                                                map.put( "UStarttime",Starttime.getText().toString());
                                                                                map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                                                map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                                                map.put( "UDate",Date.getText().toString());
                                                                                map.put( "UIntention",Mass_intention.getText().toString());
                                                                                mRef.child(WeddingId).setValue(map)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                Bride_name.setText(" ");
                                                                                                Bride_contact.setText(" ");
                                                                                                Groom_name.setText(" ");
                                                                                                Groom_contact.setText(" ");
                                                                                                Bride_mother.setText(" ");
                                                                                                Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                                                Groom_father.setText(" ");
                                                                                                Spriritual_parent.setText(" ");
                                                                                                Starttime.setText(" ");
                                                                                                Date.setText(" ");
                                                                                                Mass_intention.setText(" ");
                                                                                                Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        })
                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }   else {
                                                                                Toast.makeText(Wedding.this, "Adoration event exists at this time", Toast.LENGTH_SHORT).show();
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
                            }  else {query6.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("WeddingId",WeddingId);
                                        map.put( "UBridename",Bride_name.getText().toString());
                                        map.put( "UBridecontact",Bride_contact.getText().toString());
                                        map.put( "UGroomname",Groom_name.getText().toString());
                                        map.put( "UGroomcontact",Groom_contact.getText().toString());
                                        map.put( "UBridemother",Bride_mother.getText().toString());
                                        map.put( "UBridfather",Bride_father.getText().toString());
                                        map.put( "UGroommother",Groom_mother.getText().toString());
                                        map.put( "UGroomfather",Groom_father.getText().toString());
                                        map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                        map.put( "UStarttime",Starttime.getText().toString());
                                        map.put( "UVenue",spinner1.getSelectedItem().toString());
                                        map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                        map.put( "UDate",Date.getText().toString());
                                        map.put( "UIntention",Mass_intention.getText().toString());
                                        mRef.child(WeddingId).setValue(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Bride_name.setText(" ");
                                                        Bride_contact.setText(" ");
                                                        Groom_name.setText(" ");
                                                        Groom_contact.setText(" ");
                                                        Bride_mother.setText(" ");
                                                        Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                        Groom_father.setText(" ");
                                                        Spriritual_parent.setText(" ");
                                                        Starttime.setText(" ");
                                                        Date.setText(" ");
                                                        Mass_intention.setText(" ");
                                                        Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }  else {
                                        query8.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(!snapshot.exists()){
                                                    String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                    Map<String,Object> map=new HashMap<>();
                                                    map.put("WeddingId",WeddingId);
                                                    map.put( "UBridename",Bride_name.getText().toString());
                                                    map.put( "UBridecontact",Bride_contact.getText().toString());
                                                    map.put( "UGroomname",Groom_name.getText().toString());
                                                    map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                    map.put( "UBridemother",Bride_mother.getText().toString());
                                                    map.put( "UBridfather",Bride_father.getText().toString());
                                                    map.put( "UGroommother",Groom_mother.getText().toString());
                                                    map.put( "UGroomfather",Groom_father.getText().toString());
                                                    map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                    map.put( "UStarttime",Starttime.getText().toString());
                                                    map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                    map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                    map.put( "UDate",Date.getText().toString());
                                                    map.put( "UIntention",Mass_intention.getText().toString());
                                                    mRef.child(WeddingId).setValue(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Bride_name.setText(" ");
                                                                    Bride_contact.setText(" ");
                                                                    Groom_name.setText(" ");
                                                                    Groom_contact.setText(" ");
                                                                    Bride_mother.setText(" ");
                                                                    Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                    Groom_father.setText(" ");
                                                                    Spriritual_parent.setText(" ");
                                                                    Starttime.setText(" ");
                                                                    Date.setText(" ");
                                                                    Mass_intention.setText(" ");
                                                                    Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }else{
                                                    query9.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if(!snapshot.exists()){
                                                                String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                                Map<String,Object> map=new HashMap<>();
                                                                map.put("WeddingId",WeddingId);
                                                                map.put( "UBridename",Bride_name.getText().toString());
                                                                map.put( "UBridecontact",Bride_contact.getText().toString());
                                                                map.put( "UGroomname",Groom_name.getText().toString());
                                                                map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                                map.put( "UBridemother",Bride_mother.getText().toString());
                                                                map.put( "UBridfather",Bride_father.getText().toString());
                                                                map.put( "UGroommother",Groom_mother.getText().toString());
                                                                map.put( "UGroomfather",Groom_father.getText().toString());
                                                                map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                                map.put( "UStarttime",Starttime.getText().toString());
                                                                map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                                map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                                map.put( "UDate",Date.getText().toString());
                                                                map.put( "UIntention",Mass_intention.getText().toString());
                                                                mRef.child(WeddingId).setValue(map)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Bride_name.setText(" ");
                                                                                Bride_contact.setText(" ");
                                                                                Groom_name.setText(" ");
                                                                                Groom_contact.setText(" ");
                                                                                Bride_mother.setText(" ");
                                                                                Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                                Groom_father.setText(" ");
                                                                                Spriritual_parent.setText(" ");
                                                                                Starttime.setText(" ");
                                                                                Date.setText(" ");
                                                                                Mass_intention.setText(" ");
                                                                                Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            } else {    Toast.makeText(Wedding.this, "Baptism event exists at this time", Toast.LENGTH_SHORT).show();}
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





                }else{
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                Map<String,Object> map=new HashMap<>();
                                map.put("WeddingId",WeddingId);
                                map.put( "UBridename",Bride_name.getText().toString());
                                map.put( "UBridecontact",Bride_contact.getText().toString());
                                map.put( "UGroomname",Groom_name.getText().toString());
                                map.put( "UGroomcontact",Groom_contact.getText().toString());
                                map.put( "UBridemother",Bride_mother.getText().toString());
                                map.put( "UBridfather",Bride_father.getText().toString());
                                map.put( "UGroommother",Groom_mother.getText().toString());
                                map.put( "UGroomfather",Groom_father.getText().toString());
                                map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                map.put( "UStarttime",Starttime.getText().toString());
                                map.put( "UVenue",spinner1.getSelectedItem().toString());
                                map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                map.put( "UDate",Date.getText().toString());
                                map.put( "UIntention",Mass_intention.getText().toString());
                                mRef.child(WeddingId).setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Bride_name.setText(" ");
                                                Bride_contact.setText(" ");
                                                Groom_name.setText(" ");
                                                Groom_contact.setText(" ");
                                                Bride_mother.setText(" ");
                                                Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                Groom_father.setText(" ");
                                                Spriritual_parent.setText(" ");
                                                Starttime.setText(" ");
                                                Date.setText(" ");
                                                Mass_intention.setText(" ");
                                                Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }  else {
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){
                                            String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                            Map<String,Object> map=new HashMap<>();
                                            map.put("WeddingId",WeddingId);
                                            map.put( "UBridename",Bride_name.getText().toString());
                                            map.put( "UBridecontact",Bride_contact.getText().toString());
                                            map.put( "UGroomname",Groom_name.getText().toString());
                                            map.put( "UGroomcontact",Groom_contact.getText().toString());
                                            map.put( "UBridemother",Bride_mother.getText().toString());
                                            map.put( "UBridfather",Bride_father.getText().toString());
                                            map.put( "UGroommother",Groom_mother.getText().toString());
                                            map.put( "UGroomfather",Groom_father.getText().toString());
                                            map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                            map.put( "UStarttime",Starttime.getText().toString());
                                            map.put( "UVenue",spinner1.getSelectedItem().toString());
                                            map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                            map.put( "UDate",Date.getText().toString());
                                            map.put( "UIntention",Mass_intention.getText().toString());
                                            mRef.child(WeddingId).setValue(map)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Bride_name.setText(" ");
                                                            Bride_contact.setText(" ");
                                                            Groom_name.setText(" ");
                                                            Groom_contact.setText(" ");
                                                            Bride_mother.setText(" ");
                                                            Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                            Groom_father.setText(" ");
                                                            Spriritual_parent.setText(" ");
                                                            Starttime.setText(" ");
                                                            Date.setText(" ");
                                                            Mass_intention.setText(" ");
                                                            Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }   else {
                                            query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(!snapshot.exists()){
                                                        String WeddingId=mRef.child("weddingdetails").child("UBridename").push().getKey();
                                                        Map<String,Object> map=new HashMap<>();
                                                        map.put("WeddingId",WeddingId);
                                                        map.put( "UBridename",Bride_name.getText().toString());
                                                        map.put( "UBridecontact",Bride_contact.getText().toString());
                                                        map.put( "UGroomname",Groom_name.getText().toString());
                                                        map.put( "UGroomcontact",Groom_contact.getText().toString());
                                                        map.put( "UBridemother",Bride_mother.getText().toString());
                                                        map.put( "UBridfather",Bride_father.getText().toString());
                                                        map.put( "UGroommother",Groom_mother.getText().toString());
                                                        map.put( "UGroomfather",Groom_father.getText().toString());
                                                        map.put( "USpititualparent",Spriritual_parent.getText().toString());
                                                        map.put( "UStarttime",Starttime.getText().toString());
                                                        map.put( "UVenue",spinner1.getSelectedItem().toString());
                                                        map.put( "Ucelebrant",spinner2.getSelectedItem().toString());
                                                        map.put( "UDate",Date.getText().toString());
                                                        map.put( "UIntention",Mass_intention.getText().toString());
                                                        mRef.child(WeddingId).setValue(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Bride_name.setText(" ");
                                                                        Bride_contact.setText(" ");
                                                                        Groom_name.setText(" ");
                                                                        Groom_contact.setText(" ");
                                                                        Bride_mother.setText(" ");
                                                                        Bride_father.setText(" ");                                                                                                                                                                                                                                                                                                                                                  Groom_mother.setText(" ");
                                                                        Groom_father.setText(" ");
                                                                        Spriritual_parent.setText(" ");
                                                                        Starttime.setText(" ");
                                                                        Date.setText(" ");
                                                                        Mass_intention.setText(" ");
                                                                        Toast.makeText(Wedding.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(Wedding.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        Toast.makeText(Wedding.this, "Similar event exists", Toast.LENGTH_SHORT).show();
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



    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        Toast.makeText(Wedding.this, date, Toast.LENGTH_LONG).show();


        Date.setText(date);

    }
}
