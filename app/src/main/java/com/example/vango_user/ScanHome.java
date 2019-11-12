package com.example.vango_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanHome extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    BarcodeReader barcodeReader;
    SharedPreferences sp;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_home);
        getSupportActionBar().hide();

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }
    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

        // Check detected text is empty?
        if (TextUtils.isEmpty(barcode.displayValue)) {
            //Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
        }
        else {

            int tripExist = getTripDetail(barcode.displayValue);
            if (tripExist == 1) {
                String code = barcode.displayValue;
                sp = getSharedPreferences("barcode", MODE_PRIVATE);
                sp.edit().putString("barcode", code).apply();


                Intent intent = new Intent(ScanHome.this, BillPayment.class);
                intent.putExtra("code", barcode.displayValue);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(ScanHome.this, not_correct_qrcode.class);
                startActivity(intent);
                finish();
            }
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
        //Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(ScanHome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private int getTripDetail(String tripDocId){
        DocumentReference docRef = database.collection("trip").document(tripDocId);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            check = 1;
                        }
                    }
                });
        docRef.get()
                .addOnFailureListener(new OnFailureListener()  {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            check = 0;
                    }
                });
        return check;
    }
}

