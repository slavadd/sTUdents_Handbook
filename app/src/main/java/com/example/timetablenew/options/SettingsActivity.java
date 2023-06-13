package com.example.timetablenew.options;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetablenew.R;
import com.example.timetablenew.fragments.SettingsFragment;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_SEVEN_DAYS_SETTING = "sevendays";
    public static final String KEY_CHANGE_LANGUAGE_SETTING = "changelanguage";
    public static final String KEY_SCHOOL_WEBSITE_SETTING = "schoolwebsite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public void setLanguage(Activity activity, String language) {
        Locale locale = new Locale(language);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }
}