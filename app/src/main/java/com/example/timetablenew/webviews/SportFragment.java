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


public class SportFragment extends Fragment {

    private static final String PREFS_NAME = "PrefsSport";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    WebView webViewSport;
    ProgressBar pbSport;
    TextView tvNoInternet;

    public SportFragment() {
    }


    public static SportFragment newInstance(String param1, String param2) {
        SportFragment fragment = new SportFragment();
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
        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webViewSport = (WebView) requireView().findViewById(R.id.idWebViewSport);
        pbSport = (ProgressBar) requireView().findViewById(R.id.idPBLoading);
        tvNoInternet = (TextView) requireView().findViewById(R.id.tvNoNet);


        if (!InternetIsConnected()) {
            tvNoInternet.setVisibility(View.VISIBLE);
        } else {
            tvNoInternet.setVisibility(View.GONE);
        }

        webViewSport.loadUrl("https://sport.tu-sofia.bg/login.php");
        webViewSport.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbSport.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbSport.setVisibility(View.GONE);

            }
        });

        WebSettings webSettings = webViewSport.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewSport.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webViewSport.canGoBack()) {
                                webViewSport.goBack();
                            }
                    }
                }
                return false;
            }
        });


        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstStartSport = preferences.getBoolean("firstStartSport",true);

        if(firstStartSport){
            showSportDialog();
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

    private void showSportDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.welcome_to_tu_sports_site)
                .setMessage(R.string.here_you_can_sign_for_sport)
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
        editor.putBoolean("firstStartSport",false);
        editor.apply();
    }
}