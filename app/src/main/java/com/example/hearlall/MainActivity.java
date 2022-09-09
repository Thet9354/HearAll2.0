package com.example.hearlall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hearlall.Registration.RegistrationPage_Activity;
import com.example.hearlall.SignIn.SignInPage_Activity;

public class MainActivity extends AppCompatActivity {

    private Button btn_SignIn, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        pageDirectories();

    }

    private void pageDirectories() {

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInPage_Activity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationPage_Activity.class));
            }
        });
    }

    private void initWidget() {

        btn_SignIn = findViewById(R.id.btn_SignIn);
        btn_register = findViewById(R.id.btn_register);
    }
}