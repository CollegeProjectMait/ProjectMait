package com.example.parasrawat2124.projectmait;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterUserActivity extends AppCompatActivity {
    TextInputLayout username, password,name,confirmpassword;
    Button button;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        username=findViewById(R.id.Username);
        password=findViewById(R.id.Password);
        confirmpassword=findViewById(R.id.ConfirmPassword);
         name=findViewById(R.id.Name);
         button=findViewById(R.id.register);
         firebaseAuth=FirebaseAuth.getInstance();

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String pass=password.getEditText().getText().toString();
                 String conpass=confirmpassword.getEditText().getText().toString();
                 String user=username.getEditText().getText().toString();
                 if(pass.equals(conpass) && pass.length()>4){
                     Log.d("USERNAME PASSWORD", "onClick: "+pass+conpass);
                     CreateFirebaseUser(user,pass);
                 }

             }
         });

    }

    private void CreateFirebaseUser(String username,String password){
        Log.d("NEW USERNAME", "CreateFirebaseUser: "+username);
        Log.d("NEW PASSWORD", "CreateFirebaseUser: "+password);

        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("RESULT", "onComplete: "+task);
                if(!task.isSuccessful()){
                    Toast.makeText(RegisterUserActivity.this,"BAD FORMAT FOR EMAIL ADDRESS",Toast.LENGTH_LONG).show();


                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Log.d("FIREBASE COLLISON", "onComplete: "+ e);
                        // log error here

                    } catch (FirebaseNetworkException e) {
                        Log.d("NETWROK EXCEPTION", "onComplete: " +e);
                        // log error here
                    } catch (Exception e) {
                        Log.d("OTHER EXCEPTION", "onComplete: "+ e);
                        // log error here
                        Toast.makeText(RegisterUserActivity.this,"NOT SUCCESSFULL TRY AGAIN",Toast.LENGTH_LONG).show();
                    }

               } else {

                    Toast.makeText(RegisterUserActivity.this,"REGISTRATION SUCCESS",Toast.LENGTH_LONG).show();
//
               }
//
//
             }

        });
    }
}
