package com.example.parasrawat2124.projectmait;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class ParasRawatPushData{
   public String classid,day,timeslot,teacherid,room;
    public ParasRawatPushData(){

    }
    public ParasRawatPushData(String classid,String day,String timeslot,String teacherid,String room){
        this.classid=classid;
        this.day=day;
        this.teacherid=teacherid;
        this.timeslot=timeslot;
        this.room=room;

    }

    public String getClassid() {
        return classid;
    }

    public String getDay() {
        return day;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public String getRoom() {
        return room;
    }


}
