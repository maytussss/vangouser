package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TicketActivity extends AppCompatActivity {
    ImageButton b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().hide();

        b1 = (ImageButton)findViewById(R.id.backbt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b1 = new Intent(TicketActivity.this,MainActivity.class);
                startActivity(b1);
            }
        });

        b2 = (Button)findViewById(R.id.donebt);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b2 = new Intent(TicketActivity.this,MainActivity.class);
                startActivity(b2);
            }
        });



    }
}
