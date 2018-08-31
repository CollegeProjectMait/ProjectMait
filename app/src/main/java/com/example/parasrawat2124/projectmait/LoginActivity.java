package com.example.parasrawat2124.projectmait;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout username,password;
    TextView registerhere;
    private FirebaseAuth firebaseAuth;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.Username);
        password=findViewById(R.id.Password);
        registerhere=findViewById(R.id.registerhere);
        firebaseAuth=FirebaseAuth.getInstance();
        login=findViewById(R.id.login);


        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterUserActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin();
            }
        });
    }

    private void AttemptLogin(){
        String email=username.getEditText().getText().toString();
        String pass=password.getEditText().getText().toString();

        if(email.equals("") || pass.equals("")){
            Toast.makeText(this,"Blank fields not accepted",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Toast.makeText(this,"Login In Progress",Toast.LENGTH_SHORT).show();
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"INVALID CREDENTIALS"+ task.getException(),Toast.LENGTH_SHORT).show();


                    }
                    else {
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }
                }
            });
        }

    }



}
