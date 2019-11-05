package com.example.vango_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BillPayment extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    String tripDocId = "mcoD1l1Naa2jp0g5vj7h";
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

        getTripDetail();


        //-------------COMMENT OUT BECAUSE CODE ERROR------------------------


        /*final String __currentStringCode = getIntent().getStringExtra("code");
        final String[] __separatedCurrentStringCode = __currentStringCode.split(",");
        // try-catch text read QR-Code v1 2019/11/03
        try {
            fromTXT.setText(__separatedCurrentStringCode[0]);
            toTXT.setText(__separatedCurrentStringCode[1]);
            priceTXT.setText(__separatedCurrentStringCode[2]);
        }
        catch(Exception e)
        {
            Intent intent = new Intent(BillPayment.this,ScanHome.class);
            startActivity(intent);
        }*/

        // Button
        // Decline Button
        findViewById(R.id.declinebtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BillPayment.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        /*// Accept Button
        findViewById(R.id.acceptbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(BillPayment.this, YourBalance.class);
                        intent.putExtra("code_from",__separatedCurrentStringCode[0]);
                        intent.putExtra("code_to",__separatedCurrentStringCode[1]);
                        intent.putExtra("code_price",__separatedCurrentStringCode[2]);
                        startActivity(intent);
                    }
                }
        );*/

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
                                                    startActivity(new Intent(getApplicationContext(), SuccessBillPayment.class));
                                                }
                                                else{
                                                    startActivity(new Intent(getApplicationContext(), FailBillPayment.class));
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}

