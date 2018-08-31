package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result);

    }
    public void next(View view){
        Intent i=new Intent(GetResultActivity.this,resultActivity.class);
        int id=view.getId();
        Button b=(Button) findViewById(id);
        i.putExtra("searchvalue",b.getText());
        startActivity(i);
    }
}
