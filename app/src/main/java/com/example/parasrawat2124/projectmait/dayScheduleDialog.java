package com.example.parasrawat2124.projectmait;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class dayScheduleDialog extends Dialog {

    int[] tcards=new int[]{R.id.tslot1,R.id.tslot2,R.id.tslot3,R.id.tslot4,R.id.tslot5,R.id.tslot6,R.id.tslot7,R.id.tslot8};
    int l;
    ArrayList<String> rooms,timeslots;

    public dayScheduleDialog(@NonNull Context context, ArrayList<String> rooms,ArrayList<String> timeslots) {
        super(context);
        setContentView(R.layout.day_schedule);
        l=timeslots.size();
        this.rooms=rooms;
        this.timeslots=timeslots;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] time=checkTime();
        for(int i=0;i<l;i++){
            Log.d("time[i]",Integer.toString(time[i]));
            if(time[i]!=-1) {
                Log.d("time[i]!=-1",Integer.toString(time[i]));
                TextView t = findViewById(tcards[time[i]]);
                t.append(rooms.get(i)+"\n");
            }
        }
    }

    public int[] checkTime() {
        int[] b = new int[l];
        Log.d("l", Integer.toString(l));
        for (int i = 0; i < l; i++) {
            int dash = timeslots.get(i).indexOf("-");
            Log.d("dash", Integer.toString(dash));
            if (dash != 0 && dash != -1) {
                switch (timeslots.get(i).substring(0, dash)) {
                    case "9:15":
                        b[i] = 0;
                        break;
                    case "10:05":
                        b[i] = 1;
                        break;
                    case "10:55":
                        b[i] = 2;
                        break;
                    case "11:45":
                        b[i] = 3;
                        break;
                    case "1:15":
                        b[i] = 4;
                        break;
                    case "2:05":
                        b[i] = 5;
                        break;
                    case "2:55":
                        b[i] = 6;
                        break;
                    case "3:45":
                        b[i] = 7;
                        break;
                    default:
                        b[i] = -1;
                }
            }
            else b[i]=-1;
        }
        return b;
    }
}
