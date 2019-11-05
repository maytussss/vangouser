package com.example.vango_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SuccessBillPayment extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    String tripDocId = "mcoD1l1Naa2jp0g5vj7h";
    TextView fromTXT;
    TextView toTXT ;
    TextView priceTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_bill_payment);
        getSupportActionBar().hide();

        fromTXT = this.findViewById(R.id.from_read_text_s);
        toTXT = this.findViewById(R.id.to_read_text_s);
        priceTXT = this.findViewById(R.id.price_read_text_s);

        getTripDetail();

        // Button
        // Decline Button
        findViewById(R.id.goticketbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SuccessBillPayment.this, TicketActivity.class);
                        startActivity(intent);
                    }
                }
        );


    }
    public void onBackPressed() {
        Intent intent = new Intent(SuccessBillPayment.this, MainActivity.class);
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
}
