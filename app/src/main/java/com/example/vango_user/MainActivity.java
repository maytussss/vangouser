package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.content.Intent;
import android.widget.Button;
=======
import android.widget.TextView;
>>>>>>> dddee1a9144ae106420fa45ce5f60bd6855cde1f

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference tripRef = database.collection("trip");

    private tripAdapter adapter;
    TextView usernameDisplay;
    String uid;
    final String KEY_USERNAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUpRecyclerView();
<<<<<<< HEAD

        // Scan Button
        findViewById(R.id.goscan).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,ScanHome.class);
                        startActivity(intent);
                    }
                }
        );


=======
        usernameDisplay = findViewById(R.id.usernameDisplay);
        getUser();
    }

    private void getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            //usernameDisplay.setText(uid);
        }

        DocumentReference docRef = database.collection("user").document(uid);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString(KEY_USERNAME);
                            usernameDisplay.setText(username);
                        }
                    }
                });
>>>>>>> dddee1a9144ae106420fa45ce5f60bd6855cde1f
    }

    private void setUpRecyclerView(){
        Query query = tripRef.orderBy("start", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<tripList> options = new FirestoreRecyclerOptions.Builder<tripList>()
                .setQuery(query, tripList.class)
                .build();

        adapter = new tripAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.tripRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
