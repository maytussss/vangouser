package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();
        findViewById(R.id.Signinbutton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginPage.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        findViewById(R.id.CreateAccountbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginPage.this,RegisterPage.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
