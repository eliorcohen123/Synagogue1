package com.eliorcohen1.synagogue.ButtonsPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;

public class WinterClock extends AppCompatActivity {

    private TextView shabat, winter, morning1, noon1, evening1, morning2, noon2, evening2, clock, noon3, evening3, formula, simpleDay, sunset, sunsetText;
    private Button backWinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backWinter = findViewById(R.id.backWinter);

        clock = findViewById(R.id.clock);
        clock.setText("שעות תפילה נווה צדק");

        formula = findViewById(R.id.formula);
        formula.setText(" נוסח: ספרדי ");

        winter = findViewById(R.id.winter);
        winter.setText(" שעון חורף ");

        sunsetText = findViewById(R.id.sunsetText);
        sunsetText.setText("לינק לבדיקת שעת השקיעה ועוד...");

        sunset = findViewById(R.id.sunset);
        sunset.setMovementMethod(LinkMovementMethod.getInstance());

        simpleDay = findViewById(R.id.simpleDay);
        simpleDay.setText(" יום חול ");

        morning1 = findViewById(R.id.morning1);
        morning1.setText("תפילת שחרית(ספר תורה): " + "\n (06:15)06:30" + "\n שישי: " + "\n 07:00");

        noon1 = findViewById(R.id.noon1);
        noon1.setText("תפילת מנחה: " + "\n 20 דקות לפני השקיעה ");

        evening1 = findViewById(R.id.evening1);
        evening1.setText("תפילת ערבית: " + "\n 25 דקות אחרי השקיעה ");

        shabat = findViewById(R.id.shabat);
        shabat.setText(" שבת קודש ");

        evening2 = findViewById(R.id.evening2);
        evening2.setText(" מנחה וערבית של שבת: " + "\n 5 דקות לפני כניסת השבת ");

        morning2 = findViewById(R.id.morning2);
        morning2.setText(" שחרית של שבת: " + "\n 08:00 ");

        noon2 = findViewById(R.id.noon2);
        noon2.setText(" מנחה גדולה: " + "\n 12:30 ");

        noon3 = findViewById(R.id.noon3);
        noon3.setText(" מנחה קטנה: " + "\n שעה וחצי לפני ערבית של מוצ''ש ");

        evening3 = findViewById(R.id.evening3);
        evening3.setText(" ערבית של מוצ''ש: " + "\n 6 דקות לפני צאת השבת ");


        backWinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WinterClock.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
