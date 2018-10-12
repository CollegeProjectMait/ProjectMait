package com.example.parasrawat2124.projectmait;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;


public class SplashScreen extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=findViewById(R.id.maitlogo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        imageView.startAnimation(animation);
        Thread timer=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashScreen.this,UserSelection.class));
                }


            }
        });
        timer.start();

    }
}
