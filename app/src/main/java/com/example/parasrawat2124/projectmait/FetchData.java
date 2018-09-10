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
//    Object object = datasnapshot.getValue(Object.class);
//    String json = new Gson().toJson(object);
    Object object;
    String json;
    ArrayList<String> jsonlist=new ArrayList<>();
    Map<String,String > map=new HashMap<String, String>();
    List<Map<String,String>> compositemap=new ArrayList<Map<String,String>>();
    ArrayList<List<Map<String,String>>> composeofcompose=new ArrayList<>();
    Button button;


    Spinner spinner,classspinner;
    String classvalue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        textView = findViewById(R.id.text);
        spinner=findViewById(R.id.roomspinner);
        classspinner=findViewById(R.id.classsspinner);
        button=findViewById(R.id.submit);
        int i=0;





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classvalue=classspinner.getSelectedItem().toString();
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MasterTable");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "Class Value: "+classvalue);

                        dataSnapshot.child(classvalue).getRef().addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        object=dataSnapshot.getValue(Object.class);
//                        json=new Gson().toJson(object);
//                        jsonlist.add(json);
//                        Log.d(TAG, "THIS TIME JSON OBJECT LETS SEE WHAT: "+jsonlist);
                                int i=0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    { String key = data.getKey();
                                        String value = data.getValue().toString();
                                        map.put(key, value);
                                    }
                                }

                                Log.d(TAG, "Status of Map "+map);
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

}