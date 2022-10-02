package com.example.hearlall.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hearlall.MainActivity;
import com.example.hearlall.R;

public class Splash_Activity extends AppCompatActivity {

    private ImageView img_logo;

    private TextView txtView_features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //for fullscrren
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initWidget();

        //for animating logo
        StartAnimations();

//        //idle screen time
//        Thread timerThread = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//                }
//            }
//        };
//        timerThread.start();
    }

    private void initWidget() {
        img_logo = findViewById(R.id.img_logo);

        txtView_features = findViewById(R.id.txtView_features);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.reset();
        img_logo.clearAnimation();
        txtView_features.clearAnimation();
        img_logo.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                txtView_features.setVisibility(View.VISIBLE);
                Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
                anim2.reset();
                txtView_features.startAnimation(anim2);
                anim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}