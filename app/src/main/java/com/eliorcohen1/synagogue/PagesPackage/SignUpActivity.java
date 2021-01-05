package com.eliorcohen1.synagogue.PagesPackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.OthersPackage.EmailPasswordPhoneValidator;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

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
        passwordCheck = findViewById(R.id.input_password);
        myBtnSignUp = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initListeners() {
        myBtnSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void register() {
        String email = email_id.getText().toString();
        String password = passwordCheck.getText().toString();
        if (!EmailPasswordPhoneValidator.getInstance().isValidEmail(email) && !EmailPasswordPhoneValidator.getInstance().isValidPassword(password)) {
            email_id.setError("האימייל לא חוקי");
            passwordCheck.setError("הסיסמא לא חוקית");
            email_id.requestFocus();
            passwordCheck.requestFocus();
        } else if (!EmailPasswordPhoneValidator.getInstance().isValidEmail(email)) {
            email_id.setError("האימייל לא חוקי");
            email_id.requestFocus();
        } else if (!EmailPasswordPhoneValidator.getInstance().isValidPassword(password)) {
            passwordCheck.setError("הסיסמא לא חוקית");
            passwordCheck.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Intent intent1 = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_page:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_signup:
                register();
                break;
        }
    }

}
