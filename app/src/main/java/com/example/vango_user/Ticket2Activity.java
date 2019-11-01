package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Ticket2Activity extends AppCompatActivity {

    ImageButton A1;
    Button A2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket2);
        getSupportActionBar().hide();

        A1 = (ImageButton)findViewById(R.id.backbt2);
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent A1 = new Intent(Ticket2Activity.this,MainActivity.class);
                startActivity(A1);
            }
        });

        A2 = (Button)findViewById(R.id.buybt);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent A2 = new Intent(Ticket2Activity.this,MainActivity.class);
                startActivity(A2);
            }
        });

    }
}
