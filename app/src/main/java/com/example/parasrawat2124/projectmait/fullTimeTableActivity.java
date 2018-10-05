package com.example.parasrawat2124.projectmait;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class fullTimeTableActivity extends AppCompatActivity {

    TextView thead;
    Bundle b;
    int l;
    int[][] ids=new int[][]{{R.id.m1,R.id.t1,R.id.w1,R.id.th1,R.id.f1},
            {R.id.m2,R.id.t2,R.id.w2,R.id.th2,R.id.f2},
            {R.id.m3,R.id.t3,R.id.w3,R.id.th3,R.id.f3},
            {R.id.m4,R.id.t4,R.id.w4,R.id.th4,R.id.f4},
            {R.id.m5,R.id.t5,R.id.w5,R.id.th5,R.id.f5},
            {R.id.m6,R.id.t6,R.id.w6,R.id.th6,R.id.f6},
            {R.id.m7,R.id.t7,R.id.w7,R.id.th7,R.id.f7},
            {R.id.m8,R.id.t8,R.id.w8,R.id.th8,R.id.f8}};
    ArrayList<String> days,times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_time_table);

        thead=findViewById(R.id.head);

        b=getIntent().getExtras();
        String head=b.getString("head");
        days=b.getStringArrayList("days");
        times=b.getStringArrayList("times");

        l=days.size();

        if(head.equals("teacher")) {
            ArrayList<String> classes=b.getStringArrayList("classes");
            ArrayList<String> rooms=b.getStringArrayList("rooms");
            String steacher=b.getString("teacher");
            displayTeacherTT(steacher, days, times, classes, rooms);
        }

        if(head.equals("room")) {
            ArrayList<String> classes=b.getStringArrayList("classes");
            ArrayList<String> teachers=b.getStringArrayList("teachers");
            String sroom=b.getString("room");
            String sroomtype=b.getString("roomtype");

            displayRoomTT(sroom+"\n"+sroomtype, days, times, classes, teachers);
        }

        if(head.equals("class")){
            ArrayList<String> rooms=b.getStringArrayList("rooms");
            ArrayList<String> teachers=b.getStringArrayList("teachers");
            ArrayList<String> types=b.getStringArrayList("types");
            ArrayList<String> subjects=b.getStringArrayList("subjects");
            String sclass=b.getString("class");

            displayClassTT(sclass,days,times,subjects,teachers,rooms,types);
        }

    }

    public void displayTeacherTT(String head,ArrayList<String> days,ArrayList<String> times,ArrayList<String> classes,ArrayList<String> rooms){
        thead.setText(head);
        int[] a=checkDay(days),b=checkTime(times);
        for(int i=0;i<l;i++){
            TextView m=findViewById(ids[b[i]][a[i]]);
            m.setText(classes.get(i)+"\n"+rooms.get(i));
            m.setTextSize(10);
        }
    }

    public void displayRoomTT(String head,ArrayList<String> days,ArrayList<String> times,ArrayList<String> teachers,ArrayList<String> classes){
        thead.setText(head);
        int[] a=checkDay(days),b=checkTime(times);
        for(int i=0;i<l;i++){
            TextView m=findViewById(ids[b[i]][a[i]]);
            m.setText(classes.get(i)+"\n"+teachers.get(i));
            m.setTextSize(10);
        }
    }

    public void displayClassTT(String head,ArrayList<String> days,ArrayList<String> times,ArrayList<String> subjects,ArrayList<String> teachers,ArrayList<String> rooms,ArrayList<String> types){
        thead.setText(head);
        int[] a=checkDay(days),b=checkTime(times);
        for(int i=0;i<l;i++){
            TextView m=findViewById(ids[b[i]][a[i]]);
            String sub=subjects.get(i);
            String teacher=teachers.get(i);
            int c=teacher.indexOf(" ");
            String initials=Character.toString(teacher.charAt(0))+Character.toString(teacher.charAt(c+1));
            m.setText(sub+"\n("+types.get(i)+")"+"\n"+initials+"\n"+rooms.get(i));
            m.setTextSize(10);
        }
    }

    public int[] checkDay(ArrayList<String> days){
        int[] a=new int[l];
        for(int i=0;i<l;i++){
            switch (days.get(i)){
                case "Monday" : a[i]=0;break;
                case "Tuesday" : a[i]=1;break;
                case "Wednesday" : a[i]=2;break;
                case "Thursday" : a[i]=3;break;
                case "Friday" : a[i]=4;
            }
        }
        return a;
    }
    public int[] checkTime(ArrayList<String> times){
        int[] b=new int[l];
        for(int i=0;i<l;i++){
            switch (times.get(i)){
                case "9:15-10:05" : b[i]=0;break;
                case "10:05-10:55" : b[i]=1;break;
                case "10:55-11:45" : b[i]=2;break;
                case "11:45-12:35" : b[i]=3;break;
                case "1:15-2:05" : b[i]=4;break;
                case "2:05-2:55" : b[i]=5;break;
                case "2:55-3:45" : b[i]=6;break;
                case "3:45-4:35" : b[i]=7;
            }
        }
        return b;
    }
}
