package com.example.parasrawat2124.projectmait;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

public class FetchData extends AppCompatActivity {
    List<ParasRawatPushData> paras = new ArrayList<>();
    public static final String TAG="FETCH DATA ACTIVITY";
    TextView textView;
    Object object;
    String json;
    ArrayList<String> jsonlist=new ArrayList<>();
    Map<String,String > map=new HashMap<String, String>();
    List<Map<String,String>> compositemap=new ArrayList<Map<String,String>>();
    ArrayList<List<Map<String,String>>> composeofcompose=new ArrayList<>();
    Button button;
    Spinner roomspinner,classspinner,dayspinner,timespinner,teacherspinner;
    String slotchild;

    String vday,vtime,vroom,vclass,vteacher;

    StringBuffer msg=new StringBuffer();
    String title;
    boolean exist=false;
    String sday,stime,sclass,sroom,steacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        textView = findViewById(R.id.text);
        roomspinner=findViewById(R.id.roomspinner);
        dayspinner=findViewById(R.id.dayspinner);
        timespinner=findViewById(R.id.timespinner);
        classspinner=findViewById(R.id.classsspinner);
        teacherspinner=findViewById(R.id.teacherspinner);
        button=findViewById(R.id.submit);
        int i=0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sday=dayspinner.getSelectedItem().toString();
                stime=timespinner.getSelectedItem().toString();
                sclass=classspinner.getSelectedItem().toString();
                sroom=roomspinner.getSelectedItem().toString();
                steacher=teacherspinner.getSelectedItem().toString();

                if(!classspinner.getSelectedItem().toString().equals("No Idea")) {

                    if (dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0) {
                        //+++++++++++++++++++++++++++++++++++++Query if we know Day Time Class All of the Above++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                        slotchild = sday + "(" + stime + ")";
                        Log.d(TAG, "SLOT OF TIME CHOSEN: " + slotchild);
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTable");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "Class Value: " + sclass);
                                dataSnapshot.child(sclass).getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (dataSnapshot.getKey().equals(slotchild)) {
                                            Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue());
                                            exist=true;
                                            title="Class : "+sclass+
                                                    "\nDay|Time : "+sday+"|"+stime;
                                            for (DataSnapshot data : dataSnapshot.getChildren()
                                                    ) {
                                                if (data.getKey().equals("teacherid")) {
                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
                                                    msg.append("Your Teacher is  " + data.getValue() + "\n");
                                                }
                                                if (data.getKey().equals("room")) {
                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
                                                    msg.append("Your Room is " + data.getValue() + "\n");
                                                }
                                            }
                                        }
                                        if(exist) showmsg(title,msg.toString());
                                        else showmsg("Error","No matching result exist");
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }

                    //+++++++++++++++++++++++++++++++++++++++++++++++++++Query if we know Day,Class+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    if(timespinner.getSelectedItemPosition()==0 && dayspinner.getSelectedItemPosition()!=0){
                        Log.d(TAG, "IN THE QUERY 2: ");


                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.child(sclass).getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        title="Class : "+sclass+
                                                "\nDay : "+sday;
                                        if(dataSnapshot.getKey().contains(sday)){
                                            Log.d(TAG, "onChildAdded: "+dataSnapshot);
                                            exist=true;
                                            for (DataSnapshot data: dataSnapshot.getChildren()
                                                    ) {
                                                String teacher="";
                                                String room="";
                                                String timeslot="";
                                                Log.d(TAG, "Query 2: "+data.getKey()+" "+data.getValue());

                                                if(data.getKey().equals("teacherid")){
                                                    teacher=teacher+"Teacher "+data.getValue().toString();
                                                }
                                                if(data.getKey().equals("room")){
                                                    room=room+" In room "+data.getValue().toString();
                                                }

                                                if(data.getKey().equals("timeslot")){
                                                    timeslot=" For the slot "+timeslot+data.getValue().toString();
                                                }
                                                msg.append(teacher+room+timeslot+"\n");

                                            }
                                            if(exist) showmsg(title,msg.toString());
                                            else showmsg("Error","No matching result exist");
                                        }
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++QUERY If we know timeslot



                }


       // ======================================================================================================================================================Below code NOT TO BE DELETED !!!
                //RETRIEVE(GIVEN : TEACHER)
        DatabaseReference dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable").child(steacher);

        //given : Teacher,Day,Time | result :room,class===========================================================
        if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {

            Log.d(TAG, "MAYURI GUPTA ACTIVITYT ==========");
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(!dataSnapshot.toString().isEmpty()) {
//                        vclass = dataSnapshot.child("classid").getValue().toString();
//                        vroom = dataSnapshot.child("room").getValue().toString();
//                        if (!vclass.isEmpty() && !vroom.isEmpty()) {
//                            textView.append("Teacher : " + vteacher +
//                                    "\nis teaching class : " + vclass +
//                                    "\nin room no : " + vroom);
//                        }
//                    }
                    title="Teacher : "+steacher+
                            "\nDay|Time : "+sday+"|"+stime;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.d(TAG,child.toString());
                            if (child.getKey().equals(sday + "(" + stime + ")")) {
                                Log.d(TAG,"got in");
                                    vroom = child.child("room").getValue().toString();
                                    vclass = child.child("classid").getValue().toString();
                                msg.append("\nis teaching class : " + vclass +
                                    "\nin room no : " + vroom);
                                    Log.d(TAG, "============================ "+vclass);
                                exist=true;
                            }
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        //given : Teacher | result : teacher's timetable===========================================================
        if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        vday = child.child("day").getValue().toString();
                        vtime = child.child("timeslot").getValue().toString();
                        vroom = child.child("room").getValue().toString();
                        vclass = child.child("classid").getValue().toString();
                        msg.append("\nDAY|TIME : " + vday + "|" + vtime
                                + "\nROOM : " + vroom
                                + "\nCLASS : " + vclass + "\n");
                        exist=true;
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        //given : Teacher,Day | result : teacher's timetable of the sday================================================
        if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nDay : "+sday;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (sday.equals(child.child("day").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            Log.e("vtime", vtime);
                            vroom = child.child("room").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            msg.append("\nTIME : " + vtime
                                    + "\nROOM : " + vroom
                                    + "\nCLASS : " + vclass + "\n");
                            exist=true;
                        }
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,class | result : when will the given teacher teach the given class================================
        if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()!=0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nClass : "+sclass;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (sclass.equals(child.child("classid").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            vroom = child.child("room").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            msg.append("\nDAY|TIME : " + vday + "|" + vtime
                                    + "\nROOM : " + vroom + "\n");
                            exist=true;
                        }
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,room | result : given teacher will teach whom&when in the given room================================
        if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()!=0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nRoom : "+sroom;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (sroom.equals(child.child("room").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            msg.append("\nDAY|TIME : " + vday + "|" + vtime
                                    + "\nCLASS : " + vclass + "\n");
                            exist=true;
                        }
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,stime ================================
        if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nTimeSlot : "+stime;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (stime.equals(child.child("timeslot").getValue().toString())) {
                            vroom = child.child("room").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            msg.append("\nDAY : " + vday
                                    + "\nCLASS : " + vclass
                                    + "\nROOM : " + vroom + "\n");
                            exist=true;
                        }
                    }
                    if(exist) showmsg(title,msg.toString());
                    else showmsg("Error","No matching result exist");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//========================================================================================================================================================================================
                DatabaseReference dbref_room=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");

                //given : room,sday,stime | result : which teacher is teaching which class in the given room at given instance
                if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                        && classspinner.getSelectedItemPosition()==0
                        && roomspinner.getSelectedItemPosition()!=0
                        && teacherspinner.getSelectedItemPosition()==0) {

                    Log.d(TAG, "=====================================================  "+"ENTERED MY CASE");
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            title="Room no. : " + sroom
                                    + "\nDay|Time : " + sday + "|" + stime;
                            Log.d(TAG, "================================== "+stime+sday);
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if(child.hasChild(sday+"("+stime+")")){
                                    Log.d(TAG, "PRINT MY CHILD: "+child);
                                    Log.d(TAG, "PRINT MY CHILD 2 "+child.child(sday+"("+stime+")").child("room").toString());//room
                                    if(child.child(sday+"("+stime+")").child("room").getValue().toString().equals(sroom)) {
                                        vteacher = child.child(sday + "(" + stime + ")").child("teacherid").getValue().toString();
                                        vclass = child.child(sday + "(" + stime + ")").child("classid").getValue().toString();
                                        msg.append("\nTeacher : " + vteacher
                                                + "\nClass : " + vclass);
                                        exist=true;
                                        Log.d(TAG, "=========================================================="+vclass+ vteacher);
                                    }
                                }
                            }
                            if(exist) showmsg(title,msg.toString());
                            else showmsg("Error","No matching result exist");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given : room,sday | result : room's schedule for the sday
                if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()==0
                        && roomspinner.getSelectedItemPosition()!=0
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            title="For Room no. : "+sroom
                                    +"\nDay : "+sday+"";
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                for(String time : getResources().getStringArray(R.array.time_slots)) {
                                    if (child.hasChild(sday + "(" + time + ")")) {
                                        if(child.child(sday + "(" + time + ")").child("room").getValue().toString().equals(sroom)) {
                                            vteacher = child.child(sday + "(" + time + ")").child("teacherid").getValue().toString();
                                            vclass = child.child(sday + "(" + time + ")").child("classid").getValue().toString();
                                            msg.append("\nTime : " + time
                                                    + "\nTeacher : " + vteacher
                                                    + "\nClass : " + vclass + "\n");
                                            Log.d(TAG, "============================ "+vclass);
                                            exist=true;
                                        }
                                    }
                                }
                            }
                            if(exist) showmsg(title,msg.toString());
                            else showmsg("Error","No matching result exist");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                exist=false;
                title="";
                msg.setLength(0);
            }
        });
//========================================================================================================================================================================



    }

    public void reset(View view){
        dayspinner.setSelection(0);
        timespinner.setSelection(0);
        classspinner.setSelection(0);
        roomspinner.setSelection(0);
        teacherspinner.setSelection(0);
        exist=false;
        title="";
        msg.setLength(0);
    }

    public void showmsg(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
    }
}