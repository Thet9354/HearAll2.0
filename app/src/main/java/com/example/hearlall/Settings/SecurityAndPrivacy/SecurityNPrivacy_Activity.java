package com.example.hearlall.Settings.SecurityAndPrivacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.R;
import com.example.hearlall.Settings.Settings_Activity;

public class SecurityNPrivacy_Activity extends AppCompatActivity {

    private ImageView btn_back, img_ad_preferences, btn_ad_preferences, img_dataSharing, btn_dataSharing, img_locationInfo, btn_locationInfo,
            btn_privacyCenter, btn_privacyPolicy, btn_contactUs;

    private TextView txtView_Done, txtView_ad_preferences, txtView_dataSharing, txtView_locationInfo, txtView_privacyCenter, txtView_privacyPolicy,
            txtView_contactUs;

    private RelativeLayout rel_ad_preferences, rel_dataSharing, rel_locationInfo, rel_privacy, rel_privacyPolicy, rel_contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_nprivacy);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {
        /* onClickListener for back and done button **/
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtView_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings_Activity.class));
            }
        });


        /* onClickListener for Ads preferences section **/
        img_ad_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), Ad_Preference_Activity.class));
            }
        });

        txtView_ad_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), Ad_Preference_Activity.class));
            }
        });

        rel_ad_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), Ad_Preference_Activity.class));
            }
        });

        btn_ad_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), Ad_Preference_Activity.class));
            }
        });


        /*  onClickListener for Data sharing with business partner section **/
        img_dataSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), DataSharing_Activity.class));
            }
        });

        btn_dataSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), DataSharing_Activity.class));
            }
        });

        txtView_dataSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), DataSharing_Activity.class));
            }
        });

        rel_dataSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), DataSharing_Activity.class));
            }
        });


        /* onClickListener for Location Information section */
        img_locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LocationInfo_Activity.class));
            }
        });

        btn_locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LocationInfo_Activity.class));
            }
        });

        txtView_locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LocationInfo_Activity.class));
            }
        });

        rel_locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LocationInfo_Activity.class));
            }
        });


        /* onClickListener for Privacy center section */
        btn_privacyCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        txtView_privacyCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        rel_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });


        /* onClickListener for Privacy policy section */
        btn_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        txtView_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        rel_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        /* onClickListener for Contact us section */
        btn_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        txtView_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        rel_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(SecurityNPrivacy_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidget() {
        //---> ImageView
        btn_back = findViewById(R.id.btn_back);
        img_ad_preferences = findViewById(R.id.img_ad_preferences);
        btn_ad_preferences = findViewById(R.id.btn_ad_preferences);
        img_dataSharing = findViewById(R.id.img_dataSharing);
        btn_dataSharing = findViewById(R.id.btn_dataSharing);
        img_locationInfo = findViewById(R.id.img_locationInfo);
        btn_locationInfo = findViewById(R.id.btn_locationInfo);
        btn_privacyCenter = findViewById(R.id.btn_privacyCenter);
        btn_privacyPolicy = findViewById(R.id.btn_privacyPolicy);
        btn_contactUs = findViewById(R.id.btn_contactUs);

        //---> TextView
        txtView_Done = findViewById(R.id.txtView_Done);
        txtView_ad_preferences = findViewById(R.id.txtView_ad_preferences);
        txtView_dataSharing = findViewById(R.id.txtView_dataSharing);
        txtView_locationInfo = findViewById(R.id.txtView_locationInfo);
        txtView_privacyCenter = findViewById(R.id.txtView_privacyCenter);
        txtView_privacyPolicy = findViewById(R.id.txtView_privacyPolicy);
        txtView_contactUs = findViewById(R.id.txtView_contactUs);

        //---> RelativeLayout
        rel_ad_preferences = findViewById(R.id.rel_ad_preferences);
        rel_dataSharing = findViewById(R.id.rel_dataSharing);
        rel_locationInfo = findViewById(R.id.rel_locationInfo);
        rel_privacy = findViewById(R.id.rel_privacy);
        rel_privacyPolicy = findViewById(R.id.rel_privacyPolicy);
        rel_contactus = findViewById(R.id.rel_contactus);
    }
}