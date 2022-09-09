package com.example.hearlall.Settings.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.R;

public class Feedback_Activity extends AppCompatActivity {

    private ImageView btn_back, btn_send;

    private TextView txtView_ownEmail;

    private EditText editTxt_from, editTxt_subject, editTxt_message;

    private String ownEmail, userEmail, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
                validateSubject();
                validateMessage();
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        if (!validateEmail() | !validateMessage() | !validateSubject())
        {
            return;
        }
        else
        {
            ownEmail = txtView_ownEmail.toString();
            String[] addresses = ownEmail.split(",");

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "No App is Installed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateMessage() {
        String messageInput = editTxt_message.getText().toString().trim();

        if (messageInput.isEmpty())
        {
            editTxt_message.setError("Field cannot be empty");
            return false;
        }
        else
        {
            editTxt_message.setError(null);
            message = messageInput;
            return true;
        }
    }

    private boolean validateSubject() {
        String subjectInput = editTxt_subject.getText().toString().trim();

        if (subjectInput.isEmpty())
        {
            editTxt_subject.setError("Field cannot be empty");
            return false;
        }
        else
        {
            editTxt_subject.setError(null);
            subject = subjectInput;
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = editTxt_from.getText().toString().trim();

        if (emailInput.isEmpty())
        {
            editTxt_from.setError("Field cannot be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            editTxt_from.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            editTxt_from.setError(null);
            userEmail = emailInput;
            return true;
        }
    }

    private void initWidget() {
        //--->Button
        btn_back = findViewById(R.id.btn_back);
        btn_send = findViewById(R.id.btn_send);

        //--->TextView
        txtView_ownEmail = findViewById(R.id.txtView_ownEmail);

        //--->EditText
        editTxt_from = findViewById(R.id.editTxt_from);
        editTxt_subject = findViewById(R.id.editTxt_subject);
        editTxt_message = findViewById(R.id.editTxt_message);
    }
}