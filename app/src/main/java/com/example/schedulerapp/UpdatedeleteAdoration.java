package com.example.schedulerapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class UpdatedeleteAdoration extends AppCompatActivity {
    private Button btnUpdate,btnDelete,btnback;
    private EditText date,starttime,intention;
    private Spinner venue,celebrant;
    private DatabaseReference Ref;

    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    public interface DataStatus{
        void DataIsUpdated();
        void DataIsDeleted();
    }
    private String mdate;
    private String mstarttime;
    private String mintention;
    private String mcelebrant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelete_adoration);


        mdate= getIntent().getStringExtra("Adorationdate");
        mstarttime=getIntent().getStringExtra("Adorationstarttime");
        mintention=getIntent().getStringExtra("Adorationintention");
        mcelebrant=getIntent().getStringExtra("Adorationcelebrant");

        btnUpdate= findViewById(R.id.Update);
        btnDelete= findViewById(R.id.Delete);
        btnback= findViewById(R.id.Signout);
        date = (EditText) findViewById(R.id.AdorationDate);
        starttime = (EditText) findViewById(R.id.AdorationTime);
        intention = (EditText) findViewById(R.id.MassIntention2);
        venue = (Spinner) findViewById(R.id.spinner1);
        celebrant= (Spinner) findViewById(R.id.spinner2);
       // Ref = FirebaseDatabase.getInstance().getReference();
        Ref= FirebaseDatabase.getInstance().getReference().child("Adoration").child("Adorationdetails");

   btnUpdate.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String Sdate=date.getText().toString();
           String Starttime=starttime.getText().toString();
           String Aintention=intention.getText().toString();
           String text1 = venue.getSelectedItem().toString();
           String text2 = celebrant.getSelectedItem().toString();

       }
   });













        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {



                String AdorationId=Ref.push().getKey();
                Map<String,Object> map = new HashMap<>();
                map.put("AdorationId",AdorationId);
                          Ref.child(AdorationId)
                        .updateChildren(map)
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                           }
                       });
               
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {

                Ref= FirebaseDatabase.getInstance().getReference().child("Adoration").child("Adorationdetails");
                String AdorationId=Ref.push().getKey();

                Ref.child(AdorationId)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), " deleted successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
    public void  updateData(String key,Map map ,final DataStatus dataStatus ){
Ref.child(key).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        dataStatus.DataIsUpdated();

    }
});


    }
    public void deleteData(String key,final DataStatus dataStatus){
        Ref.child(key).setValue(null);
    }

private int getIndex_SpinnerItem(Spinner spinner, String item){
        int index=0;
        for(int i =0; i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(item)){
                index=i;
                break;
            }
        }
return index;
}
}
