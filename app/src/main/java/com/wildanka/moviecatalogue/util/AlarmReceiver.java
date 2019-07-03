package com.wildanka.moviecatalogue.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.wildanka.moviecatalogue.MainActivity;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.ReleaseTodayData;
import com.wildanka.moviecatalogue.model.entity.ReleaseTodayResult;
import com.wildanka.moviecatalogue.model.network.ApiMovies;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wildanka.moviecatalogue.BuildConfig.API_V3_KEY;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY_REMINDER = "DailyAlarm";
    public static final String TYPE_RELEASE_REMINDER = "ReleaseAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private static final String TAG = "AlarmReceiver";

    // Siapkan 2 id untuk 2 macam alarm, onetime dna repeating
    private final int ID_DAILY_REMINDER = 100;
    private final int ID_RELEASE_REMINDER = 101;

    private String TIME_FORMAT = "HH:mm";

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        final String message = intent.getStringExtra(EXTRA_MESSAGE);

        final String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? TYPE_DAILY_REMINDER : TYPE_RELEASE_REMINDER;
        final int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;

        SharedPref sp = new SharedPref(context);
        if (sp.loadReleaseReminderState() && notifId == ID_RELEASE_REMINDER) {
            //fetch data from API if release reminder is enabled
            ApiMovies mApi = ApiClient.getClient().create(ApiMovies.class);
            String todayDate = "2019-07-03";
            Call<ReleaseTodayData> call = mApi.getTodayRelease(API_V3_KEY,todayDate,todayDate,sp.loadLanguage());

            call.enqueue(new Callback<ReleaseTodayData>() {
                @Override
                public void onResponse(Call<ReleaseTodayData> call, Response<ReleaseTodayData> response) {
                    final MutableLiveData<List<ReleaseTodayResult>> releaseTodayList = new MutableLiveData<>();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            releaseTodayList.setValue(response.body().getReleaseTodayResults());
                            Log.e(TAG, "onResponse: " + releaseTodayList.getValue().get(0).getTitle());
                            showToast(context, title, "Released Movie Today is : " + releaseTodayList.getValue().get(0).getTitle());
                            showAlarmNotification(context, title, "Released Movie Today is : " + releaseTodayList.getValue().get(0).getTitle(), notifId);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReleaseTodayData> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "onFailure: "+t.getMessage());
                }
            });
        }
        if (sp.loadDailyReminderState() && notifId == ID_DAILY_REMINDER) {
            //Jika Anda ingin menampilkan dengan Notif anda bisa menghilangkan komentar pada baris dibawah ini.
            showToast(context, title, message);
            showAlarmNotification(context, title, message, notifId);
        }
    }

    //set the onetime alarm
    public void setReleaseRepeatingAlarm(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Release Reminder alarm set up", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "setReleaseReminder: is called" );
    }

    //set the onetime alarm
    public void setDailyRepeatingAlarm(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Daily Reminder alarm set up", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "setDailyReminder: is called" );
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void cancelAlarm(Context context, String type) {
        Log.e(TAG, "cancelAlarm: is called" );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER: ID_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;
        Toast.makeText(context, type+" cancelled, ID : "+notifId, Toast.LENGTH_SHORT).show();
    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId){
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager Channel";

        //1. create the notification manager
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //2. create the notification COMPAT builder
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, ID_DAILY_REMINDER, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        if (notifId==ID_DAILY_REMINDER){
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
        }else{
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
        }

        //3. if user OS version is above O, we need to create a notification channel first
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        //4. show the notification to user
        Notification notification = builder.build();
        if (notificationManagerCompat != null){
            notificationManagerCompat.notify(notifId, notification);
        }
    }

}