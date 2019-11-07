package com.example.vango_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TicketExist extends AppCompatActivity {

    String uid;
    String tripDocId;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_bill_payment);
        getSupportActionBar().hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(TicketExist.this);
        builder.setCancelable(false); // To protect press back navigation.
        builder.setMessage("Ticket already exist: Do you want to cancel the existing ticket and buy the new one?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancelQueue();
                Intent intent = new Intent(TicketExist.this, ScanHome.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(TicketExist.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(TicketExist.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public String getUID(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        else {
            uid = "aU6PtXs2QURfUOD3rdy3HKb6l7X2";
        }
        return uid;
    }

    public void cancelQueue() {

        SharedPreferences ticketPref = getSharedPreferences("ticket",MODE_PRIVATE);
        tripDocId = ticketPref.getString("ticket","");

        uid = getUID();
        DocumentReference tripRef = database.collection("trip").document(tripDocId).collection("queue").document(uid);
        DocumentReference userRef = database.collection("user").document(uid);
        tripRef.delete();
        userRef.update("ticket", false);

        SharedPreferences.Editor editor = ticketPref.edit();
        editor.putString("ticket",null);
        editor.apply();


    }
}
