package com.eliorcohen1.synagogue.ClassesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.OthersPackage.CountryData;
import com.eliorcohen1.synagogue.OthersPackage.EmailPasswordPhoneValidator;
import com.google.firebase.auth.FirebaseAuth;

public class SignPhoneActivity extends AppCompatActivity {

    private Spinner spinnerCountry;
    private EditText editTextNumPhone;

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
        editTextNumPhone = findViewById(R.id.editTextPhone);

        spinnerCountry = findViewById(R.id.spinnerCountries);
        spinnerCountry.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
    }

    private void doTasks() {
        findViewById(R.id.buttonContinue).setOnClickListener(v -> {
            String code = CountryData.countryAreaCodes[spinnerCountry.getSelectedItemPosition()];
            String number = editTextNumPhone.getText().toString().trim();
            if (!EmailPasswordPhoneValidator.getInstance().isValidPhoneNumber(number)) {
                editTextNumPhone.setError("דרוש מס' נייד חוקי");
                editTextNumPhone.requestFocus();
            } else {
                String phoneNumber = "+" + code + number;
                Intent intent = new Intent(SignPhoneActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });
    }

}
