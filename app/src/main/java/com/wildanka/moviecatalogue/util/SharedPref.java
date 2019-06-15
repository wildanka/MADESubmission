package com.wildanka.moviecatalogue.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String TAG = "SharedPref";
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor editor;

    public SharedPref(Context mContext) {
        mSharedPref = mContext.getSharedPreferences("filename", Context.MODE_PRIVATE);
        editor = mSharedPref.edit();
    }

    public void setLanguage(String language) {
        editor.putString("language", language);
        editor.apply();
    }
    public String loadLanguage() {
        return mSharedPref.getString("language", "en-US");
    }

    public void setDailyReminder(boolean dailyReminderState) {
        editor.putBoolean("dailyReminderState", dailyReminderState);
        editor.apply();
    }

    public boolean loadDailyReminderState() {
        return mSharedPref.getBoolean("dailyReminderState", false);
    }

    public void setReleaseReminder(boolean dailyReminderState) {
        editor.putBoolean("releaseReminderState", dailyReminderState);
        editor.apply();
    }

    public boolean loadReleaseReminderState() {
        return mSharedPref.getBoolean("releaseReminderState", false);
    }


}
