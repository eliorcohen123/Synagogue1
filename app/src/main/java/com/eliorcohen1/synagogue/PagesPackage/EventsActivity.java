package com.eliorcohen1.synagogue.PagesPackage;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.CustomAdaptersPackage.CustomViewPagerAdapter;
import com.eliorcohen1.synagogue.ModelsPackage.TotalModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backEvents;
    private TextView phone, textWant;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<TotalModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        initUI();
        initListeners();
        getViewPager();
        tasksEvents();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backEvents = findViewById(R.id.backEvents);
        phone = findViewById(R.id.numPhone);
        textWant = findViewById(R.id.textWant);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
    }

    private void initListeners() {
        backEvents.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    private void tasksEvents() {
        textWant.setText("על מנת לקבוע אירוע/אזכרה יש ליצור קשר עם האחראי\n - \nשלום נסים");

        SpannableString str = new SpannableString("052-9426607");
        str.setSpan(new BackgroundColorSpan(Color.WHITE), 0, 11, 0);
        phone.setText(str);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backEvents:
                onBackPressed();
                break;
            case R.id.numPhone:
                openWhatsApp();
                break;
        }
    }

    private void openWhatsApp() {
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

    private ArrayList<TotalModel> populateList() {
        ArrayList<TotalModel> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TotalModel imageModel = new TotalModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }

    private void getViewPager() {
        mPager = findViewById(R.id.viewPager);
        mPager.setAdapter(new CustomViewPagerAdapter(EventsActivity.this, imageModelArrayList));

        CirclePageIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

}
