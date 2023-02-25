package com.example.hearlall.Settings.AdditionalResource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.R;
import com.example.hearlall.Settings.SecurityAndPrivacy.PrivacyPolicy_Activity;
import com.example.hearlall.Settings.Settings_Activity;

public class AdditionalResources_Activity extends AppCompatActivity {

    private ImageView btn_termsOfService, btn_privacyPolicy, btn_cookiesUsed, btn_legalNotices;

    private TextView txtView_Done, txtView_termsOfService, txtView_privacyPolicy, txtView_cookiesUsed, txtView_legalNotices;

    private RelativeLayout rel_termsOfService, rel_privacyPolicy, rel_cookiesUsed, rel_legalNotices;

    private androidx.appcompat.widget.SwitchCompat crashReport_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_resources);

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

        /* onCheckChangeListener for crashReport_switch */
        crashReport_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    onResume();
                }
                else
                    onResume();
            }
        });

        /* onClickListener for Terms of service section */
        rel_termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        txtView_termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });


        /* onClickListener for Privacy policy section */
        rel_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), PrivacyPolicy_Activity.class));
            }
        });

        btn_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), PrivacyPolicy_Activity.class));
            }
        });

        txtView_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), PrivacyPolicy_Activity.class));
            }
        });


        /* onClickListener for Cookie use section */
        rel_cookiesUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cookiesUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });

        txtView_cookiesUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                Toast.makeText(AdditionalResources_Activity.this, "This section is supposed to lead you to an external website which i have not developed, thanks for your understanding.", Toast.LENGTH_SHORT).show();
            }
        });


        /* onClickListener for Legal notice section */
        rel_legalNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LegalNotice_Activity.class));
            }
        });

        btn_legalNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LegalNotice_Activity.class));
            }
        });

        txtView_legalNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lead to another page
                startActivity(new Intent(getApplicationContext(), LegalNotice_Activity.class));
            }
        });
    }

    private void initWidget() {
        //--->Button
        btn_termsOfService = findViewById(R.id.btn_termsOfService);
        btn_privacyPolicy = findViewById(R.id.btn_privacyPolicy);
        btn_cookiesUsed = findViewById(R.id.btn_cookiesUsed);
        btn_legalNotices = findViewById(R.id.btn_legalNotices);

        //--->TextView
        txtView_Done = findViewById(R.id.txtView_Done);
        txtView_termsOfService = findViewById(R.id.txtView_termsOfService);
        txtView_privacyPolicy = findViewById(R.id.txtView_privacyPolicy);
        txtView_cookiesUsed = findViewById(R.id.txtView_cookiesUsed);
        txtView_legalNotices = findViewById(R.id.txtView_legalNotices);

        //--->RelativeLayout
        rel_termsOfService = findViewById(R.id.rel_termsOfService);
        rel_privacyPolicy = findViewById(R.id.rel_privacyPolicy);
        rel_cookiesUsed = findViewById(R.id.rel_cookiesUsed);
        rel_legalNotices = findViewById(R.id.rel_legalNotices);

        //--->Switch
        crashReport_switch = findViewById(R.id.crashReport_switch);
    }
}