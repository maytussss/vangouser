package com.example.vango_user;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanHome extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    // PLEASE ADD: if the value already exist (aka. value != null), just direct it to TicketExist class

    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_home);
        getSupportActionBar().hide();

    }
    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
//       barcodeReader.playBeep();
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

        // Check detected text is empty?
        if (TextUtils.isEmpty(barcode.displayValue)) {
            Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
        }
        else
            {
            // ticket details activity by passing barcode

            String code =  barcode.displayValue;
            getQR(code);

            Intent intent = new Intent(ScanHome.this, BillPayment.class);
            intent.putExtra("code", barcode.displayValue);
            startActivity(intent);
        }
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

    public static String getQR(String code){
        return code;
    }
}

