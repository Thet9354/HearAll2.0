package com.example.hearlall.Onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hearlall.MainMenuPage_Activity;
import com.example.hearlall.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPage_Activity extends AppCompatActivity {

    private TextView txtView_signIn;
    private EditText editTxt_name, editTxt_email, editTxt_phoneNumber, editTxt_password;
    private Button btn_register;
    private androidx.appcompat.widget.AppCompatButton btn_facebook, btn_google;

    private String mName, mEmail, mPhoneNumber, mPassword;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hearall-3017f-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference databaseReference  = database.getReference().child("users");

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private FirebaseAuth mAuth;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handlerFacebookAccessToken(loginResult.getAccessToken());
                        startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(RegistrationPage_Activity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(RegistrationPage_Activity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        initWidget();

        pageDirectories();
    }

    private void handlerFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in successful, update UI with the signed-in user's information
                            Toast.makeText(RegistrationPage_Activity.this, "Sign In successful", Toast.LENGTH_SHORT).show();

                            // Lead to the Main Page of the app
                            startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));
                        }
                        else
                        {
                            Toast.makeText(RegistrationPage_Activity.this, "Sign In unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pageDirectories() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Store values
                mName = editTxt_name.getText().toString();
                mEmail = editTxt_email.getText().toString();
                mPassword = editTxt_password.getText().toString();
                mPhoneNumber = editTxt_phoneNumber.getText().toString();

                validateName();
                validateEmail();
                validatePhoneNumber();
                validatePassword();
                addData();
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                gsc = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignIn();
                mAuth = FirebaseAuth.getInstance();
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(RegistrationPage_Activity.this, Arrays.asList("public_profile"));
            }
        });

        txtView_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationPage_Activity.this, SignInPage_Activity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validatePhoneNumber() {
        if (mPhoneNumber.isEmpty())
        {
            editTxt_phoneNumber.setError("Required");
            return false;
        }
        else
            return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private void googleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));
            finish();

            try
            {
                task.getResult(ApiException.class);
            }
            catch (ApiException e)
            {
                System.out.println("Lmao we're fucked");
            }
        }
        else
        {
            //If not request code is RC_SIGN_IN it must be facebook
            // Passing the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void addData() {
        if (!validateName() | !validateEmail() | !validatePassword() | !validatePhoneNumber())
        {
            return;
        }
        else {
            //Adding data into google realtime database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Checking if phone number is not registered before

                    if (snapshot.hasChild(mPhoneNumber))
                    {
                        //--->Asking user if he/she wants ti log in to existing account
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPage_Activity.this);
                        builder.setTitle("HearAll");
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
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Full Name").setValue(mName);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Email").setValue(mEmail);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Phone Number").setValue(mPhoneNumber);
                        databaseReference.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Password").setValue(mPassword);

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

                        startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validatePassword() {
        //Regex pattern to require alphanumeric and special characters
        Pattern regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = regexPassword.matcher(mPassword);

        if (mPassword.isEmpty())
        {
            editTxt_password.setError("Required");
            return false;
        }
        else if (!matcher.matches())
        {
            editTxt_password.setError("Your password's not strong enough");
            return false;
        }
        else
            return true;
    }

    private boolean validateEmail() {
        if (mEmail.isEmpty())
        {
            editTxt_email.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches())
        {
            editTxt_email.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            editTxt_email.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        //Regex pattern to allow only alphabets
        Pattern regexName = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = regexName.matcher(mName);

        if (mName.isEmpty())
        {
            editTxt_name.setError("Required");
            return false;
        }
        else if (!matcher.matches())
        {
            editTxt_name.setError("Invalid input");
            return false;
        }
        else
            return true;
    }

    private void initWidget() {
        txtView_signIn = findViewById(R.id.txtView_signIn);

        editTxt_name = findViewById(R.id.editTxt_name);
        editTxt_email = findViewById(R.id.editTxt_email);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_phoneNumber = findViewById(R.id.editTxt_phoneNumber);

        btn_register = findViewById(R.id.btn_register);

        btn_facebook = findViewById(R.id.btn_facebook);
        btn_google = findViewById(R.id.btn_google);

    }
}