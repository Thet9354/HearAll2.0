package com.example.hearlall.Registration;

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

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegistrationPage_Activity extends AppCompatActivity {

    private EditText editTxt_userName, editTxt_email, editTxt_password, editTxt_confirmPassword;

    private Button btn_register;

    private ImageView img_facebook, img_google, img_linkedin;

    private String mUsername, mEmail, mPassword, mConfirmPassword;

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
                mUsername = editTxt_userName.getText().toString();
                mEmail = editTxt_email.getText().toString();
                mPassword = editTxt_password.getText().toString();
                mConfirmPassword = editTxt_confirmPassword.getText().toString();

                validateUsername();
                validateEmail();
                validatePassword();
                validateConfirmPassword();
                addData();
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

        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(RegistrationPage_Activity.this, Arrays.asList("public_profile"));
            }
        });
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
        if (!validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword())
        {
            return;
        }
        else {
            // Add to personal SQLite database
            // Add to Google Firebase database
            // Move on to next activity page
            Intent intent = new Intent(getApplicationContext(), PersonalDecloration_Activity.class);
            intent.putExtra("editTxt_userName", mUsername);
            intent.putExtra("editTxt_email", mEmail);
            intent.putExtra("editTxt_password", mPassword);
            startActivity(intent);
        }
    }

    private boolean validateConfirmPassword() {

        // If password is not the same as confirm password
        // Error and invalid
        if (mPassword.equals(mConfirmPassword))
        {
            return true;
        }
        else
            return false;
    }

    private boolean validatePassword() {
        // defining our own password pattern
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{9,}" +                // at least 4 characters
                        "$");

        // if password field is empty
        // it will display error message "Field can not be empty"
        if (mPassword.isEmpty())
        {
            editTxt_password.setError("Field can not be empty");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(mPassword).matches())
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

    private boolean validateUsername() {
        // defining our own username pattern
        final Pattern USERNAME_PATTERN2 =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{5,}" +                // at least 4 characters
                        "$");

        if (mUsername.isEmpty())
        {
            editTxt_userName.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!USERNAME_PATTERN2.matcher(mUsername).matches())
        {
            editTxt_userName.setError("Please enter a valid username");
            return false;
        }
        else
        {
            editTxt_userName.setError(null);
            return true;
        }
    }

    private void initWidget() {
        editTxt_userName = findViewById(R.id.editTxt_userName);
        editTxt_email = findViewById(R.id.editTxt_email);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_confirmPassword = findViewById(R.id.editTxt_confirmPassword);

        btn_register = findViewById(R.id.btn_register);

        img_facebook = findViewById(R.id.img_facebook);
        img_google = findViewById(R.id.img_google);
        img_linkedin = findViewById(R.id.img_linkedin);

    }
}