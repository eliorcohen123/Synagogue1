package com.eliorcohen1.synagogue.ButtonsPackage;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;

public class Donates extends AppCompatActivity {

    private Button backDonates;
    private TextView phone, textWant;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donates);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backDonates = findViewById(R.id.backDonates);
        phone = findViewById(R.id.phone);
        textWant = findViewById(R.id.textWant);

        SpannableString str = new SpannableString("054-4917147");
        str.setSpan(new BackgroundColorSpan(Color.WHITE), 0, 11, 0);
        phone.setText(str);

        textWant.setText("לאחר ההעברה נא לעדכן בהודעה את הגזבר\n - \nמוטי כהן");

        backDonates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donates.this, MainActivity.class);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(view);
            }
        });
    }

    private void openWhatsApp(View view) {
        try {
            String text = "היי מוטי, ברצוני לדווח על העברה בנקאית עבור נדר/תרומה על סך *** שקלים לכבוד בית הכנסת";// Replace with your message.
            String toNumber = "+9720544917147"; // Replace with mobile phone number without +Sign or leading zeros.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
