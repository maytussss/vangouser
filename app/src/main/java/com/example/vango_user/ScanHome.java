package com.example.vango_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanHome extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    BarcodeReader barcodeReader;

    //ImageButton __back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_home);
        getSupportActionBar().hide();

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

        /*__back = (ImageButton)findViewById(R.id.back_btn_title_bar);
        __back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b1 = new Intent(ScanHome.this,MainActivity.class);
                startActivity(b1);
            }
        });*/
    }
    @Override
    public void onScanned(Barcode barcode) {

        String code =  barcode.displayValue;
        Intent intent = new Intent(ScanHome.this, BillPayment.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(ScanHome.this, MainActivity.class);
        startActivity(intent);
    }

}

