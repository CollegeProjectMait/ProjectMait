package com.example.parasrawat2124.projectmait;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    String[] timings;
    Spinner wday;
    TextView head;
    Spinner sub,teach,room;
    String day,time,type,teacher,roomno,section,actual;
    DatabaseReference dbref,dbref_teach;

    public ViewPagerAdapter(Context context, Spinner wday) {
        this.context = context;
        timings=context.getResources().getStringArray(R.array.time_slots);
        this.wday=wday;
    }

    @Override
    public int getCount() {
        return timings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        head = (TextView) itemView.findViewById(R.id.head);
        sub=(Spinner) itemView.findViewById(R.id.sub);
        teach=(Spinner) itemView.findViewById(R.id.teach);
        room=(Spinner) itemView.findViewById(R.id.room);
        RadioGroup radio=(RadioGroup) itemView.findViewById(R.id.radios);

        day=(String)wday.getSelectedItem();
        time=timings[position];
        RadioButton r=radio.findViewById(radio.getCheckedRadioButtonId());
        type=r.getText().toString();
        teacher=(String)teach.getSelectedItem();
        roomno=(String)room.getSelectedItem();
        section=(String)sub.getSelectedItem();

        head.setText("| "+day+"   "+time+" | ");
        final ArrayAdapter<CharSequence> sub_adapter= ArrayAdapter.createFromResource(this.context,R.array.clas,android.R.layout.simple_spinner_item);
        sub_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub.setAdapter(sub_adapter);
//        ArrayAdapter<CharSequence> teach_adapter= ArrayAdapter.createFromResource(this.context,R.array.teachers,android.R.layout.simple_spinner_item);
//        teach_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        teach.setAdapter(teach_adapter);
        ArrayAdapter<CharSequence> room_adapter= ArrayAdapter.createFromResource(this.context,R.array.rooms,android.R.layout.simple_spinner_item);
        room_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room.setAdapter(room_adapter);

        //later change it to singleEventListener
//        dbref_teach=FirebaseDatabase.getInstance().getReference().child("Teachers");
//        dbref_teach.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> teachers=new ArrayList<String>();
//                for(DataSnapshot teachSnapshot:dataSnapshot.getChildren()){
//                    String teacher_name=teachSnapshot.child("name").getValue(String.class);
//                    teachers.add(teacher_name);
//                }
//                ArrayAdapter<String> teach_adapt=new ArrayAdapter<String>(,android.R.layout.simple_spinner_item,teachers);
//                teach_adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                teach.setAdapter(teach_adapt);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        Button add=(Button) itemView.findViewById(R.id.button);
        dbref=FirebaseDatabase.getInstance().getReference().child("TimeTable").child("C11").child(day).child(time);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref.child("roomNo").setValue("room");
                //dbref.child("sub").setValue("section");
                dbref.child("teacher").setValue("teacher");
            }
        });

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
