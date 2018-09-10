package com.example.parasrawat2124.projectmait;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

public class AddTTActivity extends AppCompatActivity {

    Button b;
    Spinner day,slot;
//    RadioGroup rg;
    RadioButton rb;
    boolean added=false;
    Button query;

    ViewPager viewPager;
    PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtt);

        //I was wondering,, if u can see this line ,  then its for ur award that you are getting slim


        b=(Button) findViewById(R.id.button);
        day =(Spinner) findViewById(R.id.day);
//        slot =(Spinner) findViewById(R.id.slot);
//        rg=(RadioGroup) findViewById(R.id.radios);



        //SPINNER
        ArrayAdapter<CharSequence> day_adapter= ArrayAdapter.createFromResource(this,R.array.days,android.R.layout.simple_spinner_item);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(day_adapter);

//        ArrayAdapter<CharSequence> slot_adapter=ArrayAdapter.createFromResource(this,R.array.slots,android.R.layout.simple_spinner_item);
//        slot_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        slot.setAdapter(slot_adapter);

        //BUTTON
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rb=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
//                AlertDialog.Builder builder=new AlertDialog.Builder(AddTTActivity.this);
//                LayoutInflater inflater=AddTTActivity.this.getLayoutInflater();
//                builder.setView(inflater.inflate(R.layout.activity_main2,null))
//                        .setTitle("| "+(String) day.getSelectedItem()+"\t"+ (String) slot.getSelectedItem()+" | "+rb.getText().toString())
//                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                addData();
//                                Toast.makeText(AddTTActivity.this,"ADDED",Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                .create()
//                .show();
//            }
//        });

        //VIEWPAGER
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(AddTTActivity.this,day);
        viewPager.setAdapter(adapter);

        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                adapter.notifyDataSetChanged();
            }
        });

    }

     public void addData(){
        added=true;
     }
}
