package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class adminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
    }

    public void pendingApplications(View view) {
        Intent pend = new Intent(this,pendingApplications.class);
        startActivity(pend);
    }

    public void verifyApplication(View view) {
            Intent ver = new Intent(this,verificationPage.class);
            startActivity(ver);
    }

    public void routesAdmin(View view) {
        Intent adminrou = new Intent(this,routesPage.class);
        startActivity(adminrou);
    }
}