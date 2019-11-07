package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class YourBalance extends AppCompatActivity {

    String uid;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    Button balance_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_balance);
        getSupportActionBar().hide();

        balance_amount = findViewById(R.id.balance_amount);
        getUser();
        findViewById(R.id.backbt3).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(YourBalance.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        findViewById(R.id.balance_amount).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(YourBalance.this, BillPayment.class);
                            startActivity(intent);
                    }
                }
        );
    }
    public void onBackPressed() {
        Intent intent = new Intent(YourBalance.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            //usernameDisplay.setText(uid);
        }
        else {
            uid = "aU6PtXs2QURfUOD3rdy3HKb6l7X2";
        }

        DocumentReference docRef = database.collection("user").document(uid);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Double coin = documentSnapshot.getDouble("coin");
                            balance_amount.setText(String.valueOf(coin));
                        }
                    }
                });
    }
}
