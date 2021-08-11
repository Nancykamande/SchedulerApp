package com.example.schedulerapp.Adventures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schedulerapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class featuresActivity extends AppCompatActivity {
    Button btnAdd, choose;
    ImageView images;
    EditText txtName, price, txtDescription;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private final int Image_Request_Code = 3;
    private StorageTask add;
    //Featureslist_method sitedetails;
    public Uri imguri;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        //storageReference = FirebaseStorage.getInstance().getReference("sites");
        //.child("features")
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnAdd = findViewById(R.id.btnupload);
        txtName = findViewById(R.id.txtName);
        price = findViewById(R.id.price);
        //images = (ImageView) findViewById(R.id.images);
        choose = findViewById(R.id.btnbrowse);
        txtDescription = findViewById(R.id.txtDescription);
        //sitedetails = new Featureslist_method();
        progressDialog = new ProgressDialog(featuresActivity.this);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sitesfeature();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Image_Request_Code&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imguri=data.getData();
        }
    }
    private void sitesfeature() {
        final String name = txtName.getText().toString();
        final String type = price.getText().toString();
        final String description = txtDescription.getText().toString();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File is loading....");
        progressDialog.show();
        final StorageReference sReference=storageReference.child("SitePic"+System.currentTimeMillis()+".jpg");
        final UploadTask selectPic=sReference.putFile(imguri);
        selectPic.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Task<Uri> uriTask=selectPic.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return sReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            final Uri downloadUri=task.getResult();

                            final HashMap<String, Object> map=new HashMap<>();
                            map.put("name",name);
                            map.put("price",type);
                            map.put( "description",description);
                            map.put("url",downloadUri.toString());
                            FirebaseDatabase.getInstance().getReference
                                    ("features").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    txtName.setText(" ");
                                    price.setText(" ");
                                    txtDescription.setText(" ");
                                    Toast.makeText(featuresActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(featuresActivity.this, "Could Not Add", Toast.LENGTH_SHORT).show();


                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"UriTask Failure:"+e.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploading..."+(int)progress+"%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "UriTask Failure:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
