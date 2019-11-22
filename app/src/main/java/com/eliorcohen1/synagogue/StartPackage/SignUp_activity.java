package com.eliorcohen1.synagogue.StartPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_id, passwordCheck;
    private FirebaseAuth mAuth;
    private static final String TAG = "";
    private ProgressBar progressBar;
    private Button myBtnSignUp;
    private TextView btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnSignUp = findViewById(R.id.login_page);
        email_id = findViewById(R.id.input_email);
        progressBar = findViewById(R.id.progressBar);
        passwordCheck = findViewById(R.id.input_password);
        myBtnSignUp = findViewById(R.id.btn_signup);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initListeners() {
        myBtnSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_page:
                Intent intent = new Intent(SignUp_activity.this, SignIn_activity.class);
                startActivity(intent);
                break;
            case R.id.btn_signup:
                String email = email_id.getText().toString();
                String password = passwordCheck.getText().toString();

                if (!EmailAndPasswordValidator.isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "האימייל לא חוקי", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!EmailAndPasswordValidator.isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(), "הסיסמא לא חוקית", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp_activity.this, task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent1 = new Intent(SignUp_activity.this, SignIn_activity.class);
                                startActivity(intent1);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp_activity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

}
