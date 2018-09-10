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
//    Object object = datasnapshot.getValue(Object.class);
//    String json = new Gson().toJson(object);
    Object object;
    String json;
    ArrayList<String> jsonlist=new ArrayList<>();
    Map<String,String > map=new HashMap<String, String>();
    List<Map<String,String>> compositemap=new ArrayList<Map<String,String>>();
    ArrayList<List<Map<String,String>>> composeofcompose=new ArrayList<>();
    Button button;

    Spinner spinner,classspinner,teacherspinner,dayspinner,timespinner,roomspinner;
    String classvalue;
    String vday,vtime,vroom,vclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        textView = findViewById(R.id.text);
        spinner=findViewById(R.id.roomspinner);
        classspinner=findViewById(R.id.classsspinner);
        teacherspinner=findViewById(R.id.teacherspinner);
        dayspinner=findViewById(R.id.dayspinner);
        timespinner=findViewById(R.id.timespinner);
        roomspinner=findViewById(R.id.roomspinner);
        button=findViewById(R.id.submit);
        int i=0;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RETRIEVE(GIVEN : CLASS)
//                textView.setText("");
//                classvalue = classspinner.getSelectedItem().toString();
//                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTable");
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Log.d(TAG, "Class Value: " + classvalue);
//
//                            dataSnapshot.child(classvalue).getRef().addChildEventListener(new ChildEventListener() {
//                                @Override
//                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
////                        object=dataSnapshot.getValue(Object.class);
////                        json=new Gson().toJson(object);
////                        jsonlist.add(json);
////                        Log.d(TAG, "THIS TIME JSON OBJECT LETS SEE WHAT: "+jsonlist);
//                                    int i = 0;
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                        {
//                                            String key = data.getKey();
//                                            String value = data.getValue().toString();
//                                            map.put(key, value);
//                                        }
//                                    }
//
//                                    Log.d(TAG, "Status of Map " + map);
//                                    textView.append(map.toString() + "\n");
//                                }
//
//
//                                @Override
//                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                }
//
//                                @Override
//                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                }
//
//                                @Override
//                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
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


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int index = 1;
//
//                Log.d(TAG, "BUTTTON HAS BEEN CLICKED");
//
//                    Iterator<Map<String, String>> iterator = compositemap.iterator();
//                    while (iterator.hasNext()) {
//                        Log.d(TAG, "INSIDE THE WHILE LOOP " + iterator);
//                        Map<String, String> value = iterator.next();
//                        Set<Map.Entry<String, String>> entries = value.entrySet();
//
//                        for (Map.Entry<String, String> entry : entries) {
//                            Log.d(TAG, "INSIDE THE COMPLEX CODE " + entry);
//
//
//                        }
//                        index++;
//                    }
//
//            }
//        });

    }

    public void reset(View view){
        dayspinner.setSelection(0);
        timespinner.setSelection(0);
        classspinner.setSelection(0);
        roomspinner.setSelection(0);
        teacherspinner.setSelection(0);
    }

}