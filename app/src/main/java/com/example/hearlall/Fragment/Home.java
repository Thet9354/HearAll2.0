package com.example.hearlall.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.TextPaint;

import com.example.hearlall.Adapters.ArticleAdapter;
import com.example.hearlall.Adapters.EventsAdapter;
import com.example.hearlall.Bluetooth_Activity;
import com.example.hearlall.ChatBot_Activity;
import com.example.hearlall.ComingSoonActivity;
import com.example.hearlall.Model.Article;
import com.example.hearlall.Model.Events;
import com.example.hearlall.R;
import com.example.hearlall.Settings.Settings_Activity;
import com.example.hearlall.SignLanguage.SignLanguage_Activity;
import com.example.hearlall.SpaceItemDecoration;
import com.example.hearlall.SpeechToText.SpeechToText_Activity;
import com.example.hearlall.TextToSpeech.TextToSpeech_Activity;

import java.util.ArrayList;


public class Home extends Fragment {

    private TextView tv_organiserFullName;

    private RelativeLayout hearbot_RL, btn_bluetooth, btn_speechToSL, btn_speechToText, btn_textToSpeech, btn_textToSL, btn_slToText, btn_volumeAmp, btn_settings;
    private androidx.recyclerview.widget.RecyclerView rv_articles, rv_events;

    private Context mContext;

    private EventsAdapter eventsAdapter;
    private final ArrayList<Events> eventsArrayList = new ArrayList<>();

    private ArticleAdapter articleAdapter;
    private final ArrayList<Article> articleArrayList = new ArrayList<>();


    int[] eventPic = {R.drawable.event1_logo, R.drawable.event2_logo, R.drawable.event3_logo,
            R.drawable.event4_logo, R.drawable.event5_logo,  R.drawable.event6_logo,  R.drawable.event7_logo,  R.drawable.event8_logo};

    int[] eventHostPic = {R.drawable.safd, R.drawable.safd, R.drawable.safd,
            R.drawable.safd, R.drawable.safd,  R.drawable.safd,  R.drawable.safd,  R.drawable.safd};

    int[] blogArticle = {R.drawable.article1_logo, R.drawable.article2_logo, R.drawable.article3_logo,
            R.drawable.article4_logo, R.drawable.article5_logo, R.drawable.article6_logo, R.drawable.article7_logo, R.drawable.article8_logo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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

        //RelativeLayouts
        hearbot_RL = v.findViewById(R.id.hearbot_RL);

        // tv_organiserFullName = v.findViewById(R.id.txtView_hostDesc);

        btn_bluetooth = v.findViewById(R.id.btn_bluetooth);
        btn_speechToText = v.findViewById(R.id.btn_speechToText);
        btn_textToSpeech = v.findViewById(R.id.btn_textToSpeech);
        btn_textToSL = v.findViewById(R.id.btn_textToSL);
        btn_slToText = v.findViewById(R.id.btn_slToText);
        btn_speechToSL = v.findViewById(R.id.btn_speechToSL);
        btn_volumeAmp = v.findViewById(R.id.btn_volumeAmp);
        btn_settings = v.findViewById(R.id.btn_settings);

        //RecyclerView
        rv_articles = v.findViewById(R.id.rv_articles);
        rv_events = v.findViewById(R.id.rv_events);

        initUI();

        pageDirectories();

    }

    private void initUI() {

        initEventRecView();

        initArticleRecView();
    }

    private void initArticleRecView() {

        //for better performance of recyclerview.

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        rv_articles.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_articles.setHasFixedSize(true);

        articleAdapter = new ArticleAdapter(getContext(), articleArrayList);
        rv_articles.setAdapter(articleAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setSmoothScrollbarEnabled(true);
        // orientation of linearlayoutmanager.
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setAutoMeasureEnabled(true);

        //set layoutmanager for recyclerview.
        rv_articles.setLayoutManager(llm);

        new loadArticle().execute();
    }

    private void initEventRecView() {

        //for better performance of recyclerview.

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        rv_events.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_events.setHasFixedSize(true);

        eventsAdapter = new EventsAdapter(getContext(), eventsArrayList);
        rv_events.setAdapter(eventsAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setSmoothScrollbarEnabled(true);
        // orientation of linearlayoutmanager.
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setAutoMeasureEnabled(true);

        //set layoutmanager for recyclerview.
        rv_events.setLayoutManager(llm);

        new loadEvents().execute();
    }

    Events events;
    Article article;

    class loadArticle extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            try {

                String[] articleTitle = getResources().getStringArray(R.array.article_title);

                for (int i = 0 ; i < articleTitle.length; i++)
                {
                    article = new Article();

                    article.setArticle(articleTitle[i]);
                    article.setArticlePic(blogArticle[i]);

                    articleArrayList.add(article);
                    article = null;
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            if (articleArrayList != null && articleArrayList.size() > 0) {
                articleAdapter = new ArticleAdapter(mContext, articleArrayList);
                rv_articles.setAdapter(articleAdapter);
                articleAdapter.notifyDataSetChanged();
            }
        }
    }

    class loadEvents extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            try {

                String[] eventTitle = getResources().getStringArray(R.array.event_Title);
                String[] eventOrganiser = getResources().getStringArray(R.array.event_organiser);



                String[] eventOrganiserDesc = getResources().getStringArray(R.array.event_organiserDesc);
                String[] eventLink = getResources().getStringArray(R.array.event_link);


                for (int i = 0 ; i < eventTitle.length; i++)
                {
                    events = new Events();

//                    String originalString = eventOrganiserDesc[i].toString();
//                    CharSequence ellipsized = TextUtils.ellipsize(originalString, tv_organiserFullName.paint(), maxLength, TextUtils.TruncateAt.END);
//                    textView.setText(ellipsized);

                    events.setEventImage(eventPic[i]);
                    events.setHostImage(eventHostPic[i]);

                    events.setEventTitle(eventTitle[i]);
                    events.setEventHost(eventOrganiser[i]);

                    events.setHostDesc(eventOrganiserDesc[i]);
                    events.setEventLink(eventLink[i]);

                    eventsArrayList.add(events);
                    events = null;
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        protected void onPostExecute(String file_url) {

//            pgbPopulardestination.setVisibility(View.GONE);

            if (eventsArrayList != null && eventsArrayList.size() > 0) {
                eventsAdapter = new EventsAdapter(mContext, eventsArrayList);
                rv_events.setAdapter(eventsAdapter);
                eventsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void pageDirectories() {

        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Bluetooth_Activity.class));
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Settings_Activity.class);
                startActivity(intent);
            }
        });

        btn_speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SpeechToText_Activity.class);
                startActivity(intent);
            }
        });

        btn_textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TextToSpeech_Activity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Text to Sign Language Activity

        btn_textToSL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        btn_slToText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SignLanguage_Activity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Speech to Sign Language Activity

        btn_speechToSL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Volume Amplifier Activity
        btn_volumeAmp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        hearbot_RL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatBot_Activity.class);
                startActivity(intent);
            }
        });

    }
}