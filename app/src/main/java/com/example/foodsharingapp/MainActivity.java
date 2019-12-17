package com.example.foodsharingapp;

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

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button signUp;
    TextView textSignin;
    FirebaseAuth mFirebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseauth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email_text);
        password = findViewById(R.id.password_text);
        signUp = findViewById(R.id.signup_button);
        textSignin = findViewById(R.id.text_signin);
        signUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this,"Fields are Empty", Toast.LENGTH_SHORT );
                }
                else if(!(email.isEmpty()&& passWord.isEmpty())){
                    mFirebaseauth.createUserWithEmailAndPassword(email,passWord).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!(task.isSuccessful())){
                                Toast.makeText(MainActivity.this,"Sign up Failed. Try Again!", Toast.LENGTH_SHORT );
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Error Ocurred", Toast.LENGTH_SHORT );
                }

            }
        });
        textSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
