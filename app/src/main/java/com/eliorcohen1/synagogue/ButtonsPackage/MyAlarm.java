package com.eliorcohen1.synagogue.ButtonsPackage;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;
import com.eliorcohen1.synagogue.StartPackage.MyReceiverAlarm;

import java.util.Calendar;

public class MyAlarm extends AppCompatActivity implements View.OnClickListener {

    private PendingIntent pendingIntent;
    private EditText myHour, myMinute;
    private Button okAlarm, cancelAlarm, backAlarm;
    private AlarmManager alarmManager;
    private NotificationManager notificationManager;
    private TextView myText;

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

        myHour = findViewById(R.id.myHour);
        myMinute = findViewById(R.id.myMinute);
        okAlarm = findViewById(R.id.okAlarm);
        cancelAlarm = findViewById(R.id.cancelAlarm);
        backAlarm = findViewById(R.id.backAlarm);
        myText = findViewById(R.id.myText);

        myHour.setFilters(new InputFilter[]{new InputFilterMinMax("0", "24")});
        myMinute.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
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

    private class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {

            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okAlarm:
                if (myHour.getText().toString().isEmpty() && myMinute.getText().toString().isEmpty()) {
                    Toast.makeText(MyAlarm.this, "נא הקלד שעה ודקה.", Toast.LENGTH_LONG).show();
                } else if (myHour.getText().toString().isEmpty()) {
                    Toast.makeText(MyAlarm.this, "נא הקלד שעה.", Toast.LENGTH_LONG).show();
                } else if (myMinute.getText().toString().isEmpty()) {
                    Toast.makeText(MyAlarm.this, "נא הקלד דקה.", Toast.LENGTH_LONG).show();
                } else {
                    int myHourMy = Integer.parseInt(myHour.getText().toString());
                    int myMinuteMy = Integer.parseInt(myMinute.getText().toString());

                    SharedPreferences.Editor editor = getSharedPreferences("textTime", MODE_PRIVATE).edit();
                    editor.putInt("idHour", myHourMy);
                    editor.putInt("idMinute", myMinuteMy);
                    editor.apply();

                    if (myHourMy <= 9 && myMinuteMy <= 9) {
                        Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf("0" + myHourMy) + ":" + String.valueOf("0" + myMinuteMy), Toast.LENGTH_SHORT).show();
                    } else if (myHourMy <= 9 && myMinuteMy > 9) {
                        Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf("0" + myHourMy) + ":" + String.valueOf(myMinuteMy), Toast.LENGTH_SHORT).show();
                    } else if (myHourMy > 9 && myMinuteMy > 9) {
                        Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf(myHourMy) + ":" + String.valueOf(myMinuteMy), Toast.LENGTH_SHORT).show();
                    } else if (myHourMy > 9 && myMinuteMy <= 9) {
                        Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf(myHourMy) + ":" + String.valueOf("" + myMinuteMy), Toast.LENGTH_SHORT).show();
                    }

                    Intent alarmIntent = new Intent(MyAlarm.this, MyReceiverAlarm.class); // AlarmReceiver1 = broadcast receiver
                    pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

                    Calendar alarmStartTime = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, myHourMy);
                    alarmStartTime.set(Calendar.MINUTE, myMinuteMy);
                    alarmStartTime.set(Calendar.SECOND, 0);
                    if (now.after(alarmStartTime)) {
                        alarmStartTime.add(Calendar.DATE, 1);
                    }
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    ComponentName receiver = new ComponentName(MyAlarm.this, MyReceiverAlarm.class);
                    PackageManager pm = getPackageManager();

                    pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                    finish();
                    startActivity(getIntent());
                }
                break;
            case R.id.cancelAlarm:
                ComponentName receiver = new ComponentName(MyAlarm.this, MyReceiverAlarm.class);
                PackageManager pm = getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                SharedPreferences.Editor editor = getSharedPreferences("textTime", MODE_PRIVATE).edit();
                editor.putInt("idHour", 0);
                editor.putInt("idMinute", 0);
                editor.apply();

                Intent alarmIntent = new Intent(MyAlarm.this, MyReceiverAlarm.class); // AlarmReceiver1 = broadcast receiver
                pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

                Calendar alarmStartTime = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                alarmStartTime.set(Calendar.HOUR_OF_DAY, 900000);
                alarmStartTime.set(Calendar.MINUTE, 900000);
                alarmStartTime.set(Calendar.SECOND, 900000);
                if (now.after(alarmStartTime)) {
                    alarmStartTime.add(Calendar.DATE, 1);
                }
                alarmManager.cancel(pendingIntent);

                if (notificationManager != null) {
                    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    String id = "1";
                    notificationManager.deleteNotificationChannel(id);
                }

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
