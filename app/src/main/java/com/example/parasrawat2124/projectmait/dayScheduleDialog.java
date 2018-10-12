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
    int[] dcards=new int[]{R.id.tday1,R.id.tday2,R.id.tday3,R.id.tday4,R.id.tday5};
    int[] cards,checkCard;
    int l;
    ArrayList<String> rooms,timeslots,days;

    public dayScheduleDialog(@NonNull Context context,String head,ArrayList<String> rooms,ArrayList<String> given) {
        super(context);

        l = given.size();
        this.rooms = rooms;

        if(head.equals("days")){
            setContentView(R.layout.time_schedule);
            this.days = given;
            cards=dcards;
            checkCard=checkDay();
        }
        else if(head.equals("times")) {
            setContentView(R.layout.day_schedule);
            this.timeslots = given;
            cards=tcards;
            checkCard=checkTime();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i=0;i<l;i++){
            if(checkCard[i]!=-1) {
                TextView t = findViewById(cards[checkCard[i]]);
                t.append(rooms.get(i)+"\n");
            }
        }
    }

    public int[] checkTime() {
        int[] b = new int[l];
        for (int i = 0; i < l; i++) {
            int dash = timeslots.get(i).indexOf("-");
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

    public int[] checkDay(){
        int[] b=new int[l];
        for (int i = 0; i < l; i++) {
            switch (days.get(i)) {
                case "Monday":
                    b[i] = 0;
                    break;
                case "Tuesday":
                    b[i] = 1;
                    break;
                case "Wednesday":
                    b[i] = 2;
                    break;
                case "Thursday":
                    b[i] = 3;
                    break;
                case "Friday":
                    b[i] = 4;
                    break;
                default:
                    b[i] = -1;
            }
        }
        return b;
    }
}
