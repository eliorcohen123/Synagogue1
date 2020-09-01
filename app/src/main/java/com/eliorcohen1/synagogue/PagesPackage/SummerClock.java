package com.eliorcohen1.synagogue.PagesPackage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;

public class SummerClock extends AppCompatActivity implements View.OnClickListener {

    private TextView shabat, summer, morning1, noon1, evening1, morning2, noon2, evening2, clock, noon3, evening3, formula, simpleDay, sunset, sunsetText;
    private Button backSummer, backSummerWeb;
    private LinearLayout myLinear;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summer);

        initUI();
        initListeners();
        showUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backSummer = findViewById(R.id.backSummer);
        backSummerWeb = findViewById(R.id.backSummerWeb);
        clock = findViewById(R.id.clock);
        formula = findViewById(R.id.formula);
        summer = findViewById(R.id.summer);
        sunsetText = findViewById(R.id.sunsetText);
        sunset = findViewById(R.id.sunset);
        simpleDay = findViewById(R.id.simpleDay);
        morning1 = findViewById(R.id.morning1);
        noon1 = findViewById(R.id.noon1);
        evening1 = findViewById(R.id.evening1);
        shabat = findViewById(R.id.shabat);
        evening2 = findViewById(R.id.evening2);
        morning2 = findViewById(R.id.morning2);
        noon2 = findViewById(R.id.noon2);
        noon3 = findViewById(R.id.noon3);
        evening3 = findViewById(R.id.evening3);
        myLinear = findViewById(R.id.myLinear);
        webView = findViewById(R.id.myWebView);

        webView.setVisibility(View.GONE);
        backSummerWeb.setVisibility(View.GONE);
    }

    private void initListeners() {
        backSummer.setOnClickListener(this);
        backSummerWeb.setOnClickListener(this);
        sunset.setOnClickListener(this);
    }

    private void showUI() {
        clock.setText("שעות תפילה נווה צדק");
        formula.setText(" נוסח: ספרדי ");
        summer.setText(" שעון קיץ ");
        sunsetText.setText("לינק לבדיקת שעת השקיעה ועוד...");
        simpleDay.setText(" יום חול ");
        morning1.setText("תפילת שחרית(ספר תורה): " + "\n (06:15)06:30" + "\n שישי: " + "\n 07:00");
        noon1.setText("תפילת מנחה: " + "\n 20 דקות לפני השקיעה ");
        evening1.setText("תפילת ערבית: " + "\n 25 דקות אחרי השקיעה ");
        shabat.setText(" שבת קודש ");
        evening2.setText(" מנחה וערבית של שבת: " + "\n 5 דקות לפני כניסת השבת ");
        morning2.setText(" שחרית של שבת: " + "\n 08:00 ");
        noon2.setText(" מנחה גדולה: " + "\n 13:15 ");
        noon3.setText(" מנחה קטנה: " + "\n שעה וחצי לפני ערבית של מוצ''ש ");
        evening3.setText(" ערבית של מוצ''ש: " + "\n 6 דקות לפני צאת השבת ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backSummer:
                onBackPressed();
                break;
            case R.id.backSummerWeb:
                myLinear.setVisibility(View.VISIBLE);
                backSummer.setVisibility(View.VISIBLE);
                backSummerWeb.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                break;
            case R.id.sunset:
                myLinear.setVisibility(View.GONE);
                backSummerWeb.setVisibility(View.VISIBLE);
                backSummer.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("https://www.kipa.co.il/%D7%96%D7%9E%D7%A0%D7%99-%D7%94%D7%99%D7%95%D7%9D/");
                break;
        }
    }

}
