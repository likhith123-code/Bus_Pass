package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class buspassApplyPage extends AppCompatActivity {
    TextView e1;
    TextView e2;
    TextView e3;
    static int count=0;
    String amount;
    String deduction;
    EditText phno;
    String type;
    String updated;
    EditText passw;
    FirebaseDatabase databaseApply;
    DatabaseReference myrefApply;
    DatabaseReference myrefCheck;
    DatabaseReference myrefPending;
    // this is for adding into pending database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buspass_apply_page);
        databaseApply= FirebaseDatabase.getInstance();
        Spinner mySpinner = (Spinner) findViewById(R.id.passSpinner);
        Intent in = getIntent();
        String def =in.getStringExtra("rollNumber");
        // by default set the number of student in the form automatically
        e1 = (TextView) findViewById(R.id.passRollNumber);
        e2 = (TextView) findViewById(R.id.passHint);
        e3 = (TextView) findViewById(R.id.passPrice);
        phno = (EditText)findViewById(R.id.passMobileNumber) ;
        passw = (EditText)findViewById(R.id.passPassword);
        e1.setText("Applicant : "+def.toUpperCase());
        e2.setText("Note : Once Applied amount will be deducted from your wallet");
        e3.setText("Total Amount : 120");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(buspassApplyPage.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type));
        // create array in Strings.xml and add it to here
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set this adapter to our spinner
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                type = (String)parent.getItemAtPosition(position);
                if(type.equals("Monthly Pass"))
                    e3.setText("Total Amount : 120");
                if(type.equals("Semester Pass"))
                    e3.setText("Total Amount : 500");
                if(type.equals("Annual Pass"))
                    e3.setText("Total Amount : 850");
                if(type.equals("Special Pass"))
                    e3.setText("Total Amount : 2800");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void passApply(View view) {
        final String ppass = passw.getText().toString();
        final String pphone = phno.getText().toString();
        Intent in2 = getIntent();
        final String rroll =in2.getStringExtra("rollNumber");
        myrefCheck =databaseApply.getReference().child("Students");
        myrefPending = databaseApply.getReference().child("pending");
        myrefApply = databaseApply.getReference().child("BusPasses");

        // apply is for creating and storing buspass data
        if(!ppass.isEmpty() && !pphone.isEmpty()){
            myrefCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                    String orgpass = snapshot1.child(rroll.toLowerCase()).child("password").getValue().toString();
                    String walletAmount = snapshot1.child(rroll.toLowerCase()).child("wallet").getValue().toString();
                    String naam = snapshot1.child(rroll.toLowerCase()).child("username").getValue().toString();
                    int wAmount = Integer.parseInt(walletAmount);
                    // getting current wallet amount
                    if(ppass.equals(orgpass))
                    {
                        myrefApply.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.child(rroll.toLowerCase()).exists()){
                                    Toast.makeText(buspassApplyPage.this, "Already Applied Check My Pass Page", Toast.LENGTH_LONG).show();
                                    passw.setText("");
                                    phno.setText("");
                                }
                                else {
                                    if(type.equals("Monthly Pass")){
                                        amount = "120";
                                        deduction = "120";
                                    }
                                    if(type.equals("Semester Pass")){
                                        amount = "500";
                                        deduction="500";
                                    }
                                    if(type.equals("Annual Pass")){
                                        amount = "850";
                                        deduction="850";
                                    }
                                    if(type.equals("Special Pass")){
                                        amount = "2800";
                                        deduction="2800";
                                    }
                                    int dAmount = Integer.parseInt(deduction);
                                    if(wAmount>=dAmount) {
                                        int nAmount = (wAmount-dAmount);
                                        updated = Integer.toString(nAmount);
                                        // updating new wallet amount
                                        myrefApply.child(rroll.toLowerCase()).child("rollno").setValue(rroll.toLowerCase());
                                        myrefApply.child(rroll.toLowerCase()).child("status").setValue("not verified");
                                        myrefApply.child(rroll.toLowerCase()).child("type").setValue(type.toString());
                                        myrefApply.child(rroll.toLowerCase()).child("valid").setValue("no");
                                        myrefApply.child(rroll.toLowerCase()).child("passId").setValue("Not Generated");
                                        myrefApply.child(rroll.toLowerCase()).child("amount").setValue(amount.toString());
                                        myrefApply.child(rroll.toLowerCase()).child("renewal").setValue("not verified");

                                         myrefPending.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                                               myrefPending.child(rroll.toLowerCase()).child("rollno").setValue(rroll.toLowerCase());
                                               myrefPending.child(rroll.toLowerCase()).child("status").setValue("not verified");
                                               myrefPending.child(rroll.toLowerCase()).child("type").setValue(type.toString());
                                               myrefPending.child(rroll.toLowerCase()).child("valid").setValue("no");
                                               myrefPending.child(rroll.toLowerCase()).child("passId").setValue("Not Generated");
                                               myrefPending.child(rroll.toLowerCase()).child("amount").setValue(amount.toString());
                                               myrefApply.child(rroll.toLowerCase()).child("renewal").setValue("not verified");
                                           }
                                           @Override
                                           public void onCancelled(@NonNull DatabaseError error) {

                                           }
                                          });



                                        Toast.makeText(buspassApplyPage.this, "Applied Successfully", Toast.LENGTH_LONG).show();
                                        passw.setText("");
                                        phno.setText("");
                                        myrefCheck.child(rroll.toLowerCase()).child("wallet").setValue(updated);
                                        Intent done = new Intent(buspassApplyPage.this,studentHomePage2.class);
                                        // home page 2 is duplicate page because home page 1 already has one intent so it overrides
                                        // so use other duplicate page
                                        done.putExtra("rollnumberWallet",rroll.toLowerCase());
                                        done.putExtra("usernameIntro",naam);
                                        startActivity(done);
                                    }
                                    else{
                                        Toast.makeText(buspassApplyPage.this, "Insufficient Wallet Amount", Toast.LENGTH_LONG).show();
                                        passw.setText("");
                                        phno.setText("");
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(buspassApplyPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else{
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
        // check is for checking the password and roll number match or not
    }
}