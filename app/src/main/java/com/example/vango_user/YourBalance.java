package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class YourBalance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_balance);
        getSupportActionBar().hide();

        final String code_from = getIntent().getStringExtra("code_from");
        final String code_to = getIntent().getStringExtra("code_to");
        final String code_price = getIntent().getStringExtra("code_price");

        findViewById(R.id.purchase_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(YourBalance.this, SuccessBillPayment.class);
                            intent.putExtra("code_from",code_from);
                            intent.putExtra("code_to",code_to);
                            intent.putExtra("code_price",code_price);
                            startActivity(intent);
                    }
                }
        );
    }
    public void onBackPressed() {
        Intent intent = new Intent(YourBalance.this, MainActivity.class);
        startActivity(intent);
    }
}
