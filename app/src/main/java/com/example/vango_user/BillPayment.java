package com.example.vango_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    TextView Username;
    TextView BalanceAmount;
    Button acceptbtn;
    String uid;
    Double coin;
    Double price;
    ArrayList<String> tokenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        getSupportActionBar().hide();

        fromTXT = this.findViewById(R.id.from_read_text);
        toTXT = this.findViewById(R.id.to_read_text);
        priceTXT = this.findViewById(R.id.price_read_text);
        acceptbtn = findViewById(R.id.acceptbtn);
        Username = this.findViewById(R.id.UserName);
        BalanceAmount = this.findViewById(R.id.BalanceAmount);


        tripDocId =  getIntent().getStringExtra("code");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            //usernameDisplay.setText(uid);
        }


        if (TextUtils.isEmpty(tripDocId)) {
            //Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(BillPayment.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            getTripDetail();
            getUserDetail();
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
                            String destination = documentSnapshot.getString("stop");
                            toTXT.setText(destination);
                        }
                    }
                });
    }

    private void getUserDetail(){
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
                            String name = documentSnapshot.getString("name");
                            BalanceAmount.setText(String.valueOf(coin));
                            Username.setText(name);
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

                                                    queue(tripDocId, uid);

                                                    /*SharedPreferences ticketPref = getSharedPreferences("ticket",MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = ticketPref.edit();
                                                    editor.putString("ticket",tripDocId);
                                                    editor.apply();*/

                                                    Intent intent = new Intent(getApplicationContext(), SuccessBillPayment.class);
                                                    //intent.putExtra("code", tripDocId);
                                                    startActivity(intent);

                                                    finish();
                                                }
                                                else{
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

    public void queue(final String tripDocId, final String uid) {

        CollectionReference tokenRef = database.collection("user").document(uid).collection("token");
        tokenRef
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            tokenList.add(document.getId());
                            Log.d("PRINT", tokenList.get(0));
                        }

                        DocumentReference docRef = database.collection("trip").document(tripDocId).collection("queue").document(uid);
                        Map<String, Object> userTicket = new HashMap<>();
                        userTicket.put("timestamp", FieldValue.serverTimestamp());
                        userTicket.put("status", "");
                        userTicket.put("status2", "");
                        docRef.set(userTicket);


                        for (int i =0; i < tokenList.size(); i++){
                            DocumentReference queueTokenRef = database.collection("trip").document(tripDocId).collection("queue").document(uid).collection("token").document(tokenList.get(i));
                            Log.d("PRINT", tokenList.get(i));
                            Map<String, Object> tokenMap = new HashMap<>();
                            tokenMap.put("token", tokenList.get(i));
                            queueTokenRef.set(tokenMap);
                        }
                    }
                }
            });

        DocumentReference userRef = database.collection("user").document(uid);
        userRef.update("ticket", tripDocId);
    }
}

