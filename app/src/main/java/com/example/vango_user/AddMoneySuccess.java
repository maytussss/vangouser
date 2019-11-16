package com.example.vango_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button proceed_y;
    Button proceed_n;
    TextView txtAmountProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_success);
        getSupportActionBar().hide();



//        AlertDialog.Builder builder = new AlertDialog.Builder(AddMoneySuccess.this);
//        builder.setCancelable(false); // To protect press back navigation.
//        builder.setMessage("Do you want to proceed the payment");
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                int cc = getIntent().getIntExtra("price", price);
//                updateCoin(cc);
//                Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        builder.show();
        proceed_y = (Button)findViewById(R.id.proceed_yes);
        proceed_n = (Button)findViewById(R.id.proceed_no);
        txtAmountProceed = (TextView)findViewById(R.id.txt_amount_proceed);
        int show_cc = getIntent().getIntExtra("price", price);
        txtAmountProceed.setText(String.valueOf(show_cc));

        proceed_y.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cc = getIntent().getIntExtra("price", price);
                        updateCoin(cc);
                    }
                }
        );
        proceed_n.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
    public void onBackPressed() {
        Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
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
                            Intent intent = new Intent(AddMoneySuccess.this, YourBalance.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
