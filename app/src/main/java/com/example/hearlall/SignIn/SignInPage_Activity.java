package com.example.hearlall.SignIn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Pattern;

public class SignInPage_Activity extends AppCompatActivity {

    private EditText editTxt_userName, editTxt_password, editTxt_PhoneNumber;
    private Button btn_signIn;
    private ImageView img_facebook, img_google, img_linkedin;

    private String Username, password, phoneNumber;

    private Intent intent;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hearall-3017f-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference databaseReference  = database.getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

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
                        Toast.makeText(SignInPage_Activity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(SignInPage_Activity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        intent = getIntent();

        initWidget();

        pageDirectories();
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

    private void handlerFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in successful, update UI with the signed-in user's information
                            Toast.makeText(SignInPage_Activity.this, "Sign In successful", Toast.LENGTH_SHORT).show();

                            // Lead to the Main Page of the app
                            startActivity(new Intent(getApplicationContext(), MainMenuPage_Activity.class));
                        }
                        else
                        {
                            Toast.makeText(SignInPage_Activity.this, "Sign In unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pageDirectories() {
        // Retrieve written data
        Username = editTxt_userName.getText().toString();
        password = editTxt_password.getText().toString();
        phoneNumber = editTxt_PhoneNumber.getText().toString();

        //onClickListener for different buttons
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUsername();
                validatePhoneNumber();
                validatePassword();
                validateInput();
            }
        });

        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(SignInPage_Activity.this, Arrays.asList("public_profile"));

            }
        });

        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                gsc = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignIn();
                mAuth = FirebaseAuth.getInstance();
            }
        });
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

            try {
                task.getResult(ApiException.class);
            } catch (ApiException e)
            {
                System.out.println("We are fucked");
            }
        }
    }

    private void validateInput() {
        if (!validateUsername() | !validatePassword() | !validatePhoneNumber())
        {
            return;
        }
        else
        {
            //Authenticating with real time firebase database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phoneNumber))
                    {
                        // Email exist in firebase database
                        // now getting password of user from firebase data and match if with user entered password and username

                        final String getUsername = snapshot.child(phoneNumber).child("User's Information").child("Username").getValue(String.class);
                        final String getPassword = snapshot.child(phoneNumber).child("User's Information").child("Password").getValue(String.class);

                        if ((getPassword.equals(password)) && getUsername.equals(Username))
                        {
                            // Lead user to the Main Menu Page activity
                            Toast.makeText(SignInPage_Activity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainMenuPage_Activity.class);
                            intent.putExtra("editTxt_mobileNumber", phoneNumber);
                            intent.putExtra("Username", Username);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(SignInPage_Activity.this, "Log In unsuccessful, please check your password or username", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignInPage_Activity.this, "Log In unsuccessful, please check your mobile number", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private boolean validatePassword() {
        // defining our own password pattern
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$");

        password = editTxt_password.getText().toString();
        if (password.isEmpty())
        {
            editTxt_password.setError("Field can not be empty");
            return false;
        }
        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(password).matches())
        {
            editTxt_password.setError("Password is too weak");
            return false;
        }
        else
        {
            editTxt_password.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        // defining our own mobile number pattern
        final Pattern Phone_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{8,}" +                // at least 8 characters
                        "$");

        phoneNumber = editTxt_PhoneNumber.getText().toString();

        if (phoneNumber.isEmpty())
        {
            editTxt_PhoneNumber.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.PHONE.matcher(phoneNumber).matches())
        {
            editTxt_PhoneNumber.setError("Please enter a valid mobile number");
            return false;
        }
        else
        {
            editTxt_PhoneNumber.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        Username = editTxt_userName.getText().toString();

        if (Username.isEmpty())
        {
            editTxt_userName.setError("Field can not be empty");
            return false;
        }
        else
        {
            editTxt_userName.setError(null);
            return true;
        }
    }

    private void initWidget() {
        //---> EditText
        editTxt_userName = findViewById(R.id.editTxt_userName);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_PhoneNumber = findViewById(R.id.editTxt_PhoneNumber);

        //---> Button
        btn_signIn = findViewById(R.id.btn_signIn);

        //---> ImageView
        img_facebook = findViewById(R.id.img_facebook);
        img_google = findViewById(R.id.img_google);
        img_linkedin = findViewById(R.id.img_linkedin);
    }
}