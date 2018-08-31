package com.example.parasrawat2124.projectmait;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class resultActivity extends AppCompatActivity {

    LinearLayout llreq,llres;
    FirebaseDatabase fbd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        fbd=FirebaseDatabase.getInstance();

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

    public void setDateTimeField(View view){
        final Calendar newCalendar=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                newCalendar.set(i,i1,i2,0,0);
                //dateEditText.setText(dateFormatter.format(dateSelected.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
       // dateEditText.setText(dateFormatter.format(newCalendar.getTime()));
        datePickerDialog.show();
    }
}
