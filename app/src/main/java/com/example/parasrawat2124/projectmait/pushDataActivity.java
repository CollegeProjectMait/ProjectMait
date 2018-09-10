package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class pushDataActivity extends AppCompatActivity {

   Spinner classspinner,roomspinner,teacherspinner,daysspinner,timeslotspinner;
   DatabaseReference databaseReference,dbref_teach;
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
       //TODO put a check so that one teacher cnt be assigned(added) to more than one class for same day&time like for wednesday(9:15-10:05).
       databaseReference.child(classid).child(day+"("+time+")").setValue(data);

       //==============================
        dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_teach.child(teacher).child(day+"("+time+")").setValue(data);
    }
}
