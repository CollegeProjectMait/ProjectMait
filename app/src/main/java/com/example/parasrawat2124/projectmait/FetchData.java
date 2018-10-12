package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchData extends AppCompatActivity {
    List<ParasRawatPushData> paras = new ArrayList<>();
    public static final String TAG="FETCH====";
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

    String vday,vtime,vroom,vclass,vteacher,vtype,vsub;

    StringBuffer msg=new StringBuffer();
    String title;
    boolean exist=false;
    String sday,stime,sclass,sroom,steacher;
    DatabaseReference dbref_master,dbref_teach,dbref_Room,dbref_room;
    ArrayList<String > rooms;
    ArrayAdapter<String > typeadap;
    ArrayList<String > daytime;

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

        dbref_master=FirebaseDatabase.getInstance().getReference("MasterTable");
        dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_Room=FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        dbref_room=FirebaseDatabase.getInstance().getReference("RoomTimeTable");

        //TODO dynamically populate spinners
//        dbref_room.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                rooms=new ArrayList<String>();
//                for(DataSnapshot ds:dataSnapshot.getChildren()){
//                    rooms.add((String)ds.getValue());
//                }
//                typeadap = new ArrayAdapter<String>(FetchData.this, android.R.layout.simple_spinner_item, rooms);
//                typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                roomspinner.setAdapter(typeadap);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sday=dayspinner.getSelectedItem().toString();
                stime=timespinner.getSelectedItem().toString();
                sclass=classspinner.getSelectedItem().toString();
                sroom=roomspinner.getSelectedItem().toString();
                steacher=teacherspinner.getSelectedItem().toString();


                //given : class | result : class's TT==================================================================
                if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()!=0
                        && roomspinner.getSelectedItemPosition()==0
                        && teacherspinner.getSelectedItemPosition()==0) {
                    final String sem=sclass.substring(0,1),g1,g2,g3;
                    int l1,l2;
                    l1=2+(sclass.length()-2)/3;
                    g1=sclass.substring(2,l1);
                    l2=l1+(sclass.length()-2)/3;
                    g2=sclass.substring(l1,l2);
                    g3=sclass.substring(l2);
                    dbref_master.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> vdays=new ArrayList<String>(),vtimes=new ArrayList<String>(),vteachers=new ArrayList<String>(),vrooms=new ArrayList<String>()
                                    ,vtypes=new ArrayList<String>(),vsubs=new ArrayList<String>();
                            Log.e("datasnap",dataSnapshot.toString());
                            if(dataSnapshot.hasChild(sclass)){
                                for(DataSnapshot ds:dataSnapshot.child(sclass).getChildren()){
                                    vday=ds.child("day").getValue().toString();
                                    vtime=ds.child("timeslot").getValue().toString();
                                    vroom=ds.child("room").getValue().toString();
                                    vteacher=ds.child("teacherid").getValue().toString();
                                    vtype=ds.child("roomtype").getValue().toString();
                                    vsub=ds.child("subject").getValue().toString();

                                    vdays.add(vday);
                                    vtimes.add(vtime);
                                    vrooms.add(vroom);
                                    vteachers.add(vteacher);
                                    vtypes.add(vtype);
                                    vsubs.add(vsub);
                                    exist=true;
                                }
                            }
                            if(dataSnapshot.hasChild(sem.concat("C").concat(g1))){
                                for(DataSnapshot ds:dataSnapshot.child(sem.concat("C").concat(g1)).getChildren()){
                                    vday=ds.child("day").getValue().toString();
                                    vtime=ds.child("timeslot").getValue().toString();
                                    vroom=ds.child("room").getValue().toString();
                                    vteacher=ds.child("teacherid").getValue().toString();
                                    vtype=ds.child("roomtype").getValue().toString();
                                    vsub=ds.child("subject").getValue().toString();
                                    vdays.add(vday);
                                    vtimes.add(vtime);
                                    vrooms.add(vroom);
                                    vteachers.add(vteacher);
                                    vtypes.add(vtype);
                                    vsubs.add(vsub);
                                    exist=true;
                                }
                            }
                            if(dataSnapshot.hasChild(sem.concat("C").concat(g2))){
                                for(DataSnapshot ds:dataSnapshot.child(sem.concat("C").concat(g2)).getChildren()){
                                    vday=ds.child("day").getValue().toString();
                                    vtime=ds.child("timeslot").getValue().toString();
                                    vroom=ds.child("room").getValue().toString();
                                    vteacher=ds.child("teacherid").getValue().toString();
                                    vtype=ds.child("roomtype").getValue().toString();
                                    vsub=ds.child("subject").getValue().toString();

                                    vdays.add(vday);
                                    vtimes.add(vtime);
                                    vrooms.add(vroom);
                                    vteachers.add(vteacher);
                                    vtypes.add(vtype);
                                    vsubs.add(vsub);
                                    exist=true;
                                }
                            }
                            if(dataSnapshot.hasChild(sem.concat("C").concat(g3))){
                                for(DataSnapshot ds:dataSnapshot.child(sem.concat("C").concat(g3)).getChildren()){
                                    vday=ds.child("day").getValue().toString();
                                    vtime=ds.child("timeslot").getValue().toString();
                                    vroom=ds.child("room").getValue().toString();
                                    vteacher=ds.child("teacherid").getValue().toString();
                                    vtype=ds.child("roomtype").getValue().toString();
                                    vsub=ds.child("subject").getValue().toString();

                                    vdays.add(vday);
                                    vtimes.add(vtime);
                                    vrooms.add(vroom);
                                    vteachers.add(vteacher);
                                    vtypes.add(vtype);
                                    vsubs.add(vsub);
                                    exist=true;
                                }
                            }

                            if(exist) {
                                Intent i=new Intent(FetchData.this,fullTimeTableActivity.class);
                                i.putExtra("head","class");
                                i.putExtra("days",vdays);
                                i.putExtra("times",vtimes);
                                i.putExtra("rooms",vrooms);
                                i.putExtra("teachers",vteachers);
                                i.putExtra("types",vtypes);
                                i.putExtra("subjects",vsubs);
                                i.putExtra("class",sclass);
                                startActivity(i);
                                //showmsg(title,msg.toString());
                            }
                            else showmsg("Error","No matching result exist");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

       // ======================================================================================================================================================Below code NOT TO BE DELETED !!!
                //RETRIEVE(GIVEN : TEACHER)


        //given : Teacher,Day,Time | result :room,class===========================================================
                else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {

            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nDay|Time : "+sday+"|"+stime;
                    if(dataSnapshot.hasChild(steacher)){
                        for(DataSnapshot ds:dataSnapshot.child(steacher).getChildren()){
                            if(ds.child("day").getValue().equals(sday) && ds.child("timeslot").getValue().toString().equals(stime)){
                                vroom = ds.child("room").getValue().toString();
                                vclass = ds.child("classid").getValue().toString();
                                msg.append("\nis teaching class : " + vclass +
                                        "\nin room no : " + vroom);
                                exist=true;
                            }
                        }
                        if(exist) showmsg(title,msg.toString());
                        else showmsg("Free",steacher+" is free on\n"+sday+" during "+stime+"\nStaffRoom : ");
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        //given : Teacher | result : teacher's TT===========================================================
                else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> vdays=new ArrayList<String>(),vtimes=new ArrayList<String>(),vrooms=new ArrayList<String>(),vclasses=new ArrayList<String>();
                    if(dataSnapshot.hasChild(steacher)) {
                        for (DataSnapshot child : dataSnapshot.child(steacher).getChildren()) {
                            vday = child.child("day").getValue().toString();
                            vtime = child.child("timeslot").getValue().toString();
                            vroom = child.child("room").getValue().toString();
                            vclass = child.child("classid").getValue().toString();

                            vdays.add(vday);
                            vtimes.add(vtime);
                            vrooms.add(vroom);
                            vclasses.add(vclass);
                            exist = true;
                        }
                        if(exist) {
                            Intent i=new Intent(FetchData.this,fullTimeTableActivity.class);
                            i.putExtra("head","teacher");
                            i.putExtra("days",vdays);
                            i.putExtra("times",vtimes);
                            i.putExtra("classes",vclasses);
                            i.putExtra("rooms",vrooms);
                            i.putExtra("teacher",steacher);
                            startActivity(i);
                            //showmsg(title,msg.toString());
                        }
                        else showmsg("Error","No matching record exists");
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        //given : Teacher,Day | result : teacher's timetable of the sday================================================
                else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nDay : "+sday;
                    if(dataSnapshot.hasChild(steacher)){
                        for(DataSnapshot ds:dataSnapshot.child(steacher).getChildren()){
                            if(ds.child("day").getValue().equals(sday)){
                                vtime = ds.child("timeslot").getValue().toString();
                                vroom = ds.child("room").getValue().toString();
                                vclass = ds.child("classid").getValue().toString();
                                msg.append("\nTIME : " + vtime
                                        + "\nROOM : " + vroom
                                        + "\nCLASS : " + vclass + "\n");
                                exist=true;
                            }
                        }
                        if(exist) showmsg(title,msg.toString());
                        else showmsg("Free",steacher+" has no classes on "+sday);
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,class | result : when will the given teacher teach the given class================================
                else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()!=0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nClass : "+sclass;
                    if(dataSnapshot.hasChild(steacher)) {
                        for (DataSnapshot child : dataSnapshot.child(steacher).getChildren()) {
                            if(child.child("classid").getValue().toString().equals(sclass)) {
                                vday = child.child("day").getValue().toString();
                                vtime = child.child("timeslot").getValue().toString();
                                vroom = child.child("room").getValue().toString();
                                msg.append("\nDAY|TIME : " + vday + "|" + vtime
                                        + "\nROOM : " + vroom + "\n");

                                exist = true;
                            }
                        }
                        if(exist) showmsg(title,msg.toString());
                        else showmsg("",steacher+" does not teach "+sclass);
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,room | result : given teacher will teach whom&when in the given room================================
                else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && (roomspinner.getSelectedItemPosition()!=0 || !roomspinner.getSelectedItem().equals("Empty rooms"))
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nRoom : "+sroom;
                    if(dataSnapshot.hasChild(steacher)) {
                        for (DataSnapshot child : dataSnapshot.child(steacher).getChildren()) {
                            if (sroom.equals(child.child("room").getValue().toString())) {
                                vtime = child.child("timeslot").getValue().toString();
                                vclass = child.child("classid").getValue().toString();
                                vday = child.child("day").getValue().toString();
                                msg.append("\nDAY|TIME : " + vday + "|" + vtime
                                        + "\nCLASS : " + vclass + "\n");
                                exist = true;
                            }
                        }
                        if(exist) showmsg(title,msg.toString());
                        else showmsg("",steacher+" does not take classes in Room "+sroom);
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,stime ================================
                else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title="Teacher : "+steacher+
                            "\nTimeSlot : "+stime;
                    if(dataSnapshot.hasChild(steacher)) {
                        for (DataSnapshot child : dataSnapshot.child(steacher).getChildren()) {
                            if (stime.equals(child.child("timeslot").getValue().toString())) {
                                vroom = child.child("room").getValue().toString();
                                vclass = child.child("classid").getValue().toString();
                                vday = child.child("day").getValue().toString();
                                msg.append("\nDAY : " + vday
                                        + "\nCLASS : " + vclass
                                        + "\nROOM : " + vroom + "\n");
                                exist = true;
                            }
                        }
                        if(exist) showmsg(title,msg.toString());
                        else showmsg("Free",steacher+" is free during "+stime+" for all days");
                    }
                    else showmsg("",steacher+" no longer works here");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//========================================================================================================================================================================================

                //given :sday,EMPTY rooms | result : all rooms empty on a day
                else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()==0
                        && teacherspinner.getSelectedItemPosition()==0
                        && roomspinner.getSelectedItem().equals("Empty rooms")) {
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> rooms=new ArrayList<String>(),times=new ArrayList<String>();
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                vroom=ds.getKey();
                                for(String time:getResources().getStringArray(R.array.time_slots)){
                                    if(!ds.hasChild(sday+"("+time+")")){
                                        vtime=time;
                                        rooms.add(vroom);
                                        times.add(vtime);
                                    }
                                }
                            }
                            dayScheduleDialog dialog=new dayScheduleDialog(FetchData.this,"times",rooms,times);
                            dialog.create();
                            dialog.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given :stime,EMPTY rooms | result : all rooms empty on a day
                else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()!=0
                        && classspinner.getSelectedItemPosition()==0
                        && teacherspinner.getSelectedItemPosition()==0
                        && roomspinner.getSelectedItem().equals("Empty rooms")) {
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> rooms=new ArrayList<String>(),days=new ArrayList<String>();
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                vroom=ds.getKey();
                                for(String day:getResources().getStringArray(R.array.days)){
                                    if(!ds.hasChild(day+"("+stime+")")){
                                        vday=day;
                                        rooms.add(vroom);
                                        days.add(vday);
                                    }
                                }
                            }
                            dayScheduleDialog dialog=new dayScheduleDialog(FetchData.this,"days",rooms,days);
                            dialog.create();
                            dialog.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given : room,sday,stime | result : which teacher is teaching which class in the given room at given instance
        else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                        && classspinner.getSelectedItemPosition()==0
                        && (roomspinner.getSelectedItemPosition()!=0 || !roomspinner.getSelectedItem().equals("Empty rooms"))
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_Room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            title="Room no. : " + sroom
                                    + "\nDay|Time : " + sday + "|" + stime;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if(child.hasChild(sday+"("+stime+")")){
                                    if(child.child(sday+"("+stime+")").child("room").getValue().toString().equals(sroom)) {
                                        vteacher = child.child(sday + "(" + stime + ")").child("teacherid").getValue().toString();
                                        vclass = child.child(sday + "(" + stime + ")").child("classid").getValue().toString();
                                        msg.append("\nTeacher : " + vteacher
                                                + "\nClass : " + vclass);
                                        exist=true;
                                    }
                                }
                            }
                            if(exist) showmsg(title,msg.toString());
                            else showmsg("Free","Room "+sroom+" is free on \n"+sday+" during "+stime);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given : room,sday | result : room's schedule for the sday
                //TODO append "FREE" when not assigned anything to that room for the timeslot
        else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()==0
                        && (roomspinner.getSelectedItemPosition()!=0 || !roomspinner.getSelectedItem().equals("Empty rooms"))
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_Room.addValueEventListener(new ValueEventListener() {
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
                                            exist=true;
                                        }
                                    }
                                }
                            }
                            if(exist) showmsg(title,msg.toString());
                            else showmsg("Free",sroom+" is free on "+sday);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given : room | result : rooms's TT
        else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()==0
                        && (roomspinner.getSelectedItemPosition()!=0 || !roomspinner.getSelectedItem().equals("Empty rooms"))
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> vdays=new ArrayList<String>(),vtimes=new ArrayList<String>(),vteachers=new ArrayList<String>(),vclasses=new ArrayList<String>();
                            if(dataSnapshot.hasChild(sroom)){
                                for(DataSnapshot ds:dataSnapshot.child(sroom).getChildren()){
                                    vday=ds.child("day").getValue().toString();
                                    vtime=ds.child("timeslot").getValue().toString();
                                    vclass=ds.child("classid").getValue().toString();
                                    vteacher=ds.child("teacherid").getValue().toString();
                                    vtype=ds.child("roomtype").getValue().toString();

                                    vdays.add(vday);
                                    vtimes.add(vtime);
                                    vclasses.add(vclass);
                                    vteachers.add(vteacher);
                                    exist=true;
                                }
                            }

                            if(exist) {
                                Intent i=new Intent(FetchData.this,fullTimeTableActivity.class);
                                i.putExtra("head","room");
                                i.putExtra("days",vdays);
                                i.putExtra("times",vtimes);
                                i.putExtra("classes",vclasses);
                                i.putExtra("teachers",vteachers);
                                i.putExtra("room",sroom);
                                i.putExtra("roomtype",vtype);
                                startActivity(i);
                                //showmsg(title,msg.toString());
                            }
                            else showmsg("Error","No matching record exists");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
//========================================================================================================================================================================

                //given : sday,stime,sclass,sroom,steacher
        else if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()!=0
                        && classspinner.getSelectedItemPosition()==0
                        && (roomspinner.getSelectedItemPosition()!=0 || !roomspinner.getSelectedItem().equals("Empty rooms"))
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(sclass)){
                                for(DataSnapshot ds:dataSnapshot.child(sclass).getChildren()){
                                    if(ds.child("day").getValue().toString().equals(sday)
                                            && ds.child("timeslot").getValue().toString().equals(stime)
                                            && ds.child("room").getValue().toString().equals(sroom)
                                            && ds.child("teacherid").getValue().toString().equals(steacher)){
                                        msg.append("Correct information !");
                                        exist=true;
                                    }
                                }
                                if (exist) showmsg(title,msg.toString());
                                else showmsg("","Incorrect information !");
                            }
                            else showmsg("",sclass+" no longer exists!");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
//============================================================================================================================================================================

                //given : sclass,sday,stime
                //TODO only theory lectures r cming till now
                else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                        && classspinner.getSelectedItemPosition()!=0
                        && roomspinner.getSelectedItemPosition()==0
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_master.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            title="Class : "+sclass
                                    +"\nDay|Time : "+sday+"|"+stime;
                            if(dataSnapshot.hasChild(sclass)){
                                for(DataSnapshot ds:dataSnapshot.child(sclass).getChildren()){
                                    if(ds.child("day").getValue().toString().equals(sday) && ds.child("timeslot").getValue().toString().equals(stime)){
                                        vroom=ds.child("room").getValue().toString();
                                        vteacher=ds.child("teacherid").getValue().toString();
                                        msg.append("Room : "+vroom
                                        +"\nTeacher : "+vteacher);
                                        exist=true;
                                    }
                                }
                                if (exist) showmsg(title,msg.toString());
                                else showmsg("FREE",sclass+" is free on "+sday+" in "+stime);
                            }
                            else showmsg("",sclass+" no longer exists!");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                //given : sclass,sday
                //TODO only theory lectures r cming till now
                else if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                        && classspinner.getSelectedItemPosition()!=0
                        && roomspinner.getSelectedItemPosition()==0
                        && teacherspinner.getSelectedItemPosition()==0) {
                    dbref_master.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            title="Class : "+sclass
                                    +"\nDay : "+sday;
                            if(dataSnapshot.hasChild(sclass)){
                                for(DataSnapshot ds:dataSnapshot.child(sclass).getChildren()){
                                    if(ds.child("day").getValue().toString().equals(sday)){
                                        vtime=ds.child("timeslot").getValue().toString();
                                        vroom=ds.child("room").getValue().toString();
                                        vteacher=ds.child("teacherid").getValue().toString();
                                        msg.append("Time : "+vtime
                                                +"\nRoom : "+vroom
                                                +"\nTeacher : "+vteacher+"\n");
                                        exist=true;
                                    }
                                }
                                if (exist) showmsg(title,msg.toString());
                                else showmsg("FREE",sclass+" does not have classes on "+sday);
                            }
                            else showmsg("",sclass+" no longer exists!");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
//PARAS WORK==================================================================================================================================================================
//                else if(!classspinner.getSelectedItem().toString().equals("No Idea")) {
//
//                    if (dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0) {
//                        //+++++++++++++++++++++++++++++++++++++Query if we know Day Time Class All of the Above++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//                        slotchild = sday + "(" + stime + ")";
//                        Log.d(TAG, "SLOT OF TIME CHOSEN: " + slotchild);
//                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTable");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Log.d(TAG, "Class Value: " + sclass);
//                                dataSnapshot.child(sclass).getRef().addChildEventListener(new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                                        if (dataSnapshot.getKey().equals(slotchild)) {
//                                            Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue());
//                                            exist=true;
//                                            title="Class : "+sclass+
//                                                    "\nDay|Time : "+sday+"|"+stime;
//                                            for (DataSnapshot data : dataSnapshot.getChildren()
//                                                    ) {
//                                                if (data.getKey().equals("teacherid")) {
//                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
//                                                    msg.append("Your Teacher is  " + data.getValue() + "\n");
//                                                }
//                                                if (data.getKey().equals("room")) {
//                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
//                                                    msg.append("Your Room is " + data.getValue() + "\n");
//                                                }
//                                            }
//                                        }
//                                        if(exist) showmsg(title,msg.toString());
//                                        else showmsg("Error","No matching result exist");
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                            }
//                        });
//                    }
//
//                    //+++++++++++++++++++++++++++++++++++++++++++++++++++Query if we know Day,Class+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//                    else if(timespinner.getSelectedItemPosition()==0 && dayspinner.getSelectedItemPosition()!=0){
//                        Log.d(TAG, "IN THE QUERY 2: ");
//
//
//                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                dataSnapshot.child(sclass).getRef().addChildEventListener(new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                        title="Class : "+sclass+
//                                                "\nDay : "+sday;
//                                        if(dataSnapshot.getKey().contains(sday)){
//                                            Log.d(TAG, "onChildAdded: "+dataSnapshot);
//                                            exist=true;
//                                            for (DataSnapshot data: dataSnapshot.getChildren()
//                                                    ) {
//                                                String teacher="";
//                                                String room="";
//                                                String timeslot="";
//                                                Log.d(TAG, "Query 2: "+data.getKey()+" "+data.getValue());
//
//                                                if(data.getKey().equals("teacherid")){
//                                                    teacher=teacher+"Teacher "+data.getValue().toString();
//                                                }
//                                                if(data.getKey().equals("room")){
//                                                    room=room+" In room "+data.getValue().toString();
//                                                }
//
//                                                if(data.getKey().equals("timeslot")){
//                                                    timeslot=" For the slot "+timeslot+data.getValue().toString();
//                                                }
//                                                msg.append(teacher+room+timeslot+"\n");
//
//                                            }
//                                            if(exist) showmsg(title,msg.toString());
//                                            else showmsg("Error","No matching result exist");
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//                }

                else showmsg("Query not considered yet","Contact the TiTa admin in order to get this query solved !");

                exist=false;
                title="";
                msg.setLength(0);
            }
        });
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