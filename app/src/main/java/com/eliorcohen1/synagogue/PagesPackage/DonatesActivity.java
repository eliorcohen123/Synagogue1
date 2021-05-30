package com.eliorcohen1.synagogue.PagesPackage;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;

public class DonatesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backDonates;
    private TextView phone, textWant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donates);

        initUI();
        initListeners();
        tasks();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backDonates = findViewById(R.id.backDonates);
        phone = findViewById(R.id.numPhone);
        textWant = findViewById(R.id.textWant);
    }

    private void initListeners() {
        backDonates.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    private void tasks() {
        SpannableString str = new SpannableString("052-9426607");
        str.setSpan(new BackgroundColorSpan(Color.WHITE), 0, 11, 0);
        phone.setText(str);

        textWant.setText("לאחר ההעברה נא לעדכן בהודעה את הגזבר\n - \nשלום ניסים");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backDonates:
                onBackPressed();
                break;
            case R.id.numPhone:
                openWhatsApp();
                break;
        }
    }

    private void openWhatsApp() {
        try {
            String text = "היי שלום, ברצוני לדווח על העברה בנקאית עבור נדר/תרומה על סך *** שקלים לכבוד בית הכנסת";
            String toNumber = "+9720529426607";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
