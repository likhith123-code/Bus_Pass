package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class studentHomePage2 extends AppCompatActivity {
    TextView displayname2;
    String profile2,rollnumberStudent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page2);
        displayname2 = (TextView)findViewById(R.id.welcome2);
        Intent ii=getIntent();
        profile2 = ii.getStringExtra("usernameIntro");
        rollnumberStudent2=ii.getStringExtra("rollnumberWallet");
        displayname2.setText("Hello "+profile2);
    }

    public void walletPage2(View view) {
        Intent wallet = new Intent(studentHomePage2.this,studentWallet.class);
        wallet.putExtra("RollNumber",rollnumberStudent2);
        startActivity(wallet);
    }

    public void applyPage2(View view) {
        Intent applyNew = new Intent(studentHomePage2.this,buspassApplyPage.class);
        applyNew.putExtra("rollNumber",rollnumberStudent2);
        startActivity(applyNew);
    }

    public void myPassesPage2(View view) {
        Intent mypass = new Intent(studentHomePage2.this,myPass.class);
        mypass.putExtra("rollNumber",rollnumberStudent2);
        startActivity(mypass);
    }

    public void getBusRoute2(View view) {
        Intent nin1 = new Intent(studentHomePage2.this,routesPage.class);
        startActivity(nin1);
    }

    public void renewalHomePage2(View view) {
//        Intent mo = new Intent(studentHomePage2.this,renewalPage.class);
//        startActivity(mo);
        Toast.makeText(this, "Will be Added Soon", Toast.LENGTH_LONG).show();
    }
}