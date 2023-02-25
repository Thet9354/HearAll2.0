package com.example.hearlall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView txtView_eventTitle, txtView_eventHost, txtView_eventHostDesc, txtView_eventDesc, txtView_seeMore, btn_back;
    private ImageView imgView_event, imgView_host;
    private com.google.android.material.button.MaterialButton btn_addToCalendar, btn_emailOrganizer, btn_registerEvent;
    private LinearLayout eventParent;

    private String eventTitle, hostName, hostDesc, eventParticipants, eventLink;
    private int eventImage, hostImage;

    private String mEmail = "thetpine254@gmail.com";
    private String mPhoneNumber = "93542856";


    private Intent intent;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hearall-3017f-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference databaseReference  = database.getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        intent = getIntent();

        initWidget();

        getIntentData();

        pageDirectories();
    }

    private void getIntentData() {

        eventTitle = intent.getStringExtra("Event Title");
        hostName = intent.getStringExtra("Host Name");
        hostDesc = intent.getStringExtra("Host Desc");
        eventParticipants = intent.getStringExtra("Event Participants");
        eventLink = intent.getStringExtra("Event Link");

        eventImage = intent.getIntExtra("Event Image", 0);
        hostImage = intent.getIntExtra("Host Image", 0);

        initUI();
    }

    private void initUI() {

        txtView_eventTitle.setText(eventTitle);
        txtView_eventHost.setText(hostName);
        txtView_eventHostDesc.setText(hostDesc);

        imgView_event.setImageResource(eventImage);
        imgView_host.setImageResource(hostImage);
    }

    private void pageDirectories() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtView_seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Link to actual link
                Uri uri = Uri.parse(eventLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        btn_addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add the event to your local calendar
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, eventTitle);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, hostDesc);
                intent.putExtra(CalendarContract.Events.ALL_DAY, "true");
                intent.putExtra(Intent.EXTRA_EMAIL, mEmail);

                startActivity(intent);
            }
        });

        btn_emailOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = "thetpne254@gmail.com";
                String subject = "Enquires about event";
                String message = "Your message";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                // Needed only if you are using email clients other than Gmail
                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Send email"));
            }
        });

        btn_registerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    private void addData() {

        String userEvent = "User's Events";

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(mPhoneNumber).hasChild(userEvent))
                {
                    long numOfTransaction = snapshot.child(mPhoneNumber).child("User's Events").getChildrenCount();
                    System.out.println(numOfTransaction);

                    for (int i = (int) numOfTransaction; i<=100; i++)
                    {
                        String eventID = "Event " + (numOfTransaction + 1);

                        databaseReference.child(mPhoneNumber).child("User's Events").child(eventID).child("Title").setValue(eventTitle);
                        databaseReference.child(mPhoneNumber).child("User's Events").child(eventID).child("Host").setValue(hostName);
                        databaseReference.child(mPhoneNumber).child("User's Events").child(eventID).child("Host Desc").setValue(hostDesc);
                        databaseReference.child(mPhoneNumber).child("User's Events").child(eventID).child("Link").setValue(eventLink);


                    }



                }
                else
                {
                    databaseReference.child(mPhoneNumber).child("User's Events").child("Event 1").child("Title").setValue(eventTitle);
                    databaseReference.child(mPhoneNumber).child("User's Events").child("Event 1").child("Host").setValue(hostName);
                    databaseReference.child(mPhoneNumber).child("User's Events").child("Event 1").child("Host Desc").setValue(hostDesc);
                    databaseReference.child(mPhoneNumber).child("User's Events").child("Event 1").child("Link").setValue(eventLink);


                }

                //Transition from this activity page to the fragment page

                Toast.makeText(EventDetailsActivity.this, "Event registered successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initWidget() {

        //TextView
        txtView_eventTitle = findViewById(R.id.txtView_eventTitle);
        txtView_eventHost = findViewById(R.id.txtView_eventHost);
        txtView_eventHostDesc = findViewById(R.id.txtView_eventHostDesc);
        txtView_eventDesc = findViewById(R.id.txtView_eventDesc);
        txtView_seeMore = findViewById(R.id.txtView_seeMore);
        btn_back = findViewById(R.id.btn_back);

        //MaterialButton
        btn_addToCalendar = findViewById(R.id.btn_addToCalendar);
        btn_emailOrganizer = findViewById(R.id.btn_emailOrganizer);
        btn_registerEvent = findViewById(R.id.btn_registerEvent);

        //ImageView
        imgView_event = findViewById(R.id.imgView_event);
        imgView_host = findViewById(R.id.imgView_host);

        //LinearLayout
        eventParent = findViewById(R.id.eventParent);
    }
}