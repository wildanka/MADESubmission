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

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "ReminderActivity";
    public static final String alarmTimeDaily = "13:55";
    public static final String alarmTimeRelease = "13:55";
    private AlarmReceiver alarmReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Switch swDailyReminder = findViewById(R.id.sw_daily_reminder);
        Switch swReleaseReminder = findViewById(R.id.sw_release_reminder);

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
                Log.e(TAG, "daily reminder state changed to " + isChecked);
                if (isChecked) { //on
                    alarmReceiver.setDailyRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY_REMINDER, alarmTimeDaily, "Check New TV Shows & Movies today! (Daily Reminder)");
                } else {
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY_REMINDER);
                }
                break;
            case R.id.sw_release_reminder:
                Log.e(TAG, "release reminder state changed to " + isChecked);
                if (isChecked) { //on
                    alarmReceiver.setReleaseRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE_REMINDER, alarmTimeDaily, "Check New Movies Releases today! (Release Reminder)");
                } else {
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_RELEASE_REMINDER);
                }
                break;
        }
    }
}
