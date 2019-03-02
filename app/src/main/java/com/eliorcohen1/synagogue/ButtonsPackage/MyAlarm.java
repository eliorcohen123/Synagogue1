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
import com.eliorcohen1.synagogue.StartPackage.MyReceiver;

import java.util.Calendar;

public class MyAlarm extends AppCompatActivity {

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

        SharedPreferences prefs = getSharedPreferences("elior", MODE_PRIVATE);
        int idName = prefs.getInt("idName", 0);
        int idNum = prefs.getInt("idNum", 0);
        myText.setText(String.valueOf(idName + ":" + String.valueOf(idNum)));

        okAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myHourMy = Integer.parseInt(myHour.getText().toString());
                int myMinuteMy = Integer.parseInt(myMinute.getText().toString());

                SharedPreferences.Editor editor = getSharedPreferences("elior", MODE_PRIVATE).edit();
                editor.putInt("idName", myHourMy);
                editor.putInt("idNum", myMinuteMy);
                editor.apply();

                ComponentName receiver = new ComponentName(MyAlarm.this, MyReceiver.class);
                PackageManager pm = getPackageManager();

                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                Toast.makeText(MyAlarm.this, "השעה שבחרת לתזכורת היא: " + String.valueOf(myHourMy) + ":" + String.valueOf(myMinuteMy), Toast.LENGTH_SHORT).show();

                Intent alarmIntent = new Intent(MyAlarm.this, MyReceiver.class); // AlarmReceiver1 = broadcast receiver
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

                ComponentName receiver2 = new ComponentName(MyAlarm.this, MyReceiver.class);
                PackageManager pm2 = getPackageManager();

                pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                finish();
                startActivity(getIntent());
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName receiver = new ComponentName(MyAlarm.this, MyReceiver.class);
                PackageManager pm = getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                SharedPreferences.Editor editor = getSharedPreferences("elior", MODE_PRIVATE).edit();
                editor.putInt("idName", 0);
                editor.putInt("idNum", 0);
                editor.apply();

                Intent alarmIntent = new Intent(MyAlarm.this, MyReceiver.class); // AlarmReceiver1 = broadcast receiver
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

                ComponentName receiver2 = new ComponentName(MyAlarm.this, MyReceiver.class);
                PackageManager pm2 = getPackageManager();
                pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                Toast.makeText(MyAlarm.this, "ההתראה שלך בוטלה.", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
            }
        });

        backAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });
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

}
