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

public class LoginPage extends AppCompatActivity {
    EditText uname,pass;
    FirebaseDatabase database1;
    DatabaseReference myrefLogin;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        database1 = FirebaseDatabase.getInstance();
        uname = (EditText)findViewById(R.id.usernameLogin);
        pass = (EditText)findViewById(R.id.passwordLogin);

    }
    public void loginNow(View view) {
        final String loginusername = uname.getText().toString();
        final String loginpassword = pass.getText().toString();
        myrefLogin = database1.getReference().child("Students");
        if(!loginusername.isEmpty() && !loginpassword.isEmpty()){
            myrefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(loginusername.toLowerCase()).exists()){
                        String reqpassword = snapshot.child(loginusername.toLowerCase()).child("password").getValue().toString();
                        if(loginpassword.equals(reqpassword)){
                            Toast.makeText(LoginPage.this, "LoggedIn Successfully", Toast.LENGTH_SHORT).show();
                            uname.setText("");
                            pass.setText("");
                            Intent loggedInto = new Intent(LoginPage.this,studentHomePage.class);
                            String display = snapshot.child(loginusername.toLowerCase()).child("username").getValue().toString();
                            String forWallet = snapshot.child(loginusername.toLowerCase()).child("rollnumber").getValue().toString();
                            loggedInto.putExtra("usernameIntro",display);
                            // this is for displaying user name
                            loggedInto.putExtra("rollnumberWallet",forWallet);
                            // this for adding money into wallet to track rollnumber, applying pass etc
                            startActivity(loggedInto);
                        }
                        else {
                            Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            uname.setText("");
                            pass.setText("");
                        }
                    }
                    else{
                        Toast.makeText(LoginPage.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_SHORT).show();
        }
    }
}