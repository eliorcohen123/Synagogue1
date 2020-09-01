package com.eliorcohen1.synagogue.PagesPackage;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.OthersPackage.MyReceiverAlarm;

import java.util.Calendar;

public class MyAlarm extends AppCompatActivity implements View.OnClickListener {

    private PendingIntent pendingIntent;
    private Button okAlarm, cancelAlarm, backAlarm;
    private AlarmManager alarmManager;
    private NotificationManager notificationManager;
    private TimePicker timePicker1;
    private Calendar alarmStartTime;
    private TextView myText;
    private SharedPreferences.Editor editor;
    private ComponentName receiver;
    private PackageManager pm;
    private Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initUI();
        initListeners();
        tasks();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        okAlarm = findViewById(R.id.okAlarm);
        cancelAlarm = findViewById(R.id.cancelAlarm);
        backAlarm = findViewById(R.id.backAlarm);
        myText = findViewById(R.id.myText);
        timePicker1 = findViewById(R.id.timePicker1);

        timePicker1.setIs24HourView(true);

        editor = getSharedPreferences("textTime", MODE_PRIVATE).edit();
    }

    private void initListeners() {
        okAlarm.setOnClickListener(this);
        cancelAlarm.setOnClickListener(this);
        backAlarm.setOnClickListener(this);
    }

    private void tasks() {
        SharedPreferences prefs = getSharedPreferences("textTime", MODE_PRIVATE);
        int idHour = prefs.getInt("idHour", 0);
        int idMinute = prefs.getInt("idMinute", 0);

        if (idHour <= 9 && idMinute <= 9) {
            myText.setText(String.valueOf("0" + idHour + ":" + String.valueOf("0" + idMinute)));
        } else if (idHour <= 9 && idMinute > 9) {
            myText.setText(String.valueOf("0" + idHour + ":" + String.valueOf(idMinute)));
        } else if (idHour > 9 && idMinute > 9) {
            myText.setText(String.valueOf(idHour + ":" + String.valueOf(idMinute)));
        } else if (idHour > 9 && idMinute <= 9) {
            myText.setText(String.valueOf(idHour + ":" + String.valueOf("0" + idMinute)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okAlarm:
                int myHourMy = timePicker1.getCurrentHour();
                int myMinuteMy = timePicker1.getCurrentMinute();

                editor.putInt("idHour", myHourMy).putInt("idMinute", myMinuteMy).apply();

                if (myHourMy <= 9 && myMinuteMy <= 9) {
                    Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf("0" + myHourMy) + ":" + String.valueOf("0" + myMinuteMy), Toast.LENGTH_SHORT).show();
                } else if (myHourMy <= 9 && myMinuteMy > 9) {
                    Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf("0" + myHourMy) + ":" + String.valueOf(myMinuteMy), Toast.LENGTH_SHORT).show();
                } else if (myHourMy > 9 && myMinuteMy > 9) {
                    Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf(myHourMy) + ":" + String.valueOf(myMinuteMy), Toast.LENGTH_SHORT).show();
                } else if (myHourMy > 9 && myMinuteMy <= 9) {
                    Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf(myHourMy) + ":" + String.valueOf("0" + myMinuteMy), Toast.LENGTH_SHORT).show();
                }

                alarmIntent = new Intent(MyAlarm.this, MyReceiverAlarm.class); // AlarmReceiver1 = broadcast receiver
                pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

                alarmStartTime = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                alarmStartTime.set(Calendar.HOUR_OF_DAY, myHourMy);
                alarmStartTime.set(Calendar.MINUTE, myMinuteMy);
                alarmStartTime.set(Calendar.SECOND, 0);
                if (now.after(alarmStartTime)) {
                    alarmStartTime.add(Calendar.DATE, 1);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                receiver = new ComponentName(MyAlarm.this, MyReceiverAlarm.class);
                pm = getPackageManager();

                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                finish();
                startActivity(getIntent());
                break;
            case R.id.cancelAlarm:
                editor.putInt("idHour", 0).putInt("idMinute", 0).apply();

                alarmIntent = new Intent(MyAlarm.this, MyReceiverAlarm.class);
                pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                assert alarmManager != null;
                alarmManager.cancel(pendingIntent);

                if (notificationManager != null) {
                    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    String id = "1";
                    assert notificationManager != null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationManager.deleteNotificationChannel(id);
                    }
                }

                receiver = new ComponentName(MyAlarm.this, MyReceiverAlarm.class);
                pm = getPackageManager();

                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                Toast.makeText(MyAlarm.this, "ההתראה שלך בוטלה.", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
                break;
            case R.id.backAlarm:
                Intent intent = new Intent(MyAlarm.this, MainActivity.class);
                startActivity(intent);
        }
    }

}
