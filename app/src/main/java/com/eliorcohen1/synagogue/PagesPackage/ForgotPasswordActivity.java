package com.eliorcohen1.synagogue.PagesPackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.OthersPackage.EmailPasswordPhoneValidator;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "forgotSuccessful";
    private EditText editTextForgot;
    private Button btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUI();
        initListeners();
    }

    private void initUI() {
        editTextForgot = findViewById(R.id.editTextForgot);
        btnForgot = findViewById(R.id.btnForgot);
    }

    private void initListeners() {
        btnForgot.setOnClickListener(this);
    }

    private void forgotPassword() {
        String myEmailForgot = editTextForgot.getText().toString();
        if (!EmailPasswordPhoneValidator.getInstance().isValidEmail(myEmailForgot)) {
            Toast.makeText(this, "האימייל לא חוקי", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(myEmailForgot)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "הסיסמא נשלחה");
                            Toast.makeText(this, "הסיסמה נשלחה", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPasswordActivity.this, SignInActivity.class));
                        } else {
                            Toast.makeText(this, "המייל לא קיים", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnForgot:
                forgotPassword();
                break;
        }
    }

}
