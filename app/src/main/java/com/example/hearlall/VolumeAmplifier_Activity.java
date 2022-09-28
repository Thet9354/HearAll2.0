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
import android.widget.TextView;
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

    private EditText editTxt_volumeAmplifier;

    private TextView txtView_done;

    private Button setLoudnessButton;

    private boolean serviceRunning = false;
    private SharedPreferences sharedPreferences;

    LoudnessEnhancer enhancer = new LoudnessEnhancer(27721);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_amplifier);
        NotificationChannelHelper.createNotificationChannel(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        initWidget();

        initUI();

        pageDirectories();

    }

    private void pageDirectories() {
        enableService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (enableServiceManualCheck) return;
            if (isChecked) ServiceDispatcher.startService(this);
            else ServiceDispatcher.stopService(this);
        });

        setLoudnessButton.setOnClickListener(v -> {
            dbValidation();
            
            if (!dbValidation())
            {
                return;
            }
            else
            {
                try {

                    double loudness = Double.parseDouble(editTxt_volumeAmplifier.getText().toString());
                    int intLoudness = (int) (loudness * 10);
                    sharedPreferences.edit().putInt(GlobalVars.SP_LOUDNESS, intLoudness).commit();
                    EventBus.getDefault().post(ServiceCommand.UPDATE);
                    Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException ignored) {
                    Toast.makeText(this, "Invalid loudness number", Toast.LENGTH_SHORT).show();
                }   
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

    private boolean dbValidation() {
        int db = Integer.parseInt(editTxt_volumeAmplifier.getText().toString());
        if (db > 30)
        {
            editTxt_volumeAmplifier.setError("Woah that's a bit too much don't you think?");
            return false;
        }
        else if (db < 0)
        {
            editTxt_volumeAmplifier.setError("This is a volume amplifier, not a reducer");
            return false;
        }
        else if (editTxt_volumeAmplifier.getText().toString().isEmpty())
        {
            editTxt_volumeAmplifier.setError("Field cannot be empty to proceed");
            return false;
        }
        else 
            return true;
    }

    private void initUI() {
        enableService.setChecked(false);
        editTxt_volumeAmplifier.setText(Objects.toString(sharedPreferences.getInt(GlobalVars.SP_LOUDNESS, 0) / 10.0));

    }

    private void initWidget() {
        btn_back = findViewById(R.id.btn_back);

        editTxt_volumeAmplifier = findViewById(R.id.editTxt_volumeAmplifier);

        txtView_done = findViewById(R.id.txtView_done);

        setLoudnessButton = findViewById(R.id.setLoudnessButton);

        enableService = findViewById(R.id.enableServiceSwitch);
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