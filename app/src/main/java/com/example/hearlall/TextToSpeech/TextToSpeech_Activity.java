package com.example.hearlall.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hearlall.R;

import java.util.Locale;

public class TextToSpeech_Activity extends AppCompatActivity{

    private EditText editTxt_text;

    private Button btn_speak;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        initWidget();

        initUi();

        pageDirectories();
    }

    private void initUi() {

        textToSpeech = new TextToSpeech(TextToSpeech_Activity.this,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS){
                            int language = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });
    }

    private void pageDirectories() {

        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editTxt_text.getText().toString();
                if (s.isEmpty())
                {
                    editTxt_text.setError("Input cannot be empty");
                }
                else
                {
                    int speech = textToSpeech.speak(s, textToSpeech.QUEUE_FLUSH,null);
                }

            }
        });
    }

    private void initWidget() {

        editTxt_text = findViewById(R.id.editTxt_text);

        btn_speak = findViewById(R.id.btn_speak);
    }
}