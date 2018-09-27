package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class pushDataActivity extends AppCompatActivity {

   Spinner classspinner,roomspinner,teacherspinner,daysspinner,timeslotspinner,typespinner,subspinner;
   DatabaseReference databaseReference,dbref_teach,dbref_room,dbref_rtype;
   Button push;
   List<String> paraslist;
   int count=0;
   TextView result;
    Object object;
    Button fetch;
    ArrayAdapter<String> typeadap;
//    ArrayList<String> labrooms=new ArrayList<String>();


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
        typespinner=findViewById(R.id.roomtypespinner);
        subspinner=findViewById(R.id.subjectspinner);
        push=findViewById(R.id.push);
        fetch=findViewById(R.id.next);
        databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
        paraslist=new ArrayList<>();
        //result=findViewById(R.id.result);

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
                startActivity(new Intent(pushDataActivity.this,FetchData.class));
            }
        });

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("selected item",typespinner.getSelectedItem().toString());
                    switch (i){
                        case 0 : typeadap=new ArrayAdapter<String>(pushDataActivity.this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.theoryrooms));
                            break;
                        case 1 : typeadap=new ArrayAdapter<String>(pushDataActivity.this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.labrooms));
                            break;
                        case 2 : typeadap=new ArrayAdapter<String>(pushDataActivity.this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tutrooms));
                    }
                    typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    roomspinner.setAdapter(typeadap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
       String roomtype=typespinner.getSelectedItem().toString();
       String subject=subspinner.getSelectedItem().toString();
       String key=databaseReference.push().getKey();
       ParasRawatPushData data=new ParasRawatPushData(classid,day,time,teacher,room,roomtype,subject);

       //TODO put a check so that one teacher cnt be assigned(added) to more than one class for same day&time like for wednesday(9:15-10:05).
       databaseReference.child(classid).child(day+"("+time+")").setValue(data);

        dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_teach.child(teacher).child(day+"("+time+")").setValue(data);

        dbref_room=FirebaseDatabase.getInstance().getReference("RoomTimeTable");
        dbref_room.child(room).child(day+"("+time+")").setValue(data);

//        dbref_rtype=FirebaseDatabase.getInstance().getReference("RoomInfo");
//
//        switch (typespinner.getSelectedItemPosition()){
//            case 0 : //dbref_rtype.child("Theory").setValue(rooms);
//                break;
//            case 1 :
//                dbref_rtype.child("Lab").setValue(labrooms);
//                break;
//            case 2 :          labrooms.add(roomspinner.getSelectedItem().toString());
//                dbref_rtype.child("Tut").setValue(labrooms);
//        }
    }
}
