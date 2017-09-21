package com.example.nako.tutorial;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.view.MotionEvent;

import com.example.nako.tutorial.Object.Cube;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private Timer timer = new Timer(true);
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    final MyRender R=new MyRender(this,this);
    private int score,pre_score=0;
    //private ActionBar actionBar = getSupportActionBar();

    public void myMethod(int s)
    {
        score=s;
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                if(pre_score-score!=0) {
                    pre_score=score;
                    getSupportActionBar().setTitle("3D Tetris                               Score: " + score);
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
                }
            }
        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        timer.schedule(new MyTimerTask(), 2000, 2000);
        //getSupportActionBar().hide();
        final ActivityManager AM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo CInfo = AM.getDeviceConfigurationInfo();
        final boolean SupFlag = CInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));

        if (SupFlag) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            glSurfaceView.setRenderer(R);
            glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event!=null) {
                        final float normalizedX =
                                (event.getX() / (float) v.getWidth()) * 2 - 1;
                        final float normalizedY =
                                -((event.getY() / (float) v.getHeight()) * 2 - 1);

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    if(!R.tetris.GameStart)
                                        R.GameOver();
                                    else
                                        R.handleTouchPress(normalizedX, normalizedY);
                                }
                            });
                        }
                        else if (event.getAction() == MotionEvent.ACTION_MOVE)
                        {
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    if(R.tetris.GameStart)
                                        R.handleTouchDrag(normalizedX, normalizedY);
                                }
                            });
                        }
                        else if(event.getAction()==MotionEvent.ACTION_UP)
                        {
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    if(R.tetris.GameStart)
                                        R.handleTouchUp();
                                }
                            });
                        }
                        return true;
                    }
                    return false;
                }
            });


           // R.bar.setTitle(">");
            //actionBar.setTitle("?");
            setContentView(glSurfaceView);
            getSupportActionBar().setTitle("3D Tetris                                Score: 0");
            //getSupportActionBar().setTitle("Set Your Title Here");
            rendererSet = true;

        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }


        //setContentView(R.layout.activity_main);
    }
    private int t=0;
    public class MyTimerTask extends TimerTask
    {
        public void run()
        {
            R.tetris.Drop();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        if(rendererSet){
            glSurfaceView.onResume();
            //int a=R.GetScore();
        }
    }


}
