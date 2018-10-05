package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class pushDataActivity extends AppCompatActivity {

   Spinner classspinner,roomspinner,teacherspinner,daysspinner,timeslotspinner,typespinner,subspinner;
   DatabaseReference databaseReference,dbref_teach,dbref_room, dbref_rinfo, dbref_sinfo,dbref_teachinfo;
   int count=0;
    Button fetch;
    ArrayAdapter<String> typeadap;
    ArrayList<String> rooms,subjects,teachers,teachsubjects=new ArrayList<String>();
    String node=null;
    String sday,stime,sclass,sroom,steacher,sroomtype,ssubject;
    ArrayList<String> addsubjects=new ArrayList<String>();


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

        fetch=findViewById(R.id.next);

        databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
        dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_room=FirebaseDatabase.getInstance().getReference("RoomTimeTable");
        dbref_rinfo =FirebaseDatabase.getInstance().getReference("RoomInfo");
        dbref_sinfo =FirebaseDatabase.getInstance().getReference("SubjectInfo");
        dbref_teachinfo=FirebaseDatabase.getInstance().getReference("TeacherInfo");

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
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.thclasses));
                        break;
                    case 1 : node="Lab";
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ltclasses));
                        break;
                    case 2 : node="Tut";
                        typeadap = new ArrayAdapter<String>(pushDataActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ltclasses));
                }
                typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classspinner.setAdapter(typeadap);
                dbref_rinfo.child(node).addValueEventListener(new ValueEventListener() {
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
                dbref_sinfo.child(node).addValueEventListener(new ValueEventListener() {
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

    public void pushData(View view){

        sclass=classspinner.getSelectedItem().toString();
        sday=daysspinner.getSelectedItem().toString();
        stime=timeslotspinner.getSelectedItem().toString();
        steacher=teacherspinner.getSelectedItem().toString();
        sroom=roomspinner.getSelectedItem().toString();
        sroomtype=typespinner.getSelectedItem().toString();
        ssubject=subspinner.getSelectedItem().toString();

       ParasRawatPushData data=new ParasRawatPushData(sclass,sday,stime,steacher,sroom,sroomtype,ssubject);

       //TODO put a check so that one teacher cnt be assigned(added) to more than one class for same sday&stime like for wednesday(9:15-10:05).
        databaseReference.child(sclass).child(sday+"("+stime+")").setValue(data);

        dbref_teach.child(steacher).child(sday+"("+stime+")").setValue(data);

        dbref_room.child(sroom).child(sday+"("+stime+")").setValue(data);

        //add teacherinfo
//        addsubjects.add(ssubject);
//        dbref_teachinfo.child(steacher).setValue(addsubjects);

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

    public void addAnother(View view){
        addsubjects=new ArrayList<String>();
    }
}
