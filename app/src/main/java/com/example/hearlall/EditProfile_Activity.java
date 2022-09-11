package com.example.hearlall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hearlall.Registration.PersonalDecloration_Activity;
import com.example.hearlall.Registration.RegistrationPage_Activity;
import com.example.hearlall.Settings.Settings_Activity;
import com.example.hearlall.SignIn.SignInPage_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class EditProfile_Activity extends AppCompatActivity {

    private ImageView img_PFP, btn_back;

    private EditText editTxt_updateProfileName, editTxt_updateEmail, editTxt_updateProfileMobile, editTxt_updateUsername;

    private DatePicker datePicker_DOB;

    private Button btn_uploadPFP, btn_updateProfile;

    private String newFullName, newEmail, newDOB, newMobileNumber, newUsername;

    private String phoneNumber;

    private int currentYear, userAge, isAgeValid;

    private Intent intent;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hearall-3017f-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference databaseReference  = database.getReference().child("users");

    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        intent = getIntent();
        phoneNumber = intent.getStringExtra("Phone Number");
        System.out.println(phoneNumber);


        initWidget();

        pageDirectories();

    }

    private void pageDirectories() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Activity.this);
                builder.setTitle("SPACED");
                builder.setMessage("Are you sure you want to exit? All changes will be discarded.");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.create().show();
            }
        });

        btn_uploadPFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Update the user's profile pic
            }
        });

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Activity.this);
                builder.setTitle("SPACED");
                builder.setMessage("Are you sure you want to continue?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        newFullName = editTxt_updateProfileName.getText().toString();
                        newEmail = editTxt_updateEmail.getText().toString();
                        newUsername = editTxt_updateUsername.getText().toString();
                        newMobileNumber = editTxt_updateProfileMobile.getText().toString();

                        validateFullName();
                        validateUsername();
                        validateEmail();
                        validateDOB();
                        validateMobileNumber();
                        addData();
                    }
                });
                builder.create().show();
            }
        });
    }

    private boolean validateUsername() {
        // defining our own username pattern
        final Pattern USERNAME_PATTERN2 =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{5,}" +                // at least 4 characters
                        "$");

        if (newUsername.isEmpty())
        {
            editTxt_updateUsername.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!USERNAME_PATTERN2.matcher(newUsername).matches())
        {
            editTxt_updateUsername.setError("Please enter a valid username");
            return false;
        }
        else
        {
            editTxt_updateUsername.setError(null);
            return true;
        }
    }

    private void addData() {
        if (!validateFullName() | !validateEmail() | !validateMobileNumber() | !validateDOB() | !validateUsername())
        {
            return;
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Verification");
            builder.setMessage("We need some extra verification before we can proceed with the changes");

            View view = getLayoutInflater().inflate(R.layout.phone_number_verification, null);
            EditText editTxt_mobileNumber;
            Button btn_verify;
            editTxt_mobileNumber = view.findViewById(R.id.editTxt_mobileNumber);
            btn_verify = view.findViewById(R.id.btn_verify);

            btn_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String mPhoneNumber = editTxt_mobileNumber.getText().toString();

                    if (mPhoneNumber.isEmpty())
                    {
                        editTxt_mobileNumber.setError("Field cannot be empty");
                    }
                    else if (!Patterns.PHONE.matcher(mPhoneNumber).matches())
                    {
                        editTxt_mobileNumber.setError("Please enter a valid phone number");
                    }
                    else
                    {
                        //--->Updating data in firebase
                        HashMap user = new HashMap();
                        user.put("Phone Number", newMobileNumber);
                        user.put("Email", newEmail);
                        user.put("Full Name", newFullName);
                        user.put("Date Of Birth", newDOB);

                        databaseReference.child(mPhoneNumber)
                                .child("User's Information")
                                .updateChildren(user)
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (task.isSuccessful())
                                        {
                                            //--->Updating data in firestore
                                            firestoreDB.collection("user")
                                                    .whereEqualTo("Phone Number", mPhoneNumber)
                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful() && !task.getResult().isEmpty())
                                                            {
                                                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                                String documentID = documentSnapshot.getId();
                                                                firestoreDB.collection("user")
                                                                        .document(documentID)
                                                                        .update(user)
                                                                        .addOnSuccessListener(new OnSuccessListener() {
                                                                            @Override
                                                                            public void onSuccess(Object o) {
                                                                                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class)
                                                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(EditProfile_Activity.this, "An error occured, please try again later", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(EditProfile_Activity.this, "An error occurred while updating data in firebase realtime database", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }

    private boolean validateMobileNumber() {

        if (newMobileNumber.isEmpty())
        {
            editTxt_updateProfileMobile.setError("Field cannot be empty");
            return false;
        }
        // Matching the input email to a predefined email pattern
        else if (!Patterns.PHONE.matcher(newMobileNumber).matches())
        {
            editTxt_updateProfileMobile.setError("Please enter a valid mobile number");
            return false;
        }
        else
        {
            editTxt_updateProfileMobile.setError(null);
            return true;
        }
    }

    private boolean validateDOB() {
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

            newDOB = userDate + "/" + userMonth + "/" + userYear;

            return true;
        }
    }

    private boolean validateEmail() {
        if (newEmail.isEmpty())
        {
            editTxt_updateEmail.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
        {
            editTxt_updateEmail.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            editTxt_updateEmail.setError(null);
            return true;
        }
    }

    private boolean validateFullName() {
        //Defining our own Full Name pattern
        final Pattern FULLNAME_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "$");

        // Validating if the Full Name entered is valid
        if (newFullName.isEmpty())
        {
            editTxt_updateProfileName.setError("Field cannot be empty.");
            return false;
        }
        // if fullname matches the pattern
        // it will display an error message "Invalid Name"
        else if (FULLNAME_PATTERN.matcher(newFullName).matches())
        {
            editTxt_updateProfileName.setError("Invalid Full Name");
            return false;
        }
        else
        {
            editTxt_updateProfileName.setError(null);
            return true;
        }
    }

    private void initWidget() {
        //ImageView
        img_PFP = findViewById(R.id.img_PFP);
        btn_back = findViewById(R.id.btn_back);

        //EditText
        editTxt_updateProfileName = findViewById(R.id.editTxt_updateProfileName);
        editTxt_updateEmail = findViewById(R.id.editTxt_updateEmail);
        editTxt_updateProfileMobile = findViewById(R.id.editTxt_updateProfileMobile);
        editTxt_updateUsername = findViewById(R.id.editTxt_updateUsername);

        //DatePicker
        datePicker_DOB = findViewById(R.id.datePicker_DOB);

        //Button
        btn_uploadPFP = findViewById(R.id.btn_uploadPFP);
        btn_updateProfile = findViewById(R.id.btn_updateProfile);
    }
}