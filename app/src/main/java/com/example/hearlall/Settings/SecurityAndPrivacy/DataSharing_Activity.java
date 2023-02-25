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

public class DataSharing_Activity extends AppCompatActivity {

    private ImageView btn_back;

    private TextView txtView_Done;

    private androidx.appcompat.widget.SwitchCompat dataSharing_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sharing);

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

        /* onCheckedChangedListener for dataSharing_switch */
        dataSharing_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    onResume();
                else
                    Toast.makeText(DataSharing_Activity.this, "Information sharing with business partners has been turned off", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initWidget() {
        btn_back = findViewById(R.id.btn_back);
        txtView_Done = findViewById(R.id.txtView_Done);
        dataSharing_switch = findViewById(R.id.dataSharing_switch);
    }
}