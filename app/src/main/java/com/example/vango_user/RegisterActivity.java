package com.example.vango_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.Token;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameText, emailText, passwordText, confirmPasswordText;
    TextView yourusername, appname, warn, plsentusern, plsentpass, plsentmail, plsconfpass, sixmin;
    TextInputLayout usernhint,passwhint,confpasshint,emhint;
    ProgressBar loading;
    Button registerButton;
    Button backButton;
    private FirebaseAuth firebaseAuth;
    String email, username, password, confirmPassword;
    int coin;
    int pass;
    String token;
    private Button button;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();

        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        firebaseAuth = FirebaseAuth.getInstance();
        yourusername = findViewById(R.id.urusername);
        usernhint = findViewById(R.id.usnhintt);
        passwhint = findViewById(R.id.pwhintt);
        confpasshint = findViewById(R.id.cfpwhintt);
        emhint = findViewById(R.id.emhintt);
        loading = findViewById(R.id.progressBar22);
        appname = findViewById(R.id.appname);
        warn = findViewById(R.id.notmatch);
        plsentusern = findViewById(R.id.entusern);
        plsentpass = findViewById(R.id.entpassw);
        plsentmail = findViewById(R.id.entemailadd);
        plsconfpass = findViewById(R.id.confpassw);
        sixmin = findViewById(R.id.passwmini);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                email = emailText.getText().toString().trim();
                password = passwordText.getText().toString().trim();
                confirmPassword = confirmPasswordText.getText().toString().trim();
                username = usernameText.getText().toString().trim();
                coin = 0;

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                       token = instanceIdResult.getToken();
                    }
                });

                if(TextUtils.isEmpty(email)){
                    plsentmail.setVisibility(View.VISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.INVISIBLE);
                    sixmin.setVisibility(View.INVISIBLE);
                    warn.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    plsentmail.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.VISIBLE);
                    sixmin.setVisibility(View.INVISIBLE);
                    warn.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    plsentmail.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.VISIBLE);
                    sixmin.setVisibility(View.INVISIBLE);
                    warn.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    plsentmail.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.VISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.INVISIBLE);
                    sixmin.setVisibility(View.INVISIBLE);
                    warn.setVisibility(View.INVISIBLE);
                    return;
                }
                if(password.length()<6){
                    plsentmail.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.INVISIBLE);
                    sixmin.setVisibility(View.VISIBLE);
                    warn.setVisibility(View.INVISIBLE);
                    //Toast.makeText(RegisterActivity.this, "Please enter Email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(confirmPassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loading.setVisibility(View.VISIBLE);
                                        emailText.setVisibility(View.INVISIBLE);
                                        usernameText.setVisibility(View.INVISIBLE);
                                        passwordText.setVisibility(View.INVISIBLE);
                                        confirmPasswordText.setVisibility(View.INVISIBLE);
                                        emhint.setVisibility(View.INVISIBLE);
                                        usernhint.setVisibility(View.INVISIBLE);
                                        passwhint.setVisibility(View.INVISIBLE);
                                        confpasshint.setVisibility(View.INVISIBLE);
                                        registerButton.setVisibility(View.INVISIBLE);
                                        backButton.setVisibility(View.INVISIBLE);
                                        appname.setVisibility(View.INVISIBLE);
                                        yourusername.setVisibility(View.INVISIBLE);
                                        warn.setVisibility(View.INVISIBLE);
                                        plsentmail.setVisibility(View.INVISIBLE);
                                        plsentpass.setVisibility(View.INVISIBLE);
                                        plsconfpass.setVisibility(View.INVISIBLE);
                                        plsentusern.setVisibility(View.INVISIBLE);
                                        sixmin.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                                        if (user != null) {
                                            DocumentReference userRef = database.collection("user").document(user.getUid());
                                            Map<String, Object> userMap = new HashMap<>();
                                            userMap.put("name", username);
                                            userMap.put("email", email);
                                            userMap.put("coin", 0);
                                            userMap.put("ticket", "");
                                            userRef.set(userMap);


                                            DocumentReference tokenRef = database.collection("user").document(user.getUid()).collection("token").document(token);
                                            Map<String, Object> tokenMap = new HashMap<>();
                                            tokenMap.put("token", token);
                                            tokenRef.set(tokenMap);
                                        }

                                    }


                                }
                            });

                }
                else{
                    plsentmail.setVisibility(View.INVISIBLE);
                    plsentusern.setVisibility(View.INVISIBLE);
                    plsentpass.setVisibility(View.INVISIBLE);
                    plsconfpass.setVisibility(View.INVISIBLE);
                    sixmin.setVisibility(View.INVISIBLE);
                    warn.setVisibility(View.VISIBLE);
                }

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPg();
            }
        });
    }
    public void openLoginPg() {
        Intent Intent = new Intent(this,LoginActivity.class);
        startActivity(Intent);
    }

}
