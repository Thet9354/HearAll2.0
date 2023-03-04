package com.example.hearlall.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hearlall.R;
import com.example.hearlall.SpeechToText.SpeechToText_Activity;
import com.example.hearlall.TextToSpeech.TextToSpeech_Activity;


public class Recognition extends Fragment {

    private Button btn_texttospeech, btn_speechtotext;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recognition, container, false);

        mContext = getActivity();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View v) {

        //Button
        btn_texttospeech = v.findViewById(R.id.btn_texttospeech);
        btn_speechtotext = v.findViewById(R.id.btn_speechtotext);

        pageDirectories();

    }

    private void pageDirectories() {

        btn_texttospeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, TextToSpeech_Activity.class));
            }
        });

        btn_speechtotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SpeechToText_Activity.class));
            }
        });
    }
}