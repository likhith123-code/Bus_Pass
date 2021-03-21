package com.example.bus_pass;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class studentWallet extends AppCompatActivity {
    EditText add;
    TextView available;
    String RollNumber;
    FirebaseDatabase databaseWallet;
    DatabaseReference myrefWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_wallet);
        databaseWallet = FirebaseDatabase.getInstance();
        Intent in = getIntent();
        RollNumber = in.getStringExtra("RollNumber");
        add = (EditText)findViewById(R.id.walletAmount);
        available = (TextView)findViewById(R.id.amountAvailable);
        myrefWallet = databaseWallet.getReference().child("Students");
        myrefWallet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String present1 = snapshot.child(RollNumber).child("wallet").getValue().toString();
                available.setText("Available Amount : "+present1);
                // displaying the amount present in database before adding
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addAmount(View view) {
        myrefWallet = databaseWallet.getReference().child("Students");
        String Add = add.getText().toString();
        if(!Add.isEmpty()){
            myrefWallet.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String present = snapshot.child(RollNumber).child("wallet").getValue().toString();
                    available.setText("Available Amount : "+present);
                    Integer p = Integer.parseInt(present);
                    // converting the present amount to integer
                    Integer n = Integer.parseInt(add.getText().toString());
                    // adding new entered amount
                    Integer now = n+p;
                    // calculating new amount
                    myrefWallet.child(RollNumber).child("wallet").setValue(Integer.toString(now));
                    available.setText("Available Amount : "+now);
                    // displaying updated amount
                    Toast.makeText(studentWallet.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                    add.setText("");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Amount Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }


    }
}