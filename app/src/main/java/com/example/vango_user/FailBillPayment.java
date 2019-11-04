package com.example.vango_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FailBillPayment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_bill_payment);
        getSupportActionBar().hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(FailBillPayment.this);
        builder.setCancelable(false); // To protect press back navigation.
        builder.setMessage("Payment Failure: Not enough money to purchase this payment. Please top up your money and try again.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(FailBillPayment.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(FailBillPayment.this, MainActivity.class);
        startActivity(intent);
    }

}
