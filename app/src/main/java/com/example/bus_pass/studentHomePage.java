package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class studentHomePage extends AppCompatActivity {
    TextView displayname;
    String profile,rollnumberStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        displayname = (TextView)findViewById(R.id.welcome);
        Intent i=getIntent();
        profile = i.getStringExtra("usernameIntro");
        rollnumberStudent=i.getStringExtra("rollnumberWallet");
        displayname.setText("Hello "+profile);
    }

    public void walletPage(View view) {
        Intent wallet = new Intent(studentHomePage.this,studentWallet.class);
        wallet.putExtra("RollNumber",rollnumberStudent);
        startActivity(wallet);
    }

    public void applyPage(View view) {
        Intent applyNew = new Intent(studentHomePage.this,buspassApplyPage.class);
        applyNew.putExtra("rollNumber",rollnumberStudent);
        startActivity(applyNew);
    }

    public void myPassesPage(View view) {
        Intent mypass = new Intent(studentHomePage.this,myPass.class);
        mypass.putExtra("rollNumber",rollnumberStudent);
        startActivity(mypass);
    }

    public void getBusRoute(View view) {
        Intent nin = new Intent(studentHomePage.this,routesPage.class);
        startActivity(nin);
    }

    public void renewalHomePage(View view) {
//        Intent mo = new Intent(studentHomePage.this,renewalPage.class);
//        startActivity(mo);
        Toast.makeText(this, "Will be Added Soon", Toast.LENGTH_LONG).show();
    }
}