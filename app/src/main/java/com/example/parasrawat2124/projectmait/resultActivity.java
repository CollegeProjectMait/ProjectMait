package com.example.parasrawat2124.projectmait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class resultActivity extends AppCompatActivity {

    LinearLayout llreq,llres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle=getIntent().getExtras();
        String searchvalue=bundle.getString("searchvalue");
        String arr1[]={"getclass","getroom","getteacher"};
        String arr2[]={"class","room","teacher"};
        llreq=(LinearLayout) findViewById(R.id.llreq);
        llres=(LinearLayout) findViewById(R.id.llres);
        TextView title=(TextView) findViewById(R.id.title);
        Button butt=(Button) findViewById(R.id.butt);
        butt.setText(searchvalue);

        title.setText(searchvalue);

        for(int i=0;i<3;i++){
            if(arr1[i].equals(searchvalue)) {
                TextView t=new TextView(this);
                t.setText(arr2[i]);
                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t.setTextSize(25);
                llres.addView(t);
            }
            else {
                TextView t=new TextView(this);
                t.setText(arr2[i]);
                llreq.addView(t);
                Spinner spin=new Spinner(this);
                llreq.addView(spin);
            }
        }
    }
}
