package com.eliorcohen1.synagogue.StartPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;

public class MyCredits extends AppCompatActivity {

    private TextView textMe;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        initUI();
        showUI();
    }

    private void initUI() {
        textMe = findViewById(R.id.textView25);
        btnBack = findViewById(R.id.button21);
    }

    private void showUI() {
        textMe.setText("כל הזכויות שמורות למפתח האפליקציה \n - \n אליאור כהן \n\n טלפון: \n 050-3332696 \n\n אימייל: \n eliorjobcohen@gmail.com");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
