package com.example.hearlall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hearlall.Call.ContactList_Activity;
import com.example.hearlall.Fragment.Home;
import com.example.hearlall.Fragment.SignLanguage;
import com.example.hearlall.Fragment.Speech_To_Text;
import com.example.hearlall.Fragment.VolumeAmplifier;
import com.example.hearlall.Settings.Settings_Activity;
import com.example.hearlall.SignLanguage.First_SignLanguage_Activity;
import com.example.hearlall.SignLanguage.SignLanguage_Activity;
import com.example.hearlall.SoundMeter.SoundMeter_Activity;
import com.example.hearlall.SpeechToText.SpeechToText_Activity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainMenuPage_Activity extends AppCompatActivity {

    private RelativeLayout fragment_container;
    private ChipNavigationBar bottom_nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu_page);

        initWidget();
    }

    private void initWidget() {
        fragment_container = findViewById(R.id.fragment_container);
        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        bottom_nav_bar.setItemSelected(R.id.bottom_nav_home, true);
        bottomMenu();
    }

    private void bottomMenu() {
        bottom_nav_bar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch(i)
                {
                    case R.id.bottom_nav_home:
                        fragment = new Home();
                        break;

                    case R.id.bottom_nav_speechToText:
                        fragment = new Speech_To_Text();
                        break;

                    case R.id.bottom_nav_amplifier:
                        fragment = new VolumeAmplifier();
                        break;

                    case R.id.bottom_nav_signLanguage:
                        fragment = new SignLanguage();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
}