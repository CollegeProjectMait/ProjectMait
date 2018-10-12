package com.example.parasrawat2124.projectmait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    DatabaseReference dbref_roominfo,dbref_tinfo;
    ArrayList<String> rooms=new ArrayList<String>(),subs=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbref_roominfo=FirebaseDatabase.getInstance().getReference("RoomInfo");
        dbref_tinfo=FirebaseDatabase.getInstance().getReference("TeacherInfo");
    }

    public void addRoomList(View view){
        EditText roomno=findViewById(R.id.roomno);
        rooms.add(roomno.getText().toString());
    }
    public void addRoom(View view){
        Spinner roomtype=findViewById(R.id.roomtype);
        dbref_roominfo.child(roomtype.getSelectedItem().toString()).setValue(rooms);
        rooms=new ArrayList<String>();
    }

    public void addTSubList(View view){
        Spinner tsub=findViewById(R.id.tsubs);
        subs.add(tsub.getSelectedItem().toString());
    }
    public void addTeacher(View view){
        EditText tname=findViewById(R.id.teachername);
        EditText troom=findViewById(R.id.teacherroom);
        dbref_tinfo.child(tname.getText().toString()).setValue(subs);
        dbref_tinfo.child(tname.getText().toString()).child("room").setValue(troom.getText().toString());
        subs=new ArrayList<String>();
//        tname.setText("");
//        troom.setText("");
    }
}
