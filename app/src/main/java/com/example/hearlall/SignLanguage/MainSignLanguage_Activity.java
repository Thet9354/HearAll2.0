package com.example.hearlall.SignLanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hearlall.R;

public class MainSignLanguage_Activity extends AppCompatActivity {

    private Button btn_frequentlyUsed, btn_numbers, btn_alphabets, btn_ai_signLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_language);

        initUI();
    }

    private void initUI() {

        //ImageView
        btn_frequentlyUsed = findViewById(R.id.btn_frequentlyUsed);
        btn_numbers = findViewById(R.id.btn_numbers);
        btn_alphabets = findViewById(R.id.btn_alphabets);
        btn_ai_signLanguage = findViewById(R.id.btn_ai_signLanguage);

        pageDirectories();
    }

    private void pageDirectories() {

        btn_frequentlyUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 3);
                startActivity(intent);
            }
        });

        btn_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 2);
                startActivity(intent);
            }
        });

        btn_alphabets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 1);
                startActivity(intent);
            }
        });

        btn_ai_signLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignLanguageAI_Activity.class));
            }
        });
    }
}