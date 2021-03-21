package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.internal.SmartHandler;

import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class verificationPage extends AppCompatActivity {
    EditText k;
    FirebaseDatabase databaseVerify;
    DatabaseReference myrefAdminVerify;
    DatabaseReference myrefAdminPending;
    EditText msg;
    String amt;
    String ntotal;
    String phoneNo;
    String message;
    // one is for adding success status to user pass and one is to delete that item from the pending passes

    DatabaseReference smssending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        databaseVerify = FirebaseDatabase.getInstance();
        ActivityCompat.requestPermissions(verificationPage.this,new String[]{Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        setContentView(R.layout.activity_verification_page);
        msg = (EditText)findViewById(R.id.rejectReason);
        k= (EditText)findViewById(R.id.verifyDetails);

    }

    public void verifyCandidate(View view) {
        String rollCandidate = k.getText().toString();
        myrefAdminPending = databaseVerify.getReference().child("pending");
        myrefAdminVerify = databaseVerify.getReference().child("BusPasses");
        myrefAdminPending.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if(!rollCandidate.isEmpty() && snapshot1.child(rollCandidate.toLowerCase()).exists()){

                    myrefAdminVerify.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myrefAdminVerify.child(rollCandidate.toLowerCase()).child("status").setValue("verified");
                            myrefAdminVerify.child(rollCandidate.toLowerCase()).child("valid").setValue("yes");
                            String passIdCa = rollCandidate.substring(3);
                            myrefAdminVerify.child(rollCandidate.toLowerCase()).child("passId").setValue(passIdCa+"GHZ");
                            String type = snapshot.child(rollCandidate.toLowerCase()).child("type").getValue().toString();
                            int days = 0;
                            if(type.equals("Monthly Pass")){
                                days = 30;
                            }
                            if(type.equals("Semester Pass")){
                               days = 150;
                            }
                            if(type.equals("Annual Pass")){
                                days = 365;
                            }
                            if(type.equals("Special Pass")){
                                days = 365*4;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date()); // Now use today date.
                            c.add(Calendar.DATE, days); // Adding 5 days
                            String output = sdf.format(c.getTime());
                            myrefAdminVerify.child(rollCandidate.toLowerCase()).child("renewal").setValue(output);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    // if success delete only in pending passes
                    // if reject delete in both database
                    sendSMS();

                    DatabaseReference delete = FirebaseDatabase.getInstance().getReference("pending").child(rollCandidate.toLowerCase());
                    delete.removeValue();
                    Toast.makeText(verificationPage.this, "Verified Successfully", Toast.LENGTH_LONG).show();
                    k.setText("");
                    Intent kum = new Intent(verificationPage.this,adminHomePage.class);
                    startActivity(kum);

                }
                else{
                    Toast.makeText(verificationPage.this, "Candidate Not Found", Toast.LENGTH_LONG).show();
                    k.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void rejectCandidate(View view) {
        String reason = msg.getText().toString();
        String rollCandidate = k.getText().toString();
        myrefAdminPending = databaseVerify.getReference().child("pending");
        myrefAdminVerify = databaseVerify.getReference().child("BusPasses");
        smssending = databaseVerify.getReference().child("Students");

        myrefAdminPending.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!rollCandidate.isEmpty() && snapshot.child(rollCandidate.toLowerCase()).exists()){
                    if(!reason.isEmpty()){
                        amt = snapshot.child(rollCandidate.toLowerCase()).child("amount").getValue().toString();
                        smssending.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String wall = snapshot.child(rollCandidate.toLowerCase()).child("wallet").getValue().toString();
                                Integer w1 = Integer.parseInt(wall);
                                Integer w2 = Integer.parseInt(amt);
                                Integer total = w1+w2;
                                ntotal = Integer.toString(total);
                                smssending.child(rollCandidate.toLowerCase()).child("wallet").setValue(ntotal);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        DatabaseReference delete1 = FirebaseDatabase.getInstance().getReference("pending").child(rollCandidate.toLowerCase());
                        delete1.removeValue();
                        DatabaseReference delete2 = FirebaseDatabase.getInstance().getReference("BusPasses").child(rollCandidate.toLowerCase());
                        delete2.removeValue();

                        Toast.makeText(verificationPage.this, "Rejected Successfully", Toast.LENGTH_LONG).show();
                        k.setText("");
                        Intent num1 = new Intent(verificationPage.this,adminHomePage.class);
                        startActivity(num1);
                    }
                    else{
                        Toast.makeText(verificationPage.this, "Enter Reason", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(verificationPage.this, "Candidate Not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void sendSMS(){
        message = "Sent Message";
        phoneNo = "+916303125376";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo,null,message,null,null);
        Toast.makeText(this, "SMS SENT SUCCESSFULLY", Toast.LENGTH_LONG).show();
    }

}
