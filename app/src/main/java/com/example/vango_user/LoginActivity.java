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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    TextInputLayout usernamehint, passwordhint;
    ImageButton test;
    TextView dots, openingword, leadtoReg, loadPhrase;
    Button loginButton;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

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

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Please enter Email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(LoginActivity.this, "Password has to be 6 character at minimum", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    emailText.setVisibility(View.INVISIBLE);
                                    passwordText.setVisibility(View.INVISIBLE);
                                    loginButton.setVisibility(View.INVISIBLE);
                                    dots.setVisibility(View.INVISIBLE);
                                    openingword.setVisibility(View.INVISIBLE);
                                    leadtoReg.setVisibility(View.INVISIBLE);
                                    loadPhrase.setVisibility(View.VISIBLE);
                                    usernamehint.setVisibility(View.INVISIBLE);
                                    passwordhint.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    sp.edit().putBoolean("logged",true).apply();
                                    Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
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
