package com.example.bus_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class adminLogin extends AppCompatActivity {
    EditText b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        b1= (EditText)findViewById(R.id.adminUsername);
        b2= (EditText)findViewById(R.id.adminPassword);
        b3=(EditText)findViewById(R.id.adminPin);
    }

    public void adminLoginNow(View view) {
        String auser = b1.getText().toString();
        String apass = b2.getText().toString();
        String apin = b3.getText().toString();

        if(!auser.isEmpty() && !apass.isEmpty() && !apin.isEmpty()){
            if(auser.equals("likhith1520") && apass.equals("open123") && apin.equals("1234")){
                b1.setText("");
                b2.setText("");
                b3.setText("");
                Intent inadminLogin = new Intent(this,adminHomePage.class);
                startActivity(inadminLogin);

            }
            else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                b1.setText("");
                b2.setText("");
                b3.setText("");
            }
        }
        else{
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_LONG).show();
        }
    }
}