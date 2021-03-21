package com.example.bus_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registrationPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myrefRegister;
    EditText username,password,repassword,rollno,phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        database = FirebaseDatabase.getInstance();
        username =(EditText)findViewById(R.id.usernameRegister);
        password=(EditText)findViewById(R.id.passwordRegister);
        repassword=(EditText)findViewById(R.id.repasswordRegister);
        rollno = (EditText)findViewById(R.id.rollnoRegister);
        phoneno = (EditText)findViewById(R.id.phoneRegister);

    }

    public void registerNow(View view) {
        final String uname1 = username.getText().toString();
        final String pass1 = password.getText().toString();
        final String rpass1 = repassword.getText().toString();
        final String roll1 = rollno.getText().toString();
        final String phone1 = phoneno.getText().toString();
        myrefRegister = database.getReference().child("Students");
        if(!uname1.isEmpty() && !pass1.isEmpty() && !rpass1.isEmpty() && !roll1.isEmpty() && !phone1.isEmpty()){
            myrefRegister.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if(datasnapshot.child(roll1.toLowerCase()).exists()){
                        Toast.makeText(registrationPage.this, "User Already Exists", Toast.LENGTH_LONG).show();
                        username.setText("");
                        password.setText("");
                        repassword.setText("");
                        rollno.setText("");
                        phoneno.setText("");
                    }
                    else{
                        if(pass1.equals(rpass1)){
                            myrefRegister.child(roll1.toLowerCase()).child("username").setValue(username.getText().toString());
                            myrefRegister.child(roll1.toLowerCase()).child("rollnumber").setValue(rollno.getText().toString().toLowerCase());
                            myrefRegister.child(roll1.toLowerCase()).child("password").setValue(password.getText().toString());
                            myrefRegister.child(roll1.toLowerCase()).child("phonenumber").setValue(phoneno.getText().toString());
                            myrefRegister.child(roll1.toLowerCase()).child("wallet").setValue("0");
                            Toast.makeText(registrationPage.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                            username.setText("");
                            password.setText("");
                            repassword.setText("");
                            rollno.setText("");
                            phoneno.setText("");
                            Intent done = new Intent(registrationPage.this,LoginPage.class);
                            startActivity(done);
                        }
                        else{
                            Toast.makeText(registrationPage.this, "Passwords Not Matching", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_LONG).show();
        }
    }
}