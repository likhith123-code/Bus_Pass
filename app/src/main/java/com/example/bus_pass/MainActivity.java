package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginPage(View view) {
        Intent i1 = new Intent(this,LoginPage.class);
        startActivity(i1);
    }

    public void registerPage(View view) {
        Intent i2 = new Intent(this,registrationPage.class);
        startActivity(i2);
    }

    public void conductorPage(View view) {
        Intent i3 = new Intent(this,loginConductor.class);
        startActivity(i3);
    }

    public void adminPage(View view) {
        Intent adminintent = new Intent(this,adminLogin.class);
        startActivity(adminintent);
    }
}