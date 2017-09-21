package com.example.nako.tutorial;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainTutorial extends AppCompatActivity {
    RelativeLayout r;
    private int count=0;
    private int stage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tutorial);
        getSupportActionBar().hide();
        r=(RelativeLayout)findViewById(R.id.activity_main_tutorial);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event!=null) {

            if(event.getAction()==MotionEvent.ACTION_DOWN) {

                DisplayMetrics d=new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(d);
                final float normalizedX = (event.getX()/(float)d.widthPixels)*2-1;
                final float normalizedY = -((event.getY()/(float)d.heightPixels)*2-1);

                count++;
                if (count %2==0) {
                    stage++;
                    if(normalizedX>0.3&&normalizedY>0.7)
                        stage=5;
                    switch (stage) {
                        case 1:
                            r.setBackgroundResource(R.drawable.simple4);
                            break;
                        case 2:
                            r.setBackgroundResource(R.drawable.simple3);
                            break;
                        case 3:
                            r.setBackgroundResource(R.drawable.simple2);
                            break;
                        case 4:
                            r.setBackgroundResource(R.drawable.simple1);
                            break;
                        case 5:
                            Intent intent = new Intent();
                            intent.setClass(this, MainActivity.class);
                            startActivity(intent);
                            break;
                    }

                }
            }
        }
        return super.onTouchEvent(event);
    }
}
