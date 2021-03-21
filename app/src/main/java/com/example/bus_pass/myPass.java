package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class myPass extends AppCompatActivity {

    TextView n1,n2,n3,n4,n5;
    FirebaseDatabase myPassDatabase;
    DatabaseReference myrefMyPass;
    private StorageReference mystorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pass);
        n1 = (TextView)findViewById(R.id.myPassId);
        n2 = (TextView)findViewById(R.id.myPassRollNumber);
        n3 = (TextView)findViewById(R.id.myPassStatus);
        n4 = (TextView)findViewById(R.id.myPassType);
        n5 = (TextView)findViewById(R.id.myPassRenewal);

        myPassDatabase = FirebaseDatabase.getInstance();
        myrefMyPass = myPassDatabase.getReference().child("BusPasses");

        Intent tmp = getIntent();
        String n = tmp.getStringExtra("rollNumber");
        // for getting image
        myrefMyPass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(n.toLowerCase()).exists()){
                    n2.setText("Roll Number: "+n.toUpperCase());
                    String status = snapshot.child(n.toLowerCase()).child("status").getValue().toString();
                    String type = snapshot.child(n.toLowerCase()).child("type").getValue().toString();
                    String id = snapshot.child(n.toLowerCase()).child("passId").getValue().toString();
                    String renew = snapshot.child(n.toLowerCase()).child("renewal").getValue().toString();
                    if(status.equals("verified")){
                        mystorage = FirebaseStorage.getInstance().getReference().child("allPasses/verify.png");
                        try{
                            final File localFile = File.createTempFile("verify","png");
                            mystorage.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            ((ImageView)findViewById(R.id.qrcode)).setImageBitmap(bitmap);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        mystorage = FirebaseStorage.getInstance().getReference().child("allPasses/pending.jpg");
                        try{
                            final File localFile = File.createTempFile("pending","jpg");
                            mystorage.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            ((ImageView)findViewById(R.id.qrcode)).setImageBitmap(bitmap);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    n1.setText("Pass ID : "+id);
                    n3.setText("Pass Status : "+status);
                    n4.setText("Pass Type : "+type);
                    n5.setText("Renewal Date : "+renew);
                }
                else{
                    n2.setText(n.toUpperCase());
                    n3.setText("No Pass Found");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}