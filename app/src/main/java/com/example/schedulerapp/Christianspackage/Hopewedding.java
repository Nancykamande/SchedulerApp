package com.example.schedulerapp.Christianspackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schedulerapp.Github;
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

public class Hopewedding extends AppCompatActivity {
    EditText Bride_name,Bride_contact,Bride_mother,Bride_father,BEmail;
    EditText  Groom_name,Groom_contact,Groom_mother,Groom_father,Spriritual_parent,BrideId,GroomId;
            Spinner Venue;
    Button save,Signout;
    DatePickerDialog datePickerDialog;
    private DatabaseReference mRef;
   private FirebaseStorage mStorage;
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
        }else  if(val3.length()>=20){
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
        }else  if(val5.length()>=20){
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
        }else  if(val6.length()>=20){
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
        }else  if(val7.length()>=20){
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
        }else  if(val8.length()>=20){
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
        }else  if(val9.length()>=40){
            Spriritual_parent.setError("Names too long");
            return false;

        }
        else {
            Spriritual_parent.setError(null);
            return true;
        }
    }
    private Boolean validateBrideId(){
        String val15=  BrideId.getText().toString();

        if(val15.isEmpty()){
            BrideId.setError("Field Cannot be Empty");
            return false;
        }else if(val15.length()<7){
            BrideId.setError("Id number invalid");
            return false;
        }else  if(val15.length()>8){
            BrideId.setError("Id number invalid");
            return false;
        }
        else {
            BrideId.setError(null);
            return true;
        }
    }
    private Boolean validateGroomId(){
        String val16=  GroomId.getText().toString();

        if(val16.isEmpty()){
            GroomId.setError("Field Cannot be Empty");
            return false;
        }else if(val16.length()<6){
            GroomId.setError("Id number invalid");
            return false;
        }else  if(val16.length()>8){
            GroomId.setError("Id number invalid");
            return false;
        }
        else {
            GroomId.setError(null);
            return true;
        }
    }


    private Boolean validateEmail() {

        String email = BEmail.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

// onClick of button perform this simplest code.
        if (email.matches(emailPattern))
        {

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
        setContentView(R.layout.activity_hopewedding);
        Bride_name = findViewById(R.id.Bride);
        Bride_contact = findViewById(R.id.bride_phone);
        Bride_mother = findViewById(R.id.Bridemother);
        Bride_father = findViewById(R.id.Bridefather);
        Groom_name = findViewById(R.id.Groomname);
        Groom_contact= findViewById(R.id.Groom_phone);
        Groom_mother = findViewById(R.id.Groommother);
        BEmail = findViewById(R.id.Email);
        Groom_father = findViewById(R.id.Groomfather);
        Spriritual_parent = findViewById(R.id.Spiritualparent);
        BrideId = findViewById(R.id.BrideID);
        GroomId = findViewById(R.id.GroomID);
        Venue = findViewById(R.id.Venue);
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
        String Bridename=Bride_name.getText().toString();
        String Bridecontact=Bride_contact.getText().toString();
        String Bridemother=Bride_mother.getText().toString();
        String Bridefather=Bride_father.getText().toString();
        String Groomname=Groom_name.getText().toString();
        String Groomcontact=Groom_contact.getText().toString();
        String Groommother=Groom_mother.getText().toString();
        String GroomFather=Groom_father.getText().toString();
        String Bride=BrideId.getText().toString();
        String Groom=GroomId.getText().toString();
      final  String Email=BEmail.getText().toString();
        String SpiritualParent=Spriritual_parent.getText().toString();
        String WeddingVenue=Venue.getSelectedItem().toString();

        if(!validateName()  |!validateBridecontact() | !validateGroom() |!validateBridecontact() |!validateGroomcontact() |!validateBridemother() |!validateBridefather() |!validateGroommother() |!validateGroomfather() |!validateSpriritualparent()  |!validateEmail()  |!validateBrideId()  |!validateGroomId() ) {
            return;
        }
        mRef= FirebaseDatabase.getInstance().getReference().child("WeddRequest");
        mRef.orderByChild("REmail").equalTo(Email)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    String WeddRequestId = mRef.child("WeddRequest").push().getKey();
                    Map<String, Object> map = new HashMap<>();
                    map.put("RBridename", Bride_name.getText().toString());
                    map.put("RBridecontact", Bride_contact.getText().toString());
                    map.put("RBridemother", Bride_mother.getText().toString());
                    map.put("RBridefather", Bride_father.getText().toString());
                    map.put("RGroomname", Groom_name.getText().toString());
                    map.put("RGroomcontact", Groom_contact.getText().toString());
                    map.put("RGroommother", Groom_mother.getText().toString());
                    map.put("RGroomfather", Groom_father.getText().toString());
                    map.put("RSpiritualparent", Spriritual_parent.getText().toString());
                    map.put("RVenue", Venue.getSelectedItem().toString());
                    map.put("REmail", BEmail.getText().toString());
                    map.put("RBrideId", BrideId.getText().toString());
                    map.put("RGroomId", GroomId.getText().toString());
                    mRef.child(WeddRequestId).setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Bride_name.setText(" ");
                                    Bride_contact.setText(" ");
                                    Groom_name.setText(" ");
                                    Groom_contact.setText(" ");
                                    Bride_mother.setText(" ");
                                    Bride_father.setText(" ");
                                    Groom_mother.setText(" ");
                                    Groom_father.setText(" ");
                                    Spriritual_parent.setText(" ");
                                    BrideId.setText(" ");
                                    GroomId.setText(" ");
                                    BEmail.setText(" ");
                                    Toast.makeText(Hopewedding.this, "Request sent,for approval", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Hopewedding.this, "Could Not Send", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {Toast.makeText(Hopewedding.this, "Email exists", Toast.LENGTH_SHORT).show();}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        }

    }

