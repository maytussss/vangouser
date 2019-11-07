package com.example.vango_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//<<<<<<< HEAD
import android.view.View;
import android.content.Intent;
import android.widget.Button;
//=======
import android.widget.ExpandableListView;
import android.widget.ExpandableListAdapter;
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

import java.util.ArrayList;

//logout expand
import com.journaldev.expandablelistview.CustomExpandableListAdapter;
import com.journaldev.expandablelistview.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference tripRef = database.collection("trip");

    private tripAdapter adapter;
    TextView usernameDisplay;
    String uid;
    final String KEY_USERNAME = "name";


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUpRecyclerView();
//<<<<<<< HEAD

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


        usernameDisplay = findViewById(R.id.usernameDisplay);
        getUser();
//<<<<<<<<<<<<<logout expand
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();

                if((expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition)) == "your balance")
                {
                    Intent intent = new Intent(MainActivity.this,YourBalance.class);
                    startActivity(intent);
                }
                else if((expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition)) == "logout")
                {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(
                            getApplicationContext(),"Error.", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
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
