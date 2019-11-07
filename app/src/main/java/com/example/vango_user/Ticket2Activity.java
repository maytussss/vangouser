package com.example.vango_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Ticket2Activity extends AppCompatActivity {
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    ImageButton A1;
    Button A2;
    String uid;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket2);
        getSupportActionBar().hide();

        //countQueue();

        A1 = (ImageButton)findViewById(R.id.backbt2);
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent A1 = new Intent(Ticket2Activity.this,MainActivity.class);
                startActivity(A1);
            }
        });

        A2 = (Button)findViewById(R.id.buybt);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent A2 = new Intent(Ticket2Activity.this,ScanHome.class);
                startActivity(A2);
            }
        });

    }

    /*private void countQueue(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        else {
            uid = "aU6PtXs2QURfUOD3rdy3HKb6l7X2";
        }

        database.collection("trip").document(uid).collection("queue")
                .orderBy("timeStamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                        }
                    }
                });
    }*/
}
