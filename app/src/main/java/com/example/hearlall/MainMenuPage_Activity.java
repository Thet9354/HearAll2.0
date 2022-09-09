package com.example.hearlall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hearlall.Call.ContactList_Activity;
import com.example.hearlall.Settings.Settings_Activity;
import com.example.hearlall.SignLanguage.First_SignLanguage_Activity;
import com.example.hearlall.SignLanguage.SignLanguage_Activity;
import com.example.hearlall.SoundMeter.SoundMeter_Activity;
import com.example.hearlall.SpeechToText.SpeechToText_Activity;

public class MainMenuPage_Activity extends AppCompatActivity {

    private ImageView img_profilePic, img_speechToText, img_signLanguage, img_call, img_soundMeter,
            img_volumeAmplification, img_setting;

    private TextView txtView_username, txtView_speechToText, txtView_signLanguage, txtView_call, txtView_soundMeter,
            txtView_volumeAmplification, txtView_setting;

    private androidx.cardview.widget.CardView cardView_speechToText, cardView_signLanguage, cardView_call,
            cardView_soundMeter, cardView_volumeAmplification, cardView_setting;

    private Intent intent;

    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_page);

        intent = getIntent();

        mPhoneNumber = intent.getStringExtra("editTxt_mobileNumber");
        System.out.println(mPhoneNumber);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {
        //--->Speech To Text
        cardView_speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SpeechToText_Activity.class));
            }
        });

        txtView_speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SpeechToText_Activity.class));
            }
        });

        img_speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SpeechToText_Activity.class));
            }
        });

        //--->Sign Language
        cardView_signLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), First_SignLanguage_Activity.class));
            }
        });

        txtView_signLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), First_SignLanguage_Activity.class));
            }
        });

        img_signLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), First_SignLanguage_Activity.class));
            }
        });

        //--->Call
        cardView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactList_Activity.class));
            }
        });

        txtView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactList_Activity.class));
            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactList_Activity.class));
            }
        });

        //--->SoundMeter
        cardView_soundMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoundMeter_Activity.class));
            }
        });

        txtView_soundMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoundMeter_Activity.class));
            }
        });

        img_soundMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoundMeter_Activity.class));
            }
        });

        //--->Volume Amplifier
        cardView_volumeAmplification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VolumeAmplifier_Activity.class));
            }
        });

        txtView_volumeAmplification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VolumeAmplifier_Activity.class));
            }
        });

        img_volumeAmplification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VolumeAmplifier_Activity.class));
            }
        });

        //--->Settings
        cardView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                intent.putExtra("PhoneNumber", mPhoneNumber);
                startActivity(intent);
            }
        });

        txtView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                intent.putExtra("Phone Number", mPhoneNumber);
                startActivity(intent);
            }
        });

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                intent.putExtra("PhoneNumber", mPhoneNumber);
                startActivity(intent);
            }
        });

    }

    private void initWidget() {
        //--->ImageView
        img_profilePic = findViewById(R.id.img_profilePic);
        img_speechToText = findViewById(R.id.img_speechToText);
        img_signLanguage = findViewById(R.id.img_signLanguage);
        img_call = findViewById(R.id.img_call);
        img_soundMeter = findViewById(R.id.img_soundMeter);
        img_volumeAmplification = findViewById(R.id.img_volumeAmplification);
        img_setting = findViewById(R.id.img_setting);

        //--->TextView
        txtView_username = findViewById(R.id.txtView_username);
        txtView_speechToText = findViewById(R.id.txtView_speechToText);
        txtView_signLanguage = findViewById(R.id.txtView_signLanguage);
        txtView_call = findViewById(R.id.txtView_call);
        txtView_soundMeter = findViewById(R.id.txtView_soundMeter);
        txtView_volumeAmplification = findViewById(R.id.txtView_volumeAmplification);
        txtView_setting = findViewById(R.id.txtView_setting);

        //--->CardView
        cardView_speechToText = findViewById(R.id.cardView_speechToText);
        cardView_signLanguage = findViewById(R.id.cardView_signLanguage);
        cardView_call = findViewById(R.id.cardView_call);
        cardView_soundMeter = findViewById(R.id.cardView_soundMeter);
        cardView_volumeAmplification = findViewById(R.id.cardView_volumeAmplification);
        cardView_setting = findViewById(R.id.cardView_setting);
    }
}