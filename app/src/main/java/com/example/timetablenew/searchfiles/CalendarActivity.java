package com.example.timetablenew.searchfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.timetablenew.R;
import com.example.timetablenew.adapters.CalendarAdapter;

public class CalendarActivity extends AppCompatActivity {

    private CalendarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setTitle(R.string.academic_calendar);

        adapter = new CalendarAdapter(this, getWindow().getDecorView().getRootView());
        adapter.loadData();
    }

    @Override
    public void onBackPressed() {
        // Handle back button press if needed
    }
}

