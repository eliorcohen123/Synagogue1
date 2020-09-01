package com.eliorcohen1.synagogue.PagesPackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.OthersPackage.EmailPasswordPhoneValidator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditWorshipers extends AppCompatActivity implements View.OnClickListener {

    private EditText name, num_phone;
    private TextView textViewOK;
    private Button btnBack;
    private String get_name, get_numPhone;
    private FirebaseFirestore fireStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_worshipers);

        initUI();
        initListeners();
    }

    private void initUI() {
        name = findViewById(R.id.editTextName);  // ID of the name
        num_phone = findViewById(R.id.editTextPhone);  // ID of the name

        textViewOK = findViewById(R.id.textViewOK);

        btnBack = findViewById(R.id.btnBack);

        get_name = getIntent().getStringExtra("worshipers_name");
        get_numPhone = getIntent().getStringExtra("worshipers_numPhone");

        assert get_numPhone != null;
        get_numPhone = get_numPhone.replace("-", "");

        name.setText(get_name);
        num_phone.setText(get_numPhone);

        fireStoreDB = FirebaseFirestore.getInstance();
    }

    private void initListeners() {
        textViewOK.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewOK:
                String name1 = name.getText().toString();  // GetText of the name
                String numPhone1 = num_phone.getText().toString();  // GetText of the name
                StringBuilder s = null;
                try {
                    s = new StringBuilder(numPhone1);
                    s.insert(3, "-");
                } catch (Exception e) {

                }

                if (TextUtils.isEmpty(name1) && !EmailPasswordPhoneValidator.getInstance().isValidPhoneNumber(numPhone1)) {  // If the text are empty the textViewNumPhone will not be approved
                    name.setError("דרוש שם");  // Print text of error if the text is empty
                    num_phone.setError("דרוש מס' נייד חוקי");  // Print text of error if the text is empty
                    name.requestFocus();
                    num_phone.requestFocus();
                } else if (TextUtils.isEmpty(name1)) {  // If the text are empty the textViewNumPhone will not be approved
                    name.setError("דרוש שם");  // Print text of error if the text is empty
                    name.requestFocus();
                } else if (!EmailPasswordPhoneValidator.getInstance().isValidPhoneNumber(numPhone1)) {  // If the text are empty the textViewNumPhone will not be approved
                    num_phone.setError("דרוש מס' נייד חוקי");  // Print text of error if the text is empty
                    num_phone.requestFocus();
                } else {
                    try {
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name1);
                        assert s != null;
                        user.put("numPhone", s.toString());
                        fireStoreDB.collection("synagogue")
                                .document(s.toString())
                                .update(user)
                                .addOnSuccessListener(aVoid -> {
                                    Intent intentAddInternetToMain = new Intent(EditWorshipers.this, Worshipers.class);
                                    startActivity(intentAddInternetToMain);

                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(EditWorshipers.this, "שגיאה בעדכון משתשמש: " + e, Toast.LENGTH_SHORT).show());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Pass from AddWorshipers to Worshipers
                    Intent intentAddInternetToMain = new Intent(EditWorshipers.this, Worshipers.class);
                    startActivity(intentAddInternetToMain);

                    finish();
                }
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

}
