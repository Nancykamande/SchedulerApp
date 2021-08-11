package com.example.schedulerapp.Adventures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schedulerapp.Adapters.CustomOnItemSelectedListener;
import com.example.schedulerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private EditText inputEmail, inputPassword,Name,Phoneno;
    Spinner Church;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    String email;
   // private Spinner spinner1;



    private Boolean validatePhone(){
        String val4=  Phoneno.getText().toString();

        if(val4.isEmpty()){
            Phoneno.setError("Field Cannot be Empty");
            return false;
        }else if(val4.length()<10){
            Phoneno.setError("Phone invalid");
            return false;
        }else  if(val4.length()>10){
            Phoneno.setError("phone invalid");
            return false;
        }
        else {
            Phoneno.setError(null);
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        Name = (EditText) findViewById(R.id.Username);
        Phoneno = (EditText) findViewById(R.id.phone);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
       // Church = (Spinner) findViewById(R.id.spinner1);



        mRef= FirebaseDatabase.getInstance().getReference();
        currentUser=auth.getCurrentUser();
        mStorage=FirebaseStorage.getInstance();
        storageReference=mStorage.getReferenceFromUrl("gs://scheduler-app-b31f0.appspot.com");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

//    private void addListenerOnSpinnerItemSelection() {
//        spinner1 = (Spinner) findViewById(R.id.spinner1);
//        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//    }

    private void registerUser() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if(!validatePhone()){
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String email=inputEmail.getText().toString();
                    String name=Name.getText().toString();
                    String phone= Phoneno .getText().toString();
                    //String church= Church.getSelectedItem().toString();


                    HashMap<String, Object> signup=new HashMap<>();
                    signup.put("Email",email);
                    signup.put("Name",name);
                    signup.put("Phoneno",phone);
                   // signup.put("Church",church);

                    signup.put("user_id", task.getResult().getUser().getUid());

                    mRef.child("RegisteredUsers").child(task.getResult().getUser().getUid()).setValue(signup).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBar.setVisibility(View.GONE);
                            inputEmail.setText(" ");
                            Name.setText(" ");
                            Phoneno.setText(" ");
                            inputPassword.setText(" ");
                            Toast.makeText(Signup.this, "Registered Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    btnSignUp.setVisibility(View.VISIBLE);
                    Toast.makeText(Signup.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.VISIBLE);

                Toast.makeText(Signup.this,"Failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}


