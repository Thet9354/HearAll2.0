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
import com.example.hearlall.SignLanguage.SignLanguage_Activity;
import com.example.hearlall.SignLanguage.SignLanguageAI_Activity;

public class SignLanguage extends Fragment {

   private Button btn_frequentlyUsed, btn_numbers, btn_alphabets, btn_ai_signLanguage;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_language, container, false);

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

        //ImageView
        btn_frequentlyUsed = v.findViewById(R.id.btn_frequentlyUsed);
        btn_numbers = v.findViewById(R.id.btn_numbers);
        btn_alphabets = v.findViewById(R.id.btn_alphabets);
        btn_ai_signLanguage = v.findViewById(R.id.btn_ai_signLanguage);

        pageDirectories();

    }

    private void pageDirectories() {

        btn_frequentlyUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 3);
                startActivity(intent);
            }
        });

        btn_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 2);
                startActivity(intent);
            }
        });

        btn_alphabets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignLanguage_Activity.class);
                intent.putExtra("Userchoice", 1);
                startActivity(intent);
            }
        });

        btn_ai_signLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SignLanguageAI_Activity.class));
            }
        });
    }


}