package com.example.parasrawat2124.projectmait;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class resultActivity extends AppCompatActivity {

   Spinner classspinner,roomspinner,teacherspinner,daysspinner,timeslotspinner;
   DatabaseReference databaseReference;
   Button push;
   List<String> paraslist;
   int count=0;
   TextView result;
    Object object;
    Button fetch;



   public static final String TAG="RESULT ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        classspinner=findViewById(R.id.classsspinner);
        roomspinner=findViewById(R.id.roomspinner);
        teacherspinner=findViewById(R.id.teacherspinner);
        daysspinner=findViewById(R.id.daysspinner);
        timeslotspinner=findViewById(R.id.timeslotsspinner);
        push=findViewById(R.id.push);
        fetch=findViewById(R.id.next);
        databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
        paraslist=new ArrayList<>();
        result=findViewById(R.id.result);



//        dbref=FirebaseDatabase.getInstance().getReference();
//
////        Bundle bundle=getIntent().getExtras();
////        String searchvalue=bundle.getString("searchvalue");
//
//        String arr1[]={"getclass","getroom","getteacher"};
//        final String arr2[]={"clas","rooms","teachers"};
//
//        llreq=(LinearLayout) findViewById(R.id.llreq);
//        TextView title=(TextView) findViewById(R.id.title);
//        Button butt=(Button) findViewById(R.id.butt);
//
////        butt.setText(searchvalue);
////        title.setText(searchvalue);
//
////        for(int i=0;i<3;i++){
////            if(arr1[i].equals(searchvalue)) {
////                TextView t=new TextView(this);
////                t.setText(arr2[i]);
////                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
////                t.setTextSize(25);
////                llres.addView(t);
////            }
////            else {
////                TextView t=new TextView(this);
////                t.setText(arr2[i]);
////                llreq.addView(t);
////                arr2[i]=null;
////                final Spinner spin=new Spinner(this);
////                final ArrayAdapter<String> spin_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
////                spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spin.setAdapter(spin_adapter);
////                spin_adapter.add("");
////                spin_adapter.notifyDataSetChanged();
////                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                    @Override
////                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                        arr2[i]=spin.getSelectedItem().toString();
////                    }
////
////                    @Override
////                    public void onNothingSelected(AdapterView<?> adapterView) {
////                    }
////                });
////                llreq.addView(spin);
////            }
////        }
//
//        days=(Spinner) findViewById(R.id.days);
//        timeSlots=(Spinner) findViewById(R.id.timeslots);
//        tclass=(EditText) findViewById(R.id.tclass);
//        troom=(EditText) findViewById(R.id.troom);
//        tteacher=(EditText) findViewById(R.id.tteacher);
//        result=(TextView) findViewById(R.id.result);
//
//        butt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vday=days.getSelectedItem().toString();
//                vtime=timeSlots.getSelectedItem().toString();
//                vclass=tclass.getText().toString();
//                vroom=troom.getText().toString();
//                vteacher=tteacher.getText().toString();
//
//                if(!vclass.isEmpty()) {
//                    dbref.child(vclass).child(vday).child(vtime).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
//                            result.setText("Room     : "+map.get("roomNo")+"\n"+"Teacher : "+map.get("teacher"));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
////                else if(!vroom.isEmpty()){
////                    dbref.child(vclass).child(vday).child(vtime).addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
////                            result.setText("Room     : "+map.get("roomNo")+"\n"+"Teacher : "+map.get("teacher"));
////                            Log.e("vroom", map.get("roomNo"));
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
////                }
////                else if(!vteacher.isEmpty()){
////                    dbref.child("C11").child(vday).child(vtime).child("teacher").addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            //String key=dataSnapshot.getKey();
////                            HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
//////                            result.setText("Room     : "+map.get("roomNo")+"\n"+"Teacher : "+map.get("teacher"));
//////                            Log.e("vroom", map.get("roomNo"));
////                           // Log.e("time then",dataSnapshot.getKey());
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
////                }
//            }
//        });
//
//
//    }
//
//    public void setDateTimeField(View view){
//        final Calendar newCalendar=Calendar.getInstance();
//        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                newCalendar.set(i,i1,i2,0,0);
//                //dateEditText.setText(dateFormatter.format(dateSelected.getTime()));
//            }
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//       // dateEditText.setText(dateFormatter.format(newCalendar.getTime()));
//        datePickerDialog.show();
//    }
//
//    public void reset(View view){
//        tclass.setText("");
//        troom.setText("");
//        tteacher.setText("");

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PushData();
                count++;

            }
        });


//    @Override
//    protected void onStart() {
//        super.onStart();
//        databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Object object=dataSnapshot.getValue(Object.class);
//                result.setText(object.toString());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(resultActivity.this,FetchData.class));
            }
        });

    }

    public void PushData(){
        String id="0";
        String classid=classspinner.getSelectedItem().toString();
       String day=daysspinner.getSelectedItem().toString();
       String time=timeslotspinner.getSelectedItem().toString();
       String teacher=teacherspinner.getSelectedItem().toString();
       String room=roomspinner.getSelectedItem().toString();
       String key=databaseReference.push().getKey();
       ParasRawatPushData data=new ParasRawatPushData(classid,day,time,teacher,room);
       databaseReference.child(classid).child(day+"("+time+")").setValue(data);
    }
}
