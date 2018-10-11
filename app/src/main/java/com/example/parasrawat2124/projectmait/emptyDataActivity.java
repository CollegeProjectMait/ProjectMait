package com.example.parasrawat2124.projectmait;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.common.util.CrashUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class emptyDataActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Spinner emptydayspinner;
     ArrayList<String> userslots=new ArrayList<>();
     ArrayList<String> busyslots=new ArrayList<>();
    public static final String TAG="EMP========";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_data);
        emptydayspinner=findViewById(R.id.emptydayspinner);


        final String[] slots=getResources().getStringArray(R.array.time_slots);
        databaseReference= FirebaseDatabase.getInstance().getReference("RoomTimeTable");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final DatabaseReference databaseReference1=dataSnapshot.getRef();
                for(int i=0;i<slots.length;i++) {
                    String value = "Monday" + "(" + slots[i] + ")";
                    userslots.add(value);
//
                }
                databaseReference1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for (DataSnapshot data:dataSnapshot.getChildren()
                             ) {
                            if(data.getKey().contains("Monday")){
                                busyslots.add(data.getKey());
                                Log.d(TAG, "onChildAdded: "+data.getKey());
                            }
//
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
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });

    }
    void checkdata(){
        Log.d("==============", "checkdata: "+"I AM CALLLED");
        Log.d(TAG, "USERSLOTS: "+userslots);
        Log.d(TAG, "BUSY SLOTS: "+busyslots);
        ArrayList<String> freeslots;
        for(int i=0;i<userslots.size();i++){
            if(!busyslots.contains(userslots.get(i))){
                Log.d("FREE SLOTS", "checkdata: ==========================="+userslots.get(i));
            }
        }
    }
}
