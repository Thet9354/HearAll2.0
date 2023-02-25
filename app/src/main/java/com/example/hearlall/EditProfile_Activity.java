package com.example.hearlall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.Settings.Settings_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile_Activity extends AppCompatActivity {

    private ImageView btn_back;

    private de.hdodenhof.circleimageview.CircleImageView img_PFP;

    private EditText editTxt_updateProfileName, editTxt_updateEmail, editTxt_updateProfileMobile, editTxt_updatePassword;

    private TextView txtView_done;

    private androidx.appcompat.widget.AppCompatButton btn_uploadPFP, btn_updateProfile;

    private String newFullName, newEmail, newDOB, newMobileNumber, newPassword;

    private Uri imageUri;

    private String myUri = "a";

    private StorageTask uploadTask;

    private StorageReference storageProfilePicsRef;

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

        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");

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

        img_PFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Activity.this);
                builder.setTitle("SPACED");
                builder.setMessage("DO you wish to edit your profile picture?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent photoIntent = new Intent(Intent.ACTION_PICK);
                        photoIntent.setType("image/*");
                        startActivityForResult(photoIntent, 1);
                    }
                });
                builder.create().show();
            }
        });

        btn_uploadPFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile_Activity.this);
                builder.setTitle("SPACED");
                builder.setMessage("DO you wish to save changes to your profile picture?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseStorage.getInstance().getReference("images/" + myUri)
                                .putFile(imageUri)
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(EditProfile_Activity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(EditProfile_Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.create().show();
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
                        newPassword = editTxt_updatePassword.getText().toString();
                        newMobileNumber = editTxt_updateProfileMobile.getText().toString();

                        validateFullName();
                        validatePassword();
                        validateEmail();
                        validateMobileNumber();
                        addData();
                    }
                });
                builder.create().show();
            }
        });

        txtView_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("Password", newPassword)
                                .putExtra("PFP", myUri);
                startActivity(intent);
                finish();
            }
        });
    }


    private void addData() {
        if (!validateFullName() | !validateEmail() | !validateMobileNumber() | !validatePassword())
        {
            System.out.println("Shitttttt");
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
                        user.put("Password", newPassword);
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
                                                                                Toast.makeText(EditProfile_Activity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
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
            builder.setView(view).create().show();
        }
    }

    private boolean validatePassword() {
        //Regex pattern to require alphanumeric and special characters
        Pattern regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = regexPassword.matcher(newPassword);

        if (newPassword.isEmpty())
        {
            editTxt_updatePassword.setError("Required");
            return false;
        }
        else if (!matcher.matches())
        {
            editTxt_updatePassword.setError("Your password's not strong enough");
            return false;
        }
        else
            return true;
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
        editTxt_updatePassword = findViewById(R.id.editTxt_updatePassword);

        //TextView
        txtView_done = findViewById(R.id.txtView_done);

        //Button
        btn_uploadPFP = findViewById(R.id.btn_uploadPFP);
        btn_updateProfile = findViewById(R.id.btn_updateProfile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!=null)
        {
            imageUri = data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        }catch (IOException e){
            e.printStackTrace();
        }
        img_PFP.setImageBitmap(bitmap);
    }
}