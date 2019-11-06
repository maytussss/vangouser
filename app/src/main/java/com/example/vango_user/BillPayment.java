package com.example.vango_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BillPayment extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    //public static final String TAG = "Scan Activity: ";
    //SharedPreferences saveBarcode = getSharedPreferences("saveBarcode",MODE_PRIVATE);
    //String tripDocId = saveBarcode.getString("barcode", "");
    //String tripDocId = "mcoD1l1Naa2jp0g5vj7h";
    String tripDocId;
    TextView fromTXT;
    TextView toTXT ;
    TextView priceTXT;
    Button acceptbtn;
    String uid;
    Double coin;
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        getSupportActionBar().hide();

        fromTXT = this.findViewById(R.id.from_read_text);
        toTXT = this.findViewById(R.id.to_read_text);
        priceTXT = this.findViewById(R.id.price_read_text);
        acceptbtn = findViewById(R.id.acceptbtn);

        tripDocId =  getIntent().getStringExtra("code");

        if (TextUtils.isEmpty(tripDocId)) {
            Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(BillPayment.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            getTripDetail();
        }
        findViewById(R.id.declinebtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BillPayment.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        // Accept Button
        acceptbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin();

            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(BillPayment.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getTripDetail(){
        DocumentReference docRef = database.collection("trip").document(tripDocId);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String price = String.valueOf(documentSnapshot.get("price"));
                            priceTXT.setText(price);
                            String start = documentSnapshot.getString("start");
                            fromTXT.setText(start);
                            String destination = documentSnapshot.getString("destination");
                            toTXT.setText(destination);
                        }
                    }
                });
    }

    public void updateCoin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            //usernameDisplay.setText(uid);
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
                            DocumentReference tripDocRef = database.collection("trip").document(tripDocId);
                            tripDocRef.get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                price = documentSnapshot.getDouble("price");
                                                if (coin >= price)
                                                {
                                                    coin -= price;
                                                    docRef.update("coin", coin);

                                                    SharedPreferences ticket = getSharedPreferences("ticket",MODE_PRIVATE);
                                                    ticket.edit().putBoolean("ticket",true).apply();

                                                    Intent intent = new Intent(getApplicationContext(), SuccessBillPayment.class);
                                                    intent.putExtra("code", tripDocId);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    /*String code =  "";
                                                    SharedPreferences saveBarcode = getSharedPreferences("saveBarcode",MODE_PRIVATE);
                                                    SharedPreferences.Editor edit = saveBarcode.edit();
                                                    edit.putString("barcode",code).apply();*/
                                                    startActivity(new Intent(getApplicationContext(), FailBillPayment.class));
                                                    finish();
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}

