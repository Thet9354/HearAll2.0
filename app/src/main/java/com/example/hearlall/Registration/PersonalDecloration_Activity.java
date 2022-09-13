package com.example.hearlall.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.Country.CountryAdapter;
import com.example.hearlall.Country.CountryData;
import com.example.hearlall.Country.CustomSpinner;
import com.example.hearlall.MainMenuPage_Activity;
import com.example.hearlall.R;
import com.example.hearlall.SQLiteDatabase.UserDataBaseHelper;
import com.example.hearlall.SQLiteDatabase.UserModel;
import com.example.hearlall.SignIn.SignInPage_Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PersonalDecloration_Activity extends AppCompatActivity {

    private EditText editTxt_Fullname, editTxt_mobileNumber;
    private CustomSpinner spinner_country;
    private DatePicker datePicker_DOB;
    private TextView txtView_welcomeMsg;
    private androidx.appcompat.widget.AppCompatButton btn_register;

    private UserModel userModel;

    private UserDataBaseHelper dataBaseHelper;

    private CountryAdapter countryAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hearall-3017f-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference databaseReference  = database.getReference().child("users");

    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    private Intent intent;

    private String mCountry, mUsername, mEmail, mPassword, mFullname, mPhoneNumber, mDOB;

    private int currentYear, userAge, isAgeValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_decloration);

        intent = getIntent();

        initWidget();

        countrySpinner();

        pageDirectories();

    }

    private void countrySpinner() {
        countryAdapter = new CountryAdapter(PersonalDecloration_Activity.this, CountryData.getCountryList());
        spinner_country.setAdapter(countryAdapter);
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                mCountry = spinner_country.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void pageDirectories() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storing values inside variables
                mFullname = editTxt_Fullname.getText().toString();
                mPhoneNumber = editTxt_mobileNumber.getText().toString();

                //--->Validation process
                validateFullname();
                validatePhoneNumber();
                validateDateOfBirth();
                addData();
            }
        });
    }

    private void addData() {
        if (!validateFullname() | !validatePhoneNumber() | !validateDateOfBirth())
        {
            return;
        }
        else
        {
            try {
                userModel = new UserModel(-1, mUsername, mEmail, mPhoneNumber, mPassword, mFullname, mCountry, mDOB);
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error creating account, please try again", Toast.LENGTH_SHORT).show();
                userModel = new UserModel(-1, "error", "error", "error", "error", "error", "error", "error");
            }

            dataBaseHelper = new UserDataBaseHelper(PersonalDecloration_Activity.this);
            boolean success = dataBaseHelper.addOneUser(userModel);

            //Adding data into google realtime database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Checking if phone number is not registered before

                    if (snapshot.hasChild(mPhoneNumber))
                    {
                        //--->Asking user if he/she wants ti log in to existing account
                        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalDecloration_Activity.this);
                        builder.setTitle("SPACED");
                        builder.setMessage("Hey there, it seems like there's an existing account with the same phone number.");
                        builder.setNegativeButton("Register a new account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), RegistrationPage_Activity.class));
                            }
                        });
                        builder.setPositiveButton("Log in to existing account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), SignInPage_Activity.class));
                            }
                        });
                        builder.create().show();
                    }
                    else
                    {
                        //--->Adding user's personal information to firebase
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Full Name").setValue(mFullname);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Email").setValue(mEmail);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Phone Number").setValue(mPhoneNumber);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Password").setValue(mPassword);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Username").setValue(mUsername);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Country").setValue(mCountry);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Date Of Birth").setValue(mDOB);

                        //--->Adding user's setting to firebase realtime database
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Night Mode").setValue("ON");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Notifications").setValue("OFF");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Private Account").setValue("OFF");

                        //--->Security and Privacy
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Security and Privacy").child("Personalized Ads").setValue("ON");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Security and Privacy").child("Information Sharing").setValue("ON");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Security and Privacy").child("Personalized Place").setValue("ON");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Security and Privacy").child("Precise Location").setValue("OFF");

                        //--->TextSize
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Text Size").child("Default Text Size preference").setValue("ON");
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Security and Privacy").child("Customize Text Size").setValue("OFF");

                        //--->Language
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Language").child("Recommended").setValue("English");

                        //--->Additional Resources
                        databaseReference.child(mPhoneNumber).child("User's Information").child("User's Settings").child("Additional Resources").child("Send crash reports").setValue("ON");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //Implementing data into google firebase firestore
            Map<String, Object> user = new HashMap<>();
            user.put("Username", mUsername);
            user.put("Email", mEmail);
            user.put("Phone Number", mPhoneNumber);
            user.put("Password", mPassword);
            user.put("Full Name", mFullname);
            user.put("Country", mCountry);
            user.put("Date Of Birth", mDOB);

            user.put("Night Mode", "ON");
            user.put("Notifications", "OFF");
            user.put("Private Account", "OFF");

            user.put("Personalized Ads", "ON");
            user.put("Information Sharing", "ON");
            user.put("Personalized Place", "ON");
            user.put("Precised Location", "OFF");

            user.put("Default Text Size preference", "ON");
            user.put("Customize Text Size", "OFF");

            user.put("Language", "English");

            user.put("Send crash reports", "ON");

            firestoreDB.collection("user")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(PersonalDecloration_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainMenuPage_Activity.class);
                            intent.putExtra("editTxt_mobileNumber", mPhoneNumber);
                            intent.putExtra("Username", mUsername);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PersonalDecloration_Activity.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateDateOfBirth() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        userAge = datePicker_DOB.getYear();
        isAgeValid = currentYear - userAge;

        if (isAgeValid < 18)
        {
            Toast.makeText(this, "You have not meet the minimum age requirement", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            String userDate = String.valueOf(datePicker_DOB.getDayOfMonth());
            String userMonth = String.valueOf(datePicker_DOB.getMonth());
            String userYear = String.valueOf(datePicker_DOB.getYear());

            mDOB = userDate + "/" + userMonth + "/" + userYear;

            return true;
        }
    }

    private boolean validatePhoneNumber() {

        if (mPhoneNumber.isEmpty())
        {
            editTxt_mobileNumber.setError("Field cannot be empty");
            return false;
        }
        // Matching the input email to a predefined email pattern
        else if (!Patterns.PHONE.matcher(mPhoneNumber).matches())
        {
            editTxt_mobileNumber.setError("Please enter a valid mobile number");
            return false;
        }
        else
        {
            editTxt_mobileNumber.setError(null);
            return true;
        }
    }

    private boolean validateFullname() {
        //Defining our own Full Name pattern
        final Pattern FULLNAME_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "$");

        // Validating if the Full Name entered is valid
        if (mFullname.isEmpty())
        {
            editTxt_Fullname.setError("Field cannot be empty.");
            return false;
        }
        // if fullname matches the pattern
        // it will display an error message "Invalid Name"
        else if (FULLNAME_PATTERN.matcher(mFullname).matches())
        {
            editTxt_Fullname.setError("Invalid Full Name");
            return false;
        }
        else
        {
            editTxt_Fullname.setError(null);
            return true;
        }
    }

    private void initWidget() {
        editTxt_Fullname = findViewById(R.id.editTxt_Fullname);
        editTxt_mobileNumber = findViewById(R.id.editTxt_mobileNumber);

        spinner_country = findViewById(R.id.spinner_country);

        datePicker_DOB = findViewById(R.id.datePicker_DOB);

        btn_register = findViewById(R.id.btn_register);

        txtView_welcomeMsg = findViewById(R.id.txtView_welcomeMsg);

        //---> Bring values from previous slide here
        mUsername = intent.getStringExtra("editTxt_userName");
        mEmail = intent.getStringExtra("editTxt_email");
        mPassword = intent.getStringExtra("editTxt_password");

        //---> Set welcome message text
        txtView_welcomeMsg.setText("Hello " + mUsername);
    }
}