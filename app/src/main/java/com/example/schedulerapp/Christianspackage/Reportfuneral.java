package com.example.schedulerapp.Christianspackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

public class Reportfuneral extends AppCompatActivity {
    EditText EName,EAge,EPhoneno,ERelative,Ecard,EVenue,EEmail;
    Button save,Signout;
    DatePickerDialog datePickerDialog;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    private DatabaseReference mRef;
    private Boolean validatecontact(){
        String val2=  EPhoneno.getText().toString();

        if(val2.isEmpty()){
            EPhoneno.setError("Field Cannot be Empty");
            return false;
        }else if(val2.length()<10){
            EPhoneno.setError("Phone invalid");
            return false;
        }else  if(val2.length()>10){
            EPhoneno.setError("Phone invalid");
            return false;
        }
        else {
            EPhoneno.setError(null);
            return true;
        }
    }

    private Boolean validateCard(){
        String val30=  Ecard.getText().toString();

        if(val30.isEmpty()){
            Ecard.setError("Field Cannot be Empty");
            return false;
        }else if(val30.length()<1){
            Ecard.setError("membership card number invalid");
            return false;
        }else  if(val30.length()>10){
            Ecard.setError("membership card number invalid");
            return false;
        }
        else {
            Ecard.setError(null);
            return true;
        }
    }

    private Boolean validateVenue() {
        String val6 = EVenue.getText().toString();
        if (val6.isEmpty()) {
            EVenue.setError("Field Cannot be Empty");
            return false;
        } else {
            EVenue.setError(null);
            return true;
        }
    }

    private Boolean validateName() {
        String val7 = EName.getText().toString();
        if (val7.isEmpty()) {
            EName.setError("Field Cannot be Empty");
            return false;
        } else {
            EName.setError(null);
            return true;
        }
    }
    private Boolean validateRelative() {
        String val8 = ERelative.getText().toString();
        if (val8.isEmpty()) {
            ERelative.setError("Field Cannot be Empty");
            return false;
        } else {
            ERelative.setError(null);
            return true;
        }
    }
    private Boolean validateAge(){
        String val2=  EAge.getText().toString();

        if(val2.isEmpty()){
            EAge.setError("Field Cannot be Empty");
            return false;
        }else if(val2.length()<0){
            EAge.setError("Age invalid");
            return false;
        }else  if(val2.length()>2){
            EAge.setError("Age invalid");
            return false;
        }
        else {
            EAge.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String email = EEmail.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

// onClick of button perform this simplest code.
        if (email.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportfuneral);
          EName = findViewById(R.id.Dname);
          EAge = findViewById(R.id.DAGE);
          Ecard=findViewById(R.id.number);
          EPhoneno = findViewById(R.id.Relative_phone);
          ERelative=findViewById(R.id.Relative);
          EVenue = findViewById(R.id.Venue);
        EEmail = findViewById(R.id.Email);
          save = findViewById(R.id.SAVE);

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
        String deName= EName.getText().toString();
        String deAge= EAge.getText().toString();
        String deRelative= ERelative.getText().toString();
        String dePhone=EPhoneno.getText().toString();
        String deVenue=EVenue.getText().toString();
       final String deCard=Ecard.getText().toString();
        String deemail=EEmail.getText().toString();
        if(!validateName()  |!validatecontact() | !validateName() | !validateAge()|!validateVenue()  |!validateCard()   |!validateRelative() |!validateEmail()  ) {
            return;
        }

        mRef= FirebaseDatabase.getInstance().getReference().child("FuneralReports");
        mRef.orderByChild("FCard").equalTo(deCard).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    String ReportId=mRef.push().getKey();
                    Map<String,Object> map=new HashMap<>();
                    map.put( "FName",EName.getText().toString());
                    map.put( "Mcontact",EPhoneno.getText().toString());
                    map.put( "FVenue",EVenue.getText().toString());
                    map.put( "FAge",EAge.getText().toString());
                    map.put( "FRelative",ERelative.getText().toString());
                    map.put( "FEmail",EEmail.getText().toString());
                     map.put( "FCard",Ecard.getText().toString());
                    mRef.child(ReportId).setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    EName.setText(" ");
                                    EPhoneno.setText(" ");
                                    EVenue.setText(" ");
                                    Ecard.setText(" ");
                                    EAge.setText(" ");
                                    EEmail.setText(" ");
                                    ERelative.setText(" ");

                                    Toast.makeText(Reportfuneral.this, "Request sent for approval ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Reportfuneral.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                                }
                            }  );

            }else {
                    Toast.makeText(Reportfuneral.this, "membership card exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
