package com.example.schedulerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailSend extends AppCompatActivity {
    EditText email,subject,message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_send);
        email = findViewById(R.id.Email);
        subject = findViewById(R.id.Subject);
        message = findViewById(R.id.Message);
        send=findViewById(R.id.submit);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String mEmail= email.getText().toString();
        String mSubject= subject.getText().toString();
        String mMessage= message.getText().toString();
     JavaMailApi javaMailApi= new JavaMailApi(this, mEmail, mSubject, mMessage);
     javaMailApi.execute();
    }
}
