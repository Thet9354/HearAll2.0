package com.example.hearlall.SpeechToText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hearlall.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SpeechToText_Activity extends AppCompatActivity {

    private TextView txtView_speechToText;

    private ImageView img_mic;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null)
                {
                    Intent data = result.getData();
                    txtView_speechToText.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
                }
            }
        });

        img_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");

                launcher.launch(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
            if (resultCode == REQUEST_CODE_SPEECH_INPUT)
            {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                );
                txtView_speechToText.setText(
                        Objects.requireNonNull(result).get(0)
                );
            }
        }
    }

    private void initWidget() {
        txtView_speechToText = findViewById(R.id.txtView_speechToText);

        img_mic = findViewById(R.id.img_mic);
    }
}