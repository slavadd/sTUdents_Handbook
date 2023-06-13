package com.example.timetablenew.options;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.timetablenew.R;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    WebView webViewFeedback;
    ProgressBar pbFeedback;
    TextView tvNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.give_feedback);

        webViewFeedback = findViewById(R.id.idWebViewFeedback);
        pbFeedback = findViewById(R.id.idPBLoading);
        tvNoInternet = findViewById(R.id.tvNoNet);

        if (!InternetIsConnected()) {
            tvNoInternet.setVisibility(View.VISIBLE);
        } else {
            tvNoInternet.setVisibility(View.GONE);
        }


        webViewFeedback.loadUrl("https://docs.google.com/forms/d/1jqlATebsFe5Me2w7ysyeBWnqZQwvGYXpEwYUuVHOKgw/");
        webViewFeedback.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbFeedback.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbFeedback.setVisibility(View.GONE);

            }
        });

        WebSettings webSettings = webViewFeedback.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public boolean InternetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}