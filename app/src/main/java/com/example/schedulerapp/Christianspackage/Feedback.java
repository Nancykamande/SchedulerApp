package com.example.schedulerapp.Christianspackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulerapp.Admin.Wedding;
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

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    EditText Feedback;
    Button save;
    private FirebaseStorage mStorage;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Feedback = findViewById(R.id.feedback);
        save=findViewById(R.id.submit);
        mStorage= FirebaseStorage.getInstance();
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               registerEvent();
           }
       });
    }

    private void registerEvent() {
        mRef= FirebaseDatabase.getInstance().getReference().child("Feedback");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String FeedbackId = mRef.push().getKey();
                Map<String, Object> map = new HashMap<>();
                map.put("Comment", Feedback.getText().toString());
                mRef.child(FeedbackId).setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Feedback.setText(" ");

                                Toast.makeText(Feedback.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Feedback.this, "Could Not Save", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
