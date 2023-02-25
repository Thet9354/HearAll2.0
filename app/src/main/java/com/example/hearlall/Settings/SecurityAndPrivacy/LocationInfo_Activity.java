package com.example.hearlall.Settings.SecurityAndPrivacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.R;
import com.example.hearlall.Settings.Settings_Activity;

public class LocationInfo_Activity extends AppCompatActivity {

    private ImageView btn_back;

    private TextView txtView_Done;

    private androidx.appcompat.widget.SwitchCompat locationInformation_switch, preciseLocation_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {

        txtView_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
            }
        });

        /* onCheckedChangedListener for locationInformation_switch */
        locationInformation_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    onResume();
                else
                    Toast.makeText(LocationInfo_Activity.this, "Personalized location has been turned off", Toast.LENGTH_SHORT).show();
            }
        });

        /* onCheckedChangedListener for preciseLocation_switch */
        preciseLocation_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    Toast.makeText(LocationInfo_Activity.this, "Precise location has been turned on", Toast.LENGTH_SHORT).show();
                else
                    onResume();
            }
        });
    }

    private void initWidget() {
        btn_back = findViewById(R.id.btn_back);
        txtView_Done = findViewById(R.id.txtView_Done);
        locationInformation_switch = findViewById(R.id.locationInformation_switch);
        preciseLocation_switch = findViewById(R.id.preciseLocation_switch);
    }
}