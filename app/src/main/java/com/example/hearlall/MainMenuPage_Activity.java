package com.example.hearlall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hearlall.Adapters.ArticleAdapter;
import com.example.hearlall.Adapters.EventsAdapter;
import com.example.hearlall.Adapters.TutorialAdapter;
import com.example.hearlall.Fragment.Home;
import com.example.hearlall.Fragment.Recognition;
import com.example.hearlall.Fragment.SignLanguage;
import com.example.hearlall.Fragment.Speech_To_Text;
import com.example.hearlall.Fragment.VolumeAmplifier;
import com.example.hearlall.Model.Article;
import com.example.hearlall.Model.Events;
import com.example.hearlall.Model.Tutorial;
import com.example.hearlall.Settings.Settings_Activity;
import com.example.hearlall.SignLanguage.MainSignLanguage_Activity;
import com.example.hearlall.SpeechToText.SpeechToText_Activity;
import com.example.hearlall.TextToSpeech.TextToSpeech_Activity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainMenuPage_Activity extends AppCompatActivity {

    private RelativeLayout hearbot_RL, btn_bluetooth, btn_speechToSL, btn_speechToText, btn_textToSpeech, btn_textToSL, btn_slToText, btn_volumeAmp, btn_settings;
    private androidx.recyclerview.widget.RecyclerView rv_articles, rv_events, rv_tutorial;

    // Event section
    private EventsAdapter eventsAdapter;
    private final ArrayList<Events> eventsArrayList = new ArrayList<>();

    // Article and blogs section
    private ArticleAdapter articleAdapter;
    private final ArrayList<Article> articleArrayList = new ArrayList<>();

    // Tutorial Section
    private TutorialAdapter tutorialAdapter;
    private final ArrayList<Tutorial> tutorialArrayList = new ArrayList<>();


    int[] eventPic = {R.drawable.event1_logo, R.drawable.event2_logo, R.drawable.event3_logo,
            R.drawable.event4_logo, R.drawable.event5_logo,  R.drawable.event6_logo,  R.drawable.event7_logo,  R.drawable.event8_logo};

    int[] eventHostPic = {R.drawable.safd, R.drawable.safd, R.drawable.safd,
            R.drawable.safd, R.drawable.safd,  R.drawable.safd,  R.drawable.safd,  R.drawable.safd};

    int[] blogArticle = {R.drawable.article1_logo, R.drawable.article2_logo, R.drawable.article3_logo,
            R.drawable.article4_logo, R.drawable.article5_logo, R.drawable.article6_logo, R.drawable.article7_logo, R.drawable.article8_logo};

    int[] tutorialPic = {R.drawable.tutorial1, R.drawable.tutorial2, R.drawable.tutorial3,
            R.drawable.tutorial4, R.drawable.tutorial5, R.drawable.tutorial6, R.drawable.tutorial7, R.drawable.tutorial8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu_page);

        initWidget();
    }

    private void initWidget() {

        //RelativeLayouts
        hearbot_RL = findViewById(R.id.hearbot_RL);

        //button
        btn_bluetooth = findViewById(R.id.btn_bluetooth);
        btn_speechToText = findViewById(R.id.btn_speechToText);
        btn_textToSpeech = findViewById(R.id.btn_textToSpeech);
        btn_textToSL = findViewById(R.id.btn_textToSL);
        btn_slToText = findViewById(R.id.btn_slToText);
        btn_speechToSL = findViewById(R.id.btn_speechToSL);
        btn_volumeAmp = findViewById(R.id.btn_volumeAmp);
        btn_settings = findViewById(R.id.btn_settings);

        //RecyclerView
        rv_articles = findViewById(R.id.rv_articles);
        rv_events = findViewById(R.id.rv_events);
        rv_tutorial = findViewById(R.id.rv_tutorial);

        initUI();

        pageDirectories();
    }

    private void initUI() {

        initEventRecView();

        initArticleRecView();

        initTutorialRecView();
    }

    private void initTutorialRecView() {

        //for better performance of recyclerview.

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        rv_tutorial.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_tutorial.setHasFixedSize(true);

        tutorialAdapter = new TutorialAdapter(getApplicationContext(), tutorialArrayList);
        rv_tutorial.setAdapter(tutorialAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setSmoothScrollbarEnabled(true);
        // orientation of linearlayoutmanager.
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setAutoMeasureEnabled(true);

        //set layoutmanager for recyclerview.
        rv_tutorial.setLayoutManager(llm);

        new loadTutorial().execute();
    }

    private void initArticleRecView() {

        //for better performance of recyclerview.

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        rv_articles.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_articles.setHasFixedSize(true);

        articleAdapter = new ArticleAdapter(getApplicationContext(), articleArrayList);
        rv_articles.setAdapter(articleAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
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

        eventsAdapter = new EventsAdapter(getApplicationContext(), eventsArrayList);
        rv_events.setAdapter(eventsAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
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
    Tutorial tutorial;

    class loadTutorial extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            try {

                String[] tutorialTitle = getResources().getStringArray(R.array.tutorial_title);

                for (int i = 0 ; i < tutorialTitle.length; i++)
                {
                    tutorial = new Tutorial();

                    tutorial.setTutorialName(tutorialTitle[i]);
                    tutorial.setTutorialPic(tutorialPic[i]);

                    tutorialArrayList.add(tutorial);
                    tutorial = null;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            if (articleArrayList != null && articleArrayList.size() > 0) {
                articleAdapter = new ArticleAdapter(getApplicationContext(), articleArrayList);
                rv_articles.setAdapter(articleAdapter);
                articleAdapter.notifyDataSetChanged();
            }
        }
    }

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
                articleAdapter = new ArticleAdapter(getApplicationContext(), articleArrayList);
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
                eventsAdapter = new EventsAdapter(getApplicationContext(), eventsArrayList);
                rv_events.setAdapter(eventsAdapter);
                eventsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void pageDirectories() {

        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Bluetooth_Activity.class));
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                startActivity(intent);
            }
        });

        btn_speechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SpeechToText_Activity.class);
                startActivity(intent);
            }
        });

        btn_textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextToSpeech_Activity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Text to Sign Language Activity

        btn_textToSL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        btn_slToText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainSignLanguage_Activity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Speech to Sign Language Activity

        btn_speechToSL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        // Edit Activity -> Link the Volume Amplifier Activity
        btn_volumeAmp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
            }
        });

        hearbot_RL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatBot_Activity.class);
                startActivity(intent);
            }
        });

    }


}