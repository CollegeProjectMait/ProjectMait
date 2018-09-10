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


    Spinner roomspinner,classspinner,dayspinner,timespinner;

    String classvalue,day,time,slotchild;




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
                classvalue=classspinner.getSelectedItem().toString();
                day=dayspinner.getSelectedItem().toString();
                time=timespinner.getSelectedItem().toString();
                slotchild=day+"("+time+")";
                Log.d(TAG, "SLOT OF TIME CHOSEN: "+slotchild);

                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "Class Value: "+classvalue);
                        dataSnapshot.child(classvalue).getRef().addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                if(dataSnapshot.getKey().equals(slotchild)){
                                    Log.d(TAG, "onChildAdded: "+dataSnapshot.getValue());
                                    for (DataSnapshot data:dataSnapshot.getChildren()
                                         ) {
                                        if(data.getKey().equals("teacherid")){
                                            Log.d(TAG, "onChildAdded: "+data.getValue());
                                            textView.append("Your Teacher is  "+ data.getValue()+"\n");
                                        }
                                        if(data.getKey().equals("room")){
                                            Log.d(TAG, "onChildAdded: "+data.getValue());
                                            textView.append("Your Room is " +data.getValue()+"\n");
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
        });
    }
}