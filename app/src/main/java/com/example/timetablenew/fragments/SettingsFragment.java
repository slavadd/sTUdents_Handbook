package com.example.timetablenew.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.timetablenew.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}