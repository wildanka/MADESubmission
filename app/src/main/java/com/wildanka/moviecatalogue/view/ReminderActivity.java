package com.wildanka.moviecatalogue.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.util.AlarmReceiver;
import com.wildanka.moviecatalogue.util.SharedPref;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "ReminderActivity";
    public static final String alarmTimeDaily = "07:00";
    public static final String alarmTimeRelease = "08:00";
    private AlarmReceiver alarmReceiver;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Switch swDailyReminder = findViewById(R.id.sw_daily_reminder);
        Switch swReleaseReminder = findViewById(R.id.sw_release_reminder);

        if (sharedPref.loadDailyReminderState()) swDailyReminder.setChecked(true);
        if (sharedPref.loadReleaseReminderState()) swReleaseReminder.setChecked(true);

        swDailyReminder.setOnCheckedChangeListener(this);
        swReleaseReminder.setOnCheckedChangeListener(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_daily_reminder:
                Log.e(TAG, "daily reminder state changed to " + isChecked + " for alarm at : " + alarmTimeDaily);
                if (isChecked) { //on
                    sharedPref.setDailyReminder(isChecked);
                    alarmReceiver.setDailyRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY_REMINDER, alarmTimeDaily, "Check New TV Shows & Movies today! (Daily Reminder)");
                } else {
                    sharedPref.setDailyReminder(isChecked);
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY_REMINDER);
                }
                break;
            case R.id.sw_release_reminder:
                Log.e(TAG, "release reminder state changed to " + isChecked + " for alarm at : " + alarmTimeRelease);
                if (isChecked) { //on
                    sharedPref.setReleaseReminder(isChecked);
                    alarmReceiver.setReleaseRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE_REMINDER, alarmTimeRelease, "Check New Movies Releases today! (Release Reminder)");
                } else {
                    sharedPref.setReleaseReminder(isChecked);
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE_REMINDER);
                }
                break;
        }
    }
}
