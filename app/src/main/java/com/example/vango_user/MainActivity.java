package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
//<<<<<<< HEAD
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
//=======
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
//>>>>>>> dddee1a9144ae106420fa45ce5f60bd6855cde1f

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
    SharedPreferences sp;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference tripRef = database.collection("trip");

    private tripAdapter adapter;
    TextView usernameDisplay;
    String uid;
    final String KEY_USERNAME = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("login",MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setUpRecyclerView();

        usernameDisplay = findViewById(R.id.usernameDisplay);

        Toolbar x = findViewById(R.id.menubar);
        setSupportActionBar(x);

        getUser();

//<<<<<<< HEAD

        // Scan Button
        findViewById(R.id.goscan).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                                            String tripDocId = documentSnapshot.getString("ticket");
                                            if(!tripDocId.isEmpty()){
                                                Intent intent = new Intent(MainActivity.this,TicketExist.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Intent intent = new Intent(MainActivity.this,ScanHome.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                    }
                }
        );


        findViewById(R.id.goticket).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                                            String tripDocId = documentSnapshot.getString("ticket");
                                            if(!tripDocId.isEmpty()){
                                                Intent intent = new Intent(MainActivity.this,TicketActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Intent intent = new Intent(MainActivity.this,Ticket2Activity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                    }
                }
        );
    }
//<<<<<<<<<<<end logout expand

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

    /***@Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.yourblance_id:
                Intent intent = new Intent(MainActivity.this,YourBalance.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this,"balance click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_id:
                FirebaseAuth.getInstance().signOut();
                sp.edit().putBoolean("logged",false).apply();
                Intent x = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(x);
                finish();
                //Toast.makeText(this,"logout click",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
