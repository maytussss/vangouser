package com.example.vango_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    TextInputLayout usernamehint, passwordhint;
    ImageButton test;
    TextView dots, openingword, leadtoReg, loadPhrase, warning, plsentpassw, plsentusern, plsentsixmin;
    Button loginButton;
    FrameLayout Framelayo;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sp;
    String token;
    String uid;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
            }
        });

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }


        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar11);
        dots = findViewById(R.id.fivedots);
        openingword = findViewById(R.id.opngwrd);
        leadtoReg = findViewById(R.id.btntoreg);
        loadPhrase = findViewById(R.id.welcome);
        usernamehint = findViewById(R.id.userhint);
        passwordhint = findViewById(R.id.passhint);
        warning = findViewById(R.id.plscheck);
        Framelayo = findViewById(R.id.FramdLayout2);
        plsentpassw = findViewById(R.id.plsfillp);
        plsentusern = findViewById(R.id.plsfillu);
        plsentsixmin = findViewById(R.id.plsfillmin);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    plsentusern.setVisibility(View.VISIBLE);
                    plsentsixmin.setVisibility(View.INVISIBLE);
                    plsentpassw.setVisibility(View.INVISIBLE);
                    warning.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    plsentpassw.setVisibility(View.VISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentsixmin.setVisibility(View.INVISIBLE);
                    warning.setVisibility(View.INVISIBLE);


                    //Toast.makeText(LoginActivity.this, "Please enter Email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6){
                    plsentpassw.setVisibility(View.INVISIBLE);
                    plsentsixmin.setVisibility(View.VISIBLE);
                    warning.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    //Toast.makeText(LoginActivity.this, "Password has to be 6 character at minimum", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    Framelayo.setVisibility(View.VISIBLE);
                                    emailText.setVisibility(View.INVISIBLE);
                                    passwordText.setVisibility(View.INVISIBLE);
                                    loginButton.setVisibility(View.INVISIBLE);
                                    dots.setVisibility(View.INVISIBLE);
                                    openingword.setVisibility(View.INVISIBLE);
                                    leadtoReg.setVisibility(View.INVISIBLE);
                                    loadPhrase.setVisibility(View.VISIBLE);
                                    usernamehint.setVisibility(View.INVISIBLE);
                                    passwordhint.setVisibility(View.INVISIBLE);
                                    warning.setVisibility(View.INVISIBLE);
                                    plsentpassw.setVisibility(View.INVISIBLE);
                                    plsentusern.setVisibility(View.INVISIBLE);
                                    plsentsixmin.setVisibility(View.INVISIBLE);

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        uid = user.getUid();
                                    }

                                    final DocumentReference docRef = database.collection("user").document(uid);
                                    docRef.get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if (documentSnapshot.exists()) {
                                                        DocumentReference tokenRef = database.collection("user").document(uid).collection("token").document(token);
                                                        Map<String, Object> tokenMap = new HashMap<>();
                                                        tokenMap.put("token", token);
                                                        tokenRef.set(tokenMap);
                                                    }
                                                }
                                            });

                                    DocumentReference tokenRef = database.collection("user").document(uid);
                                    tokenRef.get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if (documentSnapshot.exists()) {
                                                        String tripDocId = documentSnapshot.getString("ticket");
                                                        if(!tripDocId.isEmpty()){
                                                            DocumentReference tokenRef = database.collection("trip").document(tripDocId).collection("queue").document(uid).collection("token").document(token);
                                                            Map<String, Object> tokenMap = new HashMap<>();
                                                            tokenMap.put("token", token);
                                                            tokenRef.set(tokenMap);
                                                        }
                                                    }
                                                }
                                            });

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    sp.edit().putBoolean("logged",true).apply();
                                    //Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    plsentpassw.setVisibility(View.INVISIBLE);
                                    plsentusern.setVisibility(View.INVISIBLE);
                                    warning.setVisibility(View.VISIBLE);
                                    plsentsixmin.setVisibility(View.INVISIBLE);
                                }

                            }
                        });
            }
        });

    }

    public void btn_gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void goToMainActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }



}
