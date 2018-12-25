package com.eliorcohen1.synagogue.StartPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;

public class MyCredits extends AppCompatActivity {

    private TextView textMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        textMe = findViewById(R.id.textView25);
        textMe.setText("כל הזכויות שמורות למפתח האפליקציה \n - \n אליאור כהן \n\n טלפון: \n 050-3332696 \n\n אימייל: \n elior123456123123@gmail.com");

        // Button are back to the previous activity
        Button button1 = findViewById(R.id.button21);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
