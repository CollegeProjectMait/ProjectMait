package com.example.parasrawat2124.projectmait;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pushDataActivity extends AppCompatActivity {

   Spinner classspinner,roomspinner,teacherspinner,daysspinner,timeslotspinner,typespinner,subspinner;
   DatabaseReference databaseReference,dbref_teach,dbref_room,dbref_rtype,dbref_stype,dbref_teachinfo;
   Button push;
   List<String> paraslist;
   int count=0;
   TextView result;
    Object object;
    Button fetch;
    ArrayAdapter<String> typeadap;
    ArrayList<String> rooms,subjects,teachers,teachsubjects=new ArrayList<String>();
    String node=null;


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
        dbref_rtype=FirebaseDatabase.getInstance().getReference("RoomInfo");
        dbref_stype=FirebaseDatabase.getInstance().getReference("SubjectInfo");
        dbref_teachinfo=FirebaseDatabase.getInstance().getReference("TeacherInfo");
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

//Populate rooms and subjects according to theory/lab/tut
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0 : node="Theory";
                        break;
                    case 1 : node="Lab";
                        break;
                    case 2 : node="Tut";
                }
                dbref_rtype.child(node).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        rooms=new ArrayList<String>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            rooms.add((String)ds.getValue());
                        }
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, rooms);
                        typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        roomspinner.setAdapter(typeadap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dbref_stype.child(node).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        subjects=new ArrayList<String>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            subjects.add((String)ds.getValue());
                        }
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, subjects);
                        typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subspinner.setAdapter(typeadap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//Populate teachers according to subjects
        subspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String subject=subspinner.getSelectedItem().toString();
                dbref_teachinfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        teachers=new ArrayList<String>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            if(ds.getValue().toString().contains(subject))
                                teachers.add(ds.getKey());
                        }
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, teachers);
                        typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        teacherspinner.setAdapter(typeadap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

       //TODO put a check so that one teacher cnt be assigned(added) to more than one class for same sday&stime like for wednesday(9:15-10:05).
       databaseReference.child(classid).child(day+"("+time+")").setValue(data);

        dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_teach.child(teacher).child(day+"("+time+")").setValue(data);

        dbref_room=FirebaseDatabase.getInstance().getReference("RoomTimeTable");
        dbref_room.child(room).child(day+"("+time+")").setValue(data);

//        switch (typespinner.getSelectedItemPosition()){
//            case 0 : subjects.add(subspinner.getSelectedItem().toString());
//                break;
//            case 1 : subjects.add(subspinner.getSelectedItem().toString());
//                break;
//            case 2 : subjects.add(subspinner.getSelectedItem().toString());
//        }
//        dbref_teachinfo.child(teacherspinner.getSelectedItem().toString()).child(node).setValue(subjects);

        //ADD teacherinfo data
//        teachsubjects.add(subspinner.getSelectedItem().toString());
//        dbref_teachinfo.child(teacherspinner.getSelectedItem().toString()).setValue(teachsubjects);
    }
}
