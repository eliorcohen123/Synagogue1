package com.eliorcohen1.synagogue.StartPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.eliorcohen1.synagogue.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignPhoneActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_phone);

        initUI();
        doTasks();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void initUI() {
        editText = findViewById(R.id.editTextPhone);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
    }

    private void doTasks() {
        findViewById(R.id.buttonContinue).setOnClickListener(v -> {
            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String number = editText.getText().toString().trim();
            if (number.isEmpty() || number.length() < 10) {
                editText.setError("Valid number is required");
                editText.requestFocus();
                return;
            }
            String phoneNumber = "+" + code + number;
            Intent intent = new Intent(SignPhoneActivity.this, VerifyPhoneActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        });
    }

}
