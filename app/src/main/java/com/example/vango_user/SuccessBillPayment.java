package com.example.vango_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class SuccessBillPayment extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    String uid;
    //String tripDocId = sp.getString("barcode", "mcoD1l1Naa2jp0g5vj7h");
    String tripDocId;
    TextView fromTXT;
    TextView toTXT ;
    TextView priceTXT;
    TextView balance_read_text_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_bill_payment);
        getSupportActionBar().hide();

        fromTXT = this.findViewById(R.id.from_read_text_s);
        toTXT = this.findViewById(R.id.to_read_text_s);
        priceTXT = this.findViewById(R.id.price_read_text_s);
        balance_read_text_s = this.findViewById(R.id.balance_read_text_s);

        SharedPreferences ticketPref = getSharedPreferences("ticket",MODE_PRIVATE);
        tripDocId = ticketPref.getString("ticket","");

        //tripDocId =  getIntent().getStringExtra("code");

        if (TextUtils.isEmpty(tripDocId)) {
            Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            getTripDetail();
            getBalance();
        }

        // Button
        // See Ticket Button
        findViewById(R.id.goticketbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SuccessBillPayment.this, TicketActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );


    }
    public void onBackPressed() {
        Intent intent = new Intent(SuccessBillPayment.this, MainActivity.class);
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

    private void getBalance(){
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
                            balance_read_text_s.setText(String.valueOf(coin));
                        }
                    }
                });
    }
}
