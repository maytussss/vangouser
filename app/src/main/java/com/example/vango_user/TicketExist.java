package com.example.vango_user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TicketExist extends AppCompatActivity {

    //Add activity [Do you want to cancel the old ticket and proceed the new one ]
    // 1. YES(clear exiting value and direct to payment)
    // 2. NO(direct to HOME)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_exist);
    }
}
