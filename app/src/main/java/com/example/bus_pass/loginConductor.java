package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class loginConductor extends AppCompatActivity {

    EditText p,q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_conductor);
        p=(EditText)findViewById(R.id.conductorUsername);
        q=(EditText)findViewById(R.id.conductorPassword);
    }

    public void conductorNowLogin(View view) {
        String naam = p.getText().toString();
        String paas = q.getText().toString();
        if(!naam.isEmpty() && !paas.isEmpty()){
            if(naam.equals("likhith") && paas.equals("likhith")){
                p.setText("");
                q.setText("");
                Intent start = new Intent(loginConductor.this,conductorHomePage.class);
                startActivity(start);
            }
            else{
                Toast.makeText(this, "Invalid Crendentials", Toast.LENGTH_SHORT).show();
                p.setText("");
                q.setText("");
            }
        }
        else{
            Toast.makeText(this, "Fill Complete Details", Toast.LENGTH_SHORT).show();
        }
    }
}