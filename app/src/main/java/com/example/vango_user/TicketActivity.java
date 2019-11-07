package com.example.vango_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TicketActivity extends AppCompatActivity {
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    ImageButton b1;
    Button b2;
    String uid;
    String tripDocId;
    TextView textView3;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().hide();

        textView3 = this.findViewById(R.id.textView3);
        countQueue();



        b1 = findViewById(R.id.backbt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b1 = new Intent(TicketActivity.this,MainActivity.class);
                startActivity(b1);
            }
        });

        b2 = findViewById(R.id.donebt);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b2 = new Intent(TicketActivity.this,MainActivity.class);
                startActivity(b2);
            }
        });
    }

    public void countQueue(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        }
        else {
            uid = "aU6PtXs2QURfUOD3rdy3HKb6l7X2";
        }

        SharedPreferences ticketPref = getSharedPreferences("ticket",MODE_PRIVATE);
        tripDocId = ticketPref.getString("ticket","");

        /*DocumentReference URef = database.collection("trip").document(tripDocId).collection("queue").document(uid);
        URef.get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Timestamp time = documentSnapshot.getTimestamp("time");
                    }
                }
            });*/


        CollectionReference Ref = database.collection("trip").document(tripDocId).collection("queue");
        Ref
            .orderBy("timestamp",Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(uid.equals(document.getId())){
                                break;
                            } else{
                                count++;
                            }
                        }
                        textView3.setText(String.valueOf(count));
                    }
                }
            });
    }
}
