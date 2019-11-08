package com.example.vango_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMoneySuccess extends AppCompatActivity {
    String uid;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    Double coin;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_bill_payment);
        getSupportActionBar().hide();

        int cc = getIntent().getIntExtra("price", price);
        updateCoin(cc);

        AlertDialog.Builder builder = new AlertDialog.Builder(AddMoneySuccess.this);
        builder.setCancelable(false); // To protect press back navigation.
        builder.setMessage("Do you want to proceed the payment");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(AddMoneySuccess.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateCoin(final double pricex){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        else {
            uid = "aU6PtXs2QURfUOD3rdy3HKb6l7X2";
        }

        final DocumentReference docRef = database.collection("user").document(uid);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            coin = documentSnapshot.getDouble("coin");
                            coin += pricex;
                            docRef.update("coin", coin);
                        }
                    }
                });
    }
}