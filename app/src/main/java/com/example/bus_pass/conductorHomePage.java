package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class conductorHomePage extends AppCompatActivity {
    FirebaseDatabase databaseConductor;
    DatabaseReference myrefConductor;
    TextView one,two,three,four;
    EditText five;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_home_page);
        databaseConductor = FirebaseDatabase.getInstance();
        one = (TextView)findViewById(R.id.stuconId);
        two = (TextView)findViewById(R.id.stuconType);
        three = (TextView)findViewById(R.id.stuconVerify);
        four = (TextView)findViewById(R.id.stuconRenewal);
        five = (EditText)findViewById(R.id.conductorRollNumber);

    }

    public void conductorCheck(View view) {
        myrefConductor = databaseConductor.getReference().child("BusPasses");
        String rollCon = five.getText().toString();
        myrefConductor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!rollCon.isEmpty()){
                    if(snapshot.child(rollCon.toLowerCase()).exists()){
                        String id = snapshot.child(rollCon.toLowerCase()).child("passId").getValue().toString();
                        String type = snapshot.child(rollCon.toLowerCase()).child("type").getValue().toString();
                        String verify = snapshot.child(rollCon.toLowerCase()).child("status").getValue().toString();
                        String renew = snapshot.child(rollCon.toLowerCase()).child("renewal").getValue().toString();
                        one.setText("Bus Pass ID : "+id);
                        two.setText("Bus Pass Type : "+type);
                        three.setText("Verification Status : "+verify);
                        four.setText("Renewal Date : "+renew);
                        five.setText("");
                    }
                    else{
                        Toast.makeText(conductorHomePage.this, "Candidate Not Found", Toast.LENGTH_LONG).show();
                        five.setText("");
                    }
                }
                else{
                    Toast.makeText(conductorHomePage.this, "Enter Number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}