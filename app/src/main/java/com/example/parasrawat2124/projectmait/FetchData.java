package com.example.parasrawat2124.projectmait;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    String classvalue,day,time,slotchild;

    String vday,vtime,vroom,vclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        textView = findViewById(R.id.text);
        roomspinner=findViewById(R.id.roomspinner);
        dayspinner=findViewById(R.id.dayspinner);
        timespinner=findViewById(R.id.timespinner);
        classspinner=findViewById(R.id.classsspinner);
        button=findViewById(R.id.submit);
        int i=0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");

                if(!classspinner.getSelectedItem().toString().equals("No Idea")) {

                    classvalue = classspinner.getSelectedItem().toString();
                    day = dayspinner.getSelectedItem().toString();
                    time = timespinner.getSelectedItem().toString();

                    if (!day.equals("No Idea") && !time.equals("No Idea")) {
                        //+++++++++++++++++++++++++++++++++++++Query if we know Day Time Class All of the Above++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                        slotchild = day + "(" + time + ")";
                        Log.d(TAG, "SLOT OF TIME CHOSEN: " + slotchild);
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTable");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "Class Value: " + classvalue);
                                dataSnapshot.child(classvalue).getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (dataSnapshot.getKey().equals(slotchild)) {
                                            Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue());
                                            for (DataSnapshot data : dataSnapshot.getChildren()
                                                    ) {
                                                if (data.getKey().equals("teacherid")) {
                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
                                                    textView.append("Your Teacher is  " + data.getValue() + "\n");
                                                }
                                                if (data.getKey().equals("room")) {
                                                    Log.d(TAG, "onChildAdded: " + data.getValue());
                                                    textView.append("Your Room is " + data.getValue() + "\n");
                                                }
                                            }
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

                    //+++++++++++++++++++++++++++++++++++++++++++++++++++Query if we know Day only+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    if(time.equals("No Idea") && !day.equals("No Idea")){
                        Log.d(TAG, "IN THE QUERY 2: ");


                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.child(classvalue).getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        if(dataSnapshot.getKey().contains(day)){
                                            Log.d(TAG, "onChildAdded: "+dataSnapshot);

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
                                                textView.append(teacher+room+timeslot+"\n");

                                            }


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
                teacherspinner=findViewById(R.id.teacherspinner);
        final String vteacher=teacherspinner.getSelectedItem().toString();
        DatabaseReference dbref_teach=FirebaseDatabase.getInstance().getReference("TeacherTimeTable").child(vteacher);

        //given : Teacher,Day,Time | result :room,class===========================================================
        if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {

            Log.d(TAG, "MAYURI GUPTA ACTIVITYT ==========");
            dayspinner = findViewById(R.id.dayspinner);
            timespinner = findViewById(R.id.timespinner);
            vday = dayspinner.getSelectedItem().toString();
            vtime = timespinner.getSelectedItem().toString();
            dbref_teach.child(vday + "(" + vtime + ")").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    textView.setText("Teacher : " + vteacher +
                            "\nis teaching class : " + dataSnapshot.child("classid").getValue().toString() +
                            "\nin room no : " + dataSnapshot.child("room").getValue().toString());
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
                    textView.setText("");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        vday = child.child("day").getValue().toString();
                        vtime = child.child("timeslot").getValue().toString();
                        vroom = child.child("room").getValue().toString();
                        vclass = child.child("classid").getValue().toString();
                        textView.append("\nDAY|TIME : " + vday + "|" + vtime
                                + "\nROOM : " + vroom
                                + "\nCLASS : " + vclass + "\n");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,Day | result : teacher's timetable of the day================================================
        if(dayspinner.getSelectedItemPosition()!=0 && timespinner.getSelectedItemPosition()==0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    textView.setText("");
                    dayspinner = findViewById(R.id.dayspinner);
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        vday = dayspinner.getSelectedItem().toString();
                        if (vday.equals(child.child("day").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            Log.e("vtime", vtime);
                            vroom = child.child("room").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            textView.append("\nTIME : " + vtime
                                    + "\nROOM : " + vroom
                                    + "\nCLASS : " + vclass + "\n");
                        }
                    }
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
                    textView.setText("");
                    classspinner = findViewById(R.id.classsspinner);
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        vclass = classspinner.getSelectedItem().toString();
                        if (vclass.equals(child.child("classid").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            vroom = child.child("room").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            textView.append("\nDAY|TIME : " + vday + "|" + vtime
                                    + "\nROOM : " + vroom + "\n");
                        }
                    }
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
                    textView.setText("");
                    roomspinner = findViewById(R.id.roomspinner);
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        vroom = roomspinner.getSelectedItem().toString();
                        if (vroom.equals(child.child("room").getValue().toString())) {
                            vtime = child.child("timeslot").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            textView.append("\nDAY|TIME : " + vday + "|" + vtime
                                    + "\nCLASS : " + vclass + "\n");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //given : Teacher,time ================================
        if(dayspinner.getSelectedItemPosition()==0 && timespinner.getSelectedItemPosition()!=0
                && classspinner.getSelectedItemPosition()==0
                && roomspinner.getSelectedItemPosition()==0
                && teacherspinner.getSelectedItemPosition()!=0) {
            dbref_teach.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    textView.setText("");
                    timespinner = findViewById(R.id.timespinner);
                    vtime = timespinner.getSelectedItem().toString();
                    textView.append("\n" + "For TimeSlot : " + vtime + "\n");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (vtime.equals(child.child("timeslot").getValue().toString())) {
                            vroom = child.child("room").getValue().toString();
                            vclass = child.child("classid").getValue().toString();
                            vday = child.child("day").getValue().toString();
                            textView.append("\nDAY : " + vday
                                    + "\nCLASS : " + vclass
                                    + "\nROOM : " + vroom + "\n");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//========================================================================================================================================================================================
            }
        });




    }

    public void reset(View view){
        dayspinner.setSelection(0);
        timespinner.setSelection(0);
        classspinner.setSelection(0);
        roomspinner.setSelection(0);
        teacherspinner.setSelection(0);
    }
}