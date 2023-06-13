package com.example.timetablenew.adapters;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetablenew.R;
import com.example.timetablenew.model.Calendar;

public class CalendarAdapter {

    private Calendar model;
    private TextView[] dateTextViews;
    private ProgressBar progressBar;
    private TextView tvNoInternet;
    private ScrollView scrollView;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private AppCompatActivity activity;

    public CalendarAdapter(AppCompatActivity activity, View rootView) {
        this.activity = activity;
        dateTextViews = new TextView[]{
                rootView.findViewById(R.id.liquidationDates),
                rootView.findViewById(R.id.winterSemDates),
                rootView.findViewById(R.id.winterBreakDates),
                rootView.findViewById(R.id.winterSessionDates),
                rootView.findViewById(R.id.winterRetakeDates),
                rootView.findViewById(R.id.summerSemDates),
                rootView.findViewById(R.id.summerSemSecondDates),
                rootView.findViewById(R.id.easterBreakDates),
                rootView.findViewById(R.id.summerSessionDates),
                rootView.findViewById(R.id.yearRetakeDates)
        };
        progressBar = rootView.findViewById(R.id.idPBLoading);
        tvNoInternet = rootView.findViewById(R.id.tvNoNet);
        scrollView = rootView.findViewById(R.id.sv_calendar);
        //constraintLayout = rootView.findViewById(R.id.cl_calendar);

        model = new Calendar();
    }

    public void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        if (!model.isInternetConnected()) {
            showNoInternetMessage();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String urlContents = model.getUrlContents();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] dates = model.processDates(urlContents);
                            updateDateTextViews(dates);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
    }

    private void showNoInternetMessage() {
        Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show();
        // tvNoInternet.setText("No internet connection");
    }

    private void updateDateTextViews(String[] dates) {
        for (int i = 0; i < dateTextViews.length; i++) {
            dateTextViews[i].setText(dates[i]);
        }
    }
}
