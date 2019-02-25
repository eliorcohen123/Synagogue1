package com.eliorcohen1.synagogue.StartPackage;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.eliorcohen1.synagogue.ButtonsPackage.MyAlarm;
import com.eliorcohen1.synagogue.R;

import java.util.Calendar;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private static PendingIntent pendingIntentSnooze;
    private static AlarmManager alarmManagerSnooze;

    @Override
    public void onReceive(Context context, Intent intent) {

        final int NOTIFY_ID = 1; // ID of notification
        String id = "1"; // default_channel_id
        String title = "מתפלל"; // Default Channel
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyAlarm.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
            builder.setContentTitle("תפילה")
                    .setContentText("היי מתפלל יקר, הגיע הזמן לצאת לתפילה :)")  // required
                    .setSmallIcon(R.drawable.lamp)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("תפילה")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .addAction(R.drawable.lamp, context.getString(R.string.snooze), pendingIntentSnooze);
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyAlarm.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
            builder.setContentTitle("תפילה")
                    .setContentText("היי מתפלל יקר, הגיע הזמן לצאת לתפילה :)")  // required
                    .setSmallIcon(R.drawable.lamp)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("תפילה")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH)
                    .addAction(R.drawable.lamp, context.getString(R.string.snooze), pendingIntentSnooze);
        }
        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);

        Intent intentSnooze = new Intent(context, MyReceiver.class);
        pendingIntentSnooze = PendingIntent.getBroadcast(context, 1, intentSnooze, 0);
        alarmManagerSnooze = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1440);
        Date date = calendar.getTime();
        alarmManagerSnooze.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntentSnooze);
    }

}
