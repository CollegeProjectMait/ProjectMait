package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class UserSelection extends AppCompatActivity {
    CardView admin,faculty,student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        admin=findViewById(R.id.admincardview);
        student=findViewById(R.id.studentscardview);
        faculty=findViewById(R.id.facultycardview);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserSelection.this,OtpVerificationActivity.class));
            }
        });
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserSelection.this,LoginActivity.class));
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Coming soon",Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
