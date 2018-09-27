package com.example.parasrawat2124.projectmait;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class ParasRawatPushData{
   public String classid,day,timeslot,teacherid,room,roomtype,subject;
    public ParasRawatPushData(){

    }
    public ParasRawatPushData(String classid,String day,String timeslot,String teacherid,String room,String type,String sub){
        this.classid=classid;
        this.day=day;
        this.teacherid=teacherid;
        this.timeslot=timeslot;
        this.room=room;
        this.roomtype=type;
        this.subject=sub;
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

    public String getRoomtype() {
        return roomtype;
    }

    public String getSubject() {
        return subject;
    }


}
