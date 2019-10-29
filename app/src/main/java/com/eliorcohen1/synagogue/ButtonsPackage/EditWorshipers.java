package com.eliorcohen1.synagogue.ButtonsPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.TotalModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditWorshipers extends AppCompatActivity implements View.OnClickListener {

    private EditText name, num_phone;
    private TextView textViewOK;
    private Button btnBack;
    private String get_name, get_numPhone;
    private Firebase firebase;

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

        Firebase.setAndroidContext(this);
        firebase = new Firebase(getString(R.string.API_KEY_Firebase));

        get_name = getIntent().getStringExtra("worshipers_name");
        get_numPhone = getIntent().getStringExtra("worshipers_numPhone");

        name.setText(get_name);
        num_phone.setText(get_numPhone);
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

                if (TextUtils.isEmpty(name1) && TextUtils.isEmpty(numPhone1)) {  // If the text are empty the textViewNumPhone will not be approved
                    name.setError("דרוש שם");  // Print text of error if the text is empty
                    num_phone.setError("דרוש מס' נייד");  // Print text of error if the text is empty
                } else if (TextUtils.isEmpty(name1)) {  // If the text are empty the textViewNumPhone will not be approved
                    name.setError("דרוש שם");  // Print text of error if the text is empty
                } else if (TextUtils.isEmpty(numPhone1)) {  // If the text are empty the textViewNumPhone will not be approved
                    num_phone.setError("דרוש מס' נייד");  // Print text of error if the text is empty
                } else {
                    try {
                        firebase.getRef().child(get_numPhone).removeValue();

                        firebase.child(numPhone1).child("name").setValue(name1);
                        firebase.child(numPhone1).child("numPhone").setValue(numPhone1);
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