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


public class FcstMoodleFragment extends Fragment {

    private static final String PREFS_NAME = "PrefsFcstMoodle";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    WebView webView2;

    ProgressBar pbMoodle;
    TextView tvNoInternet;

    public FcstMoodleFragment() {
    }


    public static FcstMoodleFragment newInstance(String param1, String param2) {
        FcstMoodleFragment fragment = new FcstMoodleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fcst_moodle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView2 = (WebView) requireView().findViewById(R.id.idWebViewFcstMoodle);
        pbMoodle = (ProgressBar) requireView().findViewById(R.id.idPBLoading);
        tvNoInternet = (TextView) requireView().findViewById(R.id.tvNoNet);


        if (!InternetIsConnected()) {
            tvNoInternet.setVisibility(View.VISIBLE);
        } else {
            tvNoInternet.setVisibility(View.GONE);
        }

        webView2.loadUrl("https://fcst.bg/e-learning/bg/?redirect=0");
        webView2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbMoodle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbMoodle.setVisibility(View.GONE);

            }
        });

        WebSettings webSettings = webView2.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView2.canGoBack()) {
                                webView2.goBack();
                            }
                    }
                }
                return false;
            }
        });

        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstStartFcstMoodle = preferences.getBoolean("firstStartFcstMoodle", true);

        if (firstStartFcstMoodle) {
            showFcstMoodleDialog();
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

    private void showFcstMoodleDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.welcome_to_fcst_moodle_site)
                .setMessage(R.string.this_is_fcst_moodle)
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
        editor.putBoolean("firstStartFcstMoodle",false);
        editor.apply();
    }
}