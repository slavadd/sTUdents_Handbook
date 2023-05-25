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

public class UissFragment extends Fragment {

    private static final String PREFS_NAME = "PrefsUISSMoodle";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    WebView webViewUiss;

    ProgressBar pbUiss;

    TextView tvNoInternet;

    public UissFragment() {
    }


    public static UissFragment newInstance(String param1, String param2) {
        UissFragment fragment = new UissFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uiss, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webViewUiss = (WebView) requireView().findViewById(R.id.idWebViewUiss);
        pbUiss = (ProgressBar) requireView().findViewById(R.id.idPBLoading);
        tvNoInternet = (TextView) requireView().findViewById(R.id.tvNoNet);


        if (!InternetIsConnected()) {
            tvNoInternet.setVisibility(View.VISIBLE);
        } else {
            tvNoInternet.setVisibility(View.GONE);
        }

        webViewUiss.loadUrl("https://student.tu-sofia.bg/");
        webViewUiss.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbUiss.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbUiss.setVisibility(View.GONE);

            }
        });

        WebSettings webSettings = webViewUiss.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewUiss.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webViewUiss.canGoBack()) {
                                webViewUiss.goBack();
                            }
                    }
                }
                return false;
            }
        });

        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstStartUissMoodle = preferences.getBoolean("firstStartUissMoodle", true);

        if (firstStartUissMoodle) {
            showUissSiteDialog();
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

    private void showUissSiteDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.welcome_to_uiss_site)
                .setMessage(R.string.this_is_uiss_site)
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
        editor.putBoolean("firstStartUissMoodle",false);
        editor.apply();
    }
}