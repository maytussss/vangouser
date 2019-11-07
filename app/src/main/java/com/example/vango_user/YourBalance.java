package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    TextView balance_amount;
    TextView UserName;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_balance);
        getSupportActionBar().hide();

        Button uni25btn = findViewById(R.id.uni25btn);
        Button uni50btn = findViewById(R.id.uni50btn);
        Button uni75btn = findViewById(R.id.uni75btn);
        Button uni100btn = findViewById(R.id.uni100btn);
        Button uni200btn = findViewById(R.id.uni200btn);
        Button uni500btn = findViewById(R.id.uni500btn);
        Button uni1000btn = findViewById(R.id.uni1000btn);


        balance_amount = findViewById(R.id.balance_amount);
        UserName = findViewById(R.id.UserName);
        getUser();

        uni25btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(25);
            }
        });
        uni50btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(50);
            }
        });
        uni75btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(75);
            }
        });
        uni100btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(100);
            }
        });
        uni200btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(200);
            }
        });
        uni500btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCoin(500);
            }
        });
        uni1000btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ updateCoin(1000);
            }
        });


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
                            String name = documentSnapshot.getString("name");
                            UserName.setText(name);
                        }
                    }
                });
    }

    public void updateCoin(final int price){
        Intent intent = new Intent(YourBalance.this, AddMoneySuccess.class);
        intent.putExtra("price", price);
        startActivity(intent);
        finish();
    }
}
