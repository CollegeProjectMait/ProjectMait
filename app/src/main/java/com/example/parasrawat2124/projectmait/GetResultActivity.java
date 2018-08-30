package com.example.parasrawat2124.projectmait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class GetResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result);

        Button b1=(Button) findViewById(R.id.getclass);
        Button b2=(Button) findViewById(R.id.getroom);
        Button b3=(Button) findViewById(R.id.getteacher);
        Button b4=(Button) findViewById(R.id.gettimetable);


    }

}
