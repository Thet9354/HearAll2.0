package com.example.hearlall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hearlall.messages.QueryReplyPing;
import com.example.hearlall.messages.ServiceCommand;
import com.example.hearlall.messages.ServiceQueryPing;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class VolumeAmplifier_Activity extends AppCompatActivity {

    private SwitchMaterial enableService;
    private boolean enableServiceManualCheck = false;

    private ImageView btn_back;

    private boolean serviceRunning = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_amplifier);
        NotificationChannelHelper.createNotificationChannel(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        btn_back = findViewById(R.id.btn_back);

        enableService = findViewById(R.id.enableServiceSwitch);

        enableService.setChecked(false);

        enableService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (enableServiceManualCheck) return;
            if (isChecked) ServiceDispatcher.startService(this);
            else ServiceDispatcher.stopService(this);
        });

        LoudnessEnhancer enhancer = new LoudnessEnhancer(27721);

        EditText editTextNumber = findViewById(R.id.editTextNumber);
        Button setLoudnessButton = findViewById(R.id.setLoudnessButton);

        editTextNumber.setText(Objects.toString(sharedPreferences.getInt(GlobalVars.SP_LOUDNESS, 0) / 10.0));

        setLoudnessButton.setOnClickListener(v -> {
            try {

                double loudness = Double.parseDouble(editTextNumber.getText().toString());
                int intLoudness = (int) (loudness * 10);
                sharedPreferences.edit().putInt(GlobalVars.SP_LOUDNESS, intLoudness).commit();
                EventBus.getDefault().post(ServiceCommand.UPDATE);
            } catch (NumberFormatException ignored) {
                Toast.makeText(this, "Invalid loudness number", Toast.LENGTH_SHORT).show();
            }
        });
        enhancer.setEnabled(true);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new ServiceQueryPing());
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void handlePingReply(QueryReplyPing reply) {
        serviceRunning = true;
        enableServiceManualCheck = true;
        enableService.setChecked(true);
        enableServiceManualCheck = false;
        if (GlobalVars.DEBUG_TOAST)
            Toast.makeText(this, "Ping reply", Toast.LENGTH_SHORT).show();
    }
}