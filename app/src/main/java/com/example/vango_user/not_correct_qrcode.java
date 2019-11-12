package com.example.vango_user;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class not_correct_qrcode extends AppCompatActivity {
    Button toBack;
    Button toscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_correct_qrcode);
        getSupportActionBar().hide();

        toBack = (Button)findViewById(R.id.back_page_not_correct);
        toscan = (Button)findViewById(R.id.try_again_btn);

        toBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(not_correct_qrcode.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        toscan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(not_correct_qrcode.this,ScanHome.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
    public void onBackPressed() {
        Intent intent = new Intent(not_correct_qrcode.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
