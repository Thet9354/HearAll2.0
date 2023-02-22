package com.example.hearlall.Onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInPage_Activity extends AppCompatActivity {

    private EditText editTxt_PhoneNumber, editTxt_Password;
    private Button btn_signIn, btn_biometric;
    private androidx.appcompat.widget.AppCompatButton btn_facebook, btn_google;

    private String mPhoneNumber, mPassword;

    private Intent intent;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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

        initBiometric();

        pageDirectories();
    }

    private void initBiometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SignInPage_Activity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //f there's any error that comes up while auth
                Toast.makeText(SignInPage_Activity.this, "Error while using biometric login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Authentication successful
                Toast.makeText(SignInPage_Activity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //fail to authenticate
                Toast.makeText(SignInPage_Activity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        //Setup title, description on auth dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentiction")
                .setSubtitle("Login using fingerprint or face")
                .setNegativeButtonText("Cancel")
                .build();
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

        //onClickListener for different buttons
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Retrieve written data
                mPhoneNumber = editTxt_PhoneNumber.getText().toString();
                mPassword = editTxt_Password.getText().toString();

                validatePhoneNumber();
                validatePassword();
                validateInput();
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(SignInPage_Activity.this, Arrays.asList("public_profile"));

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

        btn_biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    private boolean validatePassword() {

        //Regex pattern to require alphanumeric and special characters
        Pattern regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = regexPassword.matcher(mPassword);

        if (mPassword.isEmpty())
        {
            editTxt_Password.setError("Required");
            return false;
        }
        else if (!matcher.matches())
        {
            editTxt_Password.setError("Invalid password");
            return false;
        }
        else
            return true;
    }

    private boolean validatePhoneNumber() {

        if (mPhoneNumber.isEmpty())
        {
            editTxt_PhoneNumber.setError("Required");
            return false;
        }
        else if (!Patterns.PHONE.matcher(mPhoneNumber).matches())
        {
            editTxt_PhoneNumber.setError("Invalid Phone");
            return false;
        }
        else
            return true;
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

        if (!validatePhoneNumber() | !validatePassword())
        {
            return;
        }
        else
        {
            //TODO: Do later after settling the signup
            //Authenticating with real time firebase database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(mPhoneNumber))
                    {
                        // Name exist in firebase database
                        // now getting password of user from firebase data and match if with user entered password and username

                        final String getName = snapshot.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Full Name").getValue(String.class);
                        final String getPhoneNumber = snapshot.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Phone Number").getValue(String.class);
                        final String getEmail = snapshot.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Email").getValue(String.class);
                        final String getPassword = snapshot.child(mPhoneNumber).child("User's Information").child("Log In Information").child("Password").getValue(String.class);

                        if ((getPassword.equals(mPassword)) && getPhoneNumber.equals(mPhoneNumber))
                        {
                            // Lead user to the Main Menu Page activity
                            Toast.makeText(SignInPage_Activity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainMenuPage_Activity.class);

                            intent.putExtra("Name", getName);
                            intent.putExtra("Phone Number", mPhoneNumber);
                            intent.putExtra("Password", mPassword);

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

    private void initWidget() {
        //---> EditText
        editTxt_PhoneNumber = findViewById(R.id.editTxt_PhoneNumber);
        editTxt_Password = findViewById(R.id.editTxt_Password);

        //---> Button
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_biometric = findViewById(R.id.btn_biometric);

        //---> ImageView
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_google = findViewById(R.id.btn_google);
    }
}