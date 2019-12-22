package com.example.foodsharingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText emailId, password;
    Button login;
    TextView textSignup;
    FirebaseAuth mFirebaseauth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseauth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        textSignup = findViewById(R.id.tvSignup);

        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseauth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this,"You are Logged In", Toast.LENGTH_SHORT);
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login!", Toast.LENGTH_SHORT);
                }

            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String passWord = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please Enter Email ID");
                    emailId.requestFocus();
                }
                else if(passWord.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && passWord.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are Empty", Toast.LENGTH_SHORT );
                }
                else if(!(email.isEmpty()&&passWord.isEmpty())){
                    mFirebaseauth.signInWithEmailAndPassword(email,passWord).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!(task.isSuccessful())){
                                Toast.makeText(LoginActivity.this,"Login Failed. Try Again!", Toast.LENGTH_SHORT );
                            }
                            else{
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Ocurred", Toast.LENGTH_SHORT );
                }
            }
        });*/
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseauth.addAuthStateListener(mAuthStateListener);
    }
}
