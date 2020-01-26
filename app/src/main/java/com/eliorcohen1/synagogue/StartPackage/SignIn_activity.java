package com.eliorcohen1.synagogue.StartPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class SignIn_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Check";
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CallbackManager mCallbackManager;
    private SignInButton btnSignInGoogle;
    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnLogin, btnPhone;
    private TextView btnSignUp, btnForgotPassword;
    private LoginButton loginButtonFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        //check the current user
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignIn_activity.this, WelcomeActivity.class));
            finish();
        }

        setContentView(R.layout.activity_signin_activity);

        initUI();
        initListeners();
        btnLoginGoogle();
        btnLoginFacebook();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void initUI() {
        loginButtonFacebook = findViewById(R.id.login_button_facebook);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        btnSignUp = findViewById(R.id.sign_up_button);
        btnSignInGoogle = findViewById(R.id.sign_in_google);
        btnPhone = findViewById(R.id.btnPhone);
        btnForgotPassword = findViewById(R.id.btn_forgot);

        mAuthListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(SignIn_activity.this, WelcomeActivity.class));
                finish();
            }
        };
    }

    private void initListeners() {
        btnSignInGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    private void btnLoginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleSignInKey))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        progressBar.setVisibility(View.GONE);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(SignIn_activity.this, "ההתחברות דרך גוגל נכשלה", Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(SignIn_activity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }

    private void btnLoginFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mCallbackManager = CallbackManager.Factory.create();
        loginButtonFacebook.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButtonFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "facebook:onSuccess: " + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "facebook:onCancel: ");
                // [START_EXCLUDE]
//                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "facebook:onError: ", error);
                // [START_EXCLUDE]
//                updateUI(null);
                // [END_EXCLUDE]
            }
        });
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        try {
            Log.i(TAG, "handleFacebookAccessToken: " + token.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i(TAG, "signInWithCredential:success: ");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.i(TAG, "signInWithCredential:failure: ", task.getException());
                        Toast.makeText(SignIn_activity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_google:
                progressBar.setVisibility(View.VISIBLE);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.btn_login:
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if (!EmailPasswordPhoneValidator.getInstance().isValidEmail(email) && !EmailPasswordPhoneValidator.getInstance().isValidPassword(password)) {
                    inputEmail.setError("האימייל לא חוקי");
                    inputPassword.setError("הסיסמא לא חוקית");
                    inputEmail.requestFocus();
                    inputPassword.requestFocus();
                    return;
                } else if (!EmailPasswordPhoneValidator.getInstance().isValidEmail(email)) {
                    inputEmail.setError("האימייל לא חוקי");
                    inputEmail.requestFocus();
                    return;
                } else if (!EmailPasswordPhoneValidator.getInstance().isValidPassword(password)) {
                    inputPassword.setError("הסיסמא לא חוקית");
                    inputPassword.requestFocus();
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignIn_activity.this, task -> {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // there was an error
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(SignIn_activity.this, WelcomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d(TAG, "singInWithEmail:Fail");
                                    Toast.makeText(SignIn_activity.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                break;
            case R.id.sign_up_button:
                startActivity(new Intent(SignIn_activity.this, SignUp_activity.class));
                break;
            case R.id.btnPhone:
                startActivity(new Intent(SignIn_activity.this, SignPhoneActivity.class));
                break;
            case R.id.btn_forgot:
                startActivity(new Intent(SignIn_activity.this, ForgotPasswordActivity.class));
                break;
        }
    }

}
