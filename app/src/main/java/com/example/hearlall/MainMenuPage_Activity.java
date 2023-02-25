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
import android.widget.Toast;

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

    private com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton fab_parent;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_faq, fab_settings;
    private TextView txtView_faq, txtView_settings;

    private Boolean isAllFABVisible;

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

        //FAB
        fab_parent = findViewById(R.id.fab_parent);
        fab_faq = findViewById(R.id.fab_faq);
        fab_settings = findViewById(R.id.fab_settings);

        //TextView
        txtView_faq = findViewById(R.id.txtView_faq);
        txtView_settings = findViewById(R.id.txtView_settings);

        initUI();

        bottomMenu();
    }

    private void initUI() {

        fab_faq.setVisibility(View.GONE);
        fab_settings.setVisibility(View.GONE);
        txtView_faq.setVisibility(View.GONE);
        txtView_settings.setVisibility(View.GONE);

        isAllFABVisible = false;

        fab_parent.shrink();

        pageDirectories();
    }

    private void pageDirectories() {

        fab_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFABVisible)
                {
                    fab_faq.show();
                    fab_settings.show();
                    txtView_faq.setVisibility(View.VISIBLE);
                    txtView_settings.setVisibility(View.VISIBLE);

                    fab_parent.extend();

                    isAllFABVisible = true;
                }
                else
                {
                    fab_faq.hide();
                    fab_settings.hide();
                    txtView_faq.setVisibility(View.GONE);
                    txtView_settings.setVisibility(View.GONE);

                    fab_parent.shrink();

                    isAllFABVisible = false;
                }
            }
        });

        fab_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatBot_Activity.class));
            }
        });

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
            }
        });
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