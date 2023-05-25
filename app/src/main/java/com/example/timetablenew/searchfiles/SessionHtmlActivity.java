package com.example.timetablenew.searchfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.timetablenew.R;

public class SessionHtmlActivity extends AppCompatActivity {

    WebView sessionHtmlView;
    static String changeUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_html);

        sessionHtmlView = findViewById(R.id.sessionHtmlView);

        sessionHtmlView.setInitialScale(100);
        sessionHtmlView.getSettings().setBuiltInZoomControls(true);
        sessionHtmlView.getSettings().setDisplayZoomControls(false);
        sessionHtmlView.getSettings().setJavaScriptEnabled(true);

        sessionHtmlView.loadUrl(getChangeUrl());
    }

    public static String getChangeUrl() {
        return changeUrl;
    }

    public static void setChangeUrl(String changeUrl) {
        SessionHtmlActivity.changeUrl = changeUrl;
    }


}