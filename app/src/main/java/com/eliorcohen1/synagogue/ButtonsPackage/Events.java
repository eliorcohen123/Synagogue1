package com.eliorcohen1.synagogue.ButtonsPackage;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;

public class Events extends AppCompatActivity {

    private Button backDonates;
    private TextView phone, textWant;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        backDonates = findViewById(R.id.backEvents);
        phone = findViewById(R.id.phone);
        textWant = findViewById(R.id.textWant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("בית הכנסת נווה צדק");

        textWant.setText("על מנת לקבוע אירוע/אזכרה יש ליצור קשר עם האחראי\n - \n שלום נסים");

        SpannableString str = new SpannableString("052-9426607");
        str.setSpan(new BackgroundColorSpan(Color.RED), 0, 11, 0);
        phone.setText(str);

        backDonates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(view);
            }
        });
    }

    public void openWhatsApp(View view) {
        try {
            String text = "היי שלום, ברצוני לקבוע אירוע/אזכרה באולם בית הכנסת, נא צור עימי קשר";// Replace with your message.
            String toNumber = "+9720529426607"; // Replace with mobile phone number without +Sign or leading zeros.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
