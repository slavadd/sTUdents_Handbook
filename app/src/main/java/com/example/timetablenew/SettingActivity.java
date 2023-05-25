package com.example.timetablenew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public class SettingActivity extends AppCompatActivity {

    Button bgLanguage;
    Button enLanguage;
    Button enableNotifications;

    Button darkMode;

    ProgressBar progressBar;
    boolean isNightModeOn;
    boolean isLanguageBg;
    String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);

        darkMode = findViewById(R.id.btnDarkMode);
        bgLanguage = findViewById(R.id.btnLanguageBg);
        enLanguage = findViewById(R.id.btnLanguageEn);
        enableNotifications = findViewById(R.id.btnTurnNotifications);
        progressBar = findViewById(R.id.pbSwitchMode);

        bgLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLanguageBg = true;
                bgLanguage.setText(R.string.language_set_to_bulgarian);
                enLanguage.setText(R.string.english);
                setLanguage("bg");
                startActivity(new Intent(SettingActivity.this, MainMenuActivity.class));

            }
        });

        enLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLanguageBg = false;
                bgLanguage.setText(R.string.language_set_to_english);
                bgLanguage.setText(R.string.bulgarian);
                setLanguage("en");
                startActivity(new Intent(SettingActivity.this, MainMenuActivity.class));
            }
        });

        enableNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarmBroadcastReceiver(getApplicationContext());
                enableNotifications.setText("Enabled Notifications");
            }
        });


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false;
            darkMode.setText(R.string.enable_dark_mode);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightModeOn = true;
            darkMode.setText(R.string.disable_dark_mode);
        } else {
            Log.i("SettingActivity", "Mode is automatically switched");
        }

        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkMode.setText(R.string.enable_dark_mode);
                    isNightModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkMode.setText(R.string.disable_dark_mode);
                    isNightModeOn = true;
                }
            }
        });

    }

    public void setLanguage(String languageCode) {
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void startAlarmBroadcastReceiver(Context context) {
        Intent _intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 41);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}