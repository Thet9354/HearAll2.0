package com.example.hearlall.SignLanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hearlall.MainMenuPage_Activity;
import com.example.hearlall.R;

public class First_SignLanguage_Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_sign_language);

        Button btnalpha = (Button) findViewById(R.id.alphabets);
        Button btnnum = (Button) findViewById(R.id.numbers);
        Button btnfuw = (Button) findViewById(R.id.fuw);
        Button btn_ai_signLanguage = (Button) findViewById(R.id.btn_ai_signLanguage);
        ImageView backbtn = (ImageView) findViewById(R.id.backbutton_sign);
        ImageView homebtn = (ImageView) findViewById(R.id.homebutton_sign);


        btnalpha.setOnClickListener(this);
        btnnum.setOnClickListener(this);
        btnfuw.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        btn_ai_signLanguage.setOnClickListener(this);
        homebtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        /* Call MainActivity with appropriate choice */
        Intent intent = new Intent(First_SignLanguage_Activity.this, SignLanguage_Activity.class);
        switch (v.getId())
        {
            case R.id.alphabets:
                intent.putExtra("Userchoice", 1);
                startActivity(intent);
                finish();
                break;

            case R.id.numbers:
                intent.putExtra("Userchoice", 2);
                startActivity(intent);
                finish();
                break;

            case R.id.fuw:
                intent.putExtra("Userchoice", 3);
                startActivity(intent);
                finish();
                break;

            case R.id.backbutton_sign:
                startActivity(new Intent(First_SignLanguage_Activity.this, MainMenuPage_Activity.class));
                finish();
                break;

            case R.id.homebutton_sign:
                startActivity(new Intent(First_SignLanguage_Activity.this, SearchText_Activity.class));
                finish();
                break;

            case R.id.btn_ai_signLanguage:
                startActivity(new Intent(getApplicationContext(), AI_signLanguage_recognition_Activity.class));
                finish();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}