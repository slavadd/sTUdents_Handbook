package com.example.timetablenew.webviews;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.timetablenew.R;


public class CseMoodleFragment extends Fragment {

    private static final String PREFS_NAME = "PrefsCseMoodle";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    WebView webView1;
    ProgressBar progressBar;

    TextView tvNoInternet;

    public CseMoodleFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cse_moodle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView1 = (WebView) requireView().findViewById(R.id.idWebViewCseMoodle);
        progressBar = (ProgressBar) requireView().findViewById(R.id.idPBLoading);
        tvNoInternet = (TextView) requireView().findViewById(R.id.tvNoNet);


        if (!InternetIsConnected()) {
            tvNoInternet.setVisibility(View.VISIBLE);
        } else {
            tvNoInternet.setVisibility(View.GONE);
        }

        webView1.loadUrl("http://81.161.243.12/bgmoodle/");
        webView1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);

            }
        });

        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView1.canGoBack()) {
                                webView1.goBack();
                            }
                    }
                }
                return false;
            }
        });


        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstStartCseMoodle = preferences.getBoolean("firstStartCseMoodle", true);

        if (firstStartCseMoodle) {
            showCseMoodleDialog();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public boolean InternetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }


    }

    private void showCseMoodleDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.welcome_to_cse_moodle_site)
                .setMessage(R.string.this_is_cse_moodle)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();

        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStartCseMoodle",false);
        editor.apply();
    }


}