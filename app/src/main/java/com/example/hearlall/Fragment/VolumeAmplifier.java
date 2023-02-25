package com.example.hearlall.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.GlobalVars;
import com.example.hearlall.NotificationChannelHelper;
import com.example.hearlall.R;
import com.example.hearlall.ServiceDispatcher;
import com.example.hearlall.messages.QueryReplyPing;
import com.example.hearlall.messages.ServiceCommand;
import com.example.hearlall.messages.ServiceQueryPing;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;


public class VolumeAmplifier extends Fragment {

    private SwitchMaterial enableService;
    private boolean enableServiceManualCheck = false;

    private EditText editTxt_volumeAmplifier;

    private TextView txtView_done;

    private Button setLoudnessButton;

    private boolean serviceRunning = false;
    private SharedPreferences sharedPreferences;

    LoudnessEnhancer enhancer = new LoudnessEnhancer(27721);

    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_volume_amplifier, container, false);

        mContext = getActivity();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        NotificationChannelHelper.createNotificationChannel(mContext);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);


    }

    private void findViews(View v) {

        editTxt_volumeAmplifier = v.findViewById(R.id.editTxt_volumeAmplifier);

        txtView_done = v.findViewById(R.id.txtView_done);

        setLoudnessButton = v.findViewById(R.id.setLoudnessButton);

        enableService = v.findViewById(R.id.enableServiceSwitch);

        initUI();

        pageDirectories();

    }

    private void pageDirectories() {

        enableService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (enableServiceManualCheck) return;
            if (isChecked) ServiceDispatcher.startService(mContext);
            else ServiceDispatcher.stopService(mContext);
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
                    Toast.makeText(mContext, "Loudness has been set", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException ignored) {
                    Toast.makeText(mContext, "Invalid loudness number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        enhancer.setEnabled(true);


    }

    private void initUI() {
        enableService.setChecked(false);
        editTxt_volumeAmplifier.setText(Objects.toString(sharedPreferences.getInt(GlobalVars.SP_LOUDNESS, 0) / 10.0));
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new ServiceQueryPing());
    }

    @Override
    public void onStop() {
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
            Toast.makeText(mContext, "Ping reply", Toast.LENGTH_SHORT).show();
    }
}