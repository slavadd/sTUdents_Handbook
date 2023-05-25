package com.example.timetablenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.timetablenew.authentication.LoginActivity;
import com.example.timetablenew.authentication.ProfileActivity;
import com.example.timetablenew.searchfiles.SearchSemesterFilesActivity;
import com.example.timetablenew.searchfiles.SearchSessionFilesActivity;
import com.example.timetablenew.webviews.CseMoodleFragment;
import com.example.timetablenew.webviews.EtusFragment;
import com.example.timetablenew.webviews.FcstMoodleFragment;
import com.example.timetablenew.webviews.SportFragment;
import com.example.timetablenew.webviews.TuMailFragment;
import com.example.timetablenew.webviews.UissFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PREFS_NAME = "PrefsNotification";
    private DrawerLayout drawer;
    CardView weeklyPlan, exams;
    CardView teachers, maps;
    CardView profile, settings;
    NavigationView navigationView;
    ConstraintLayout constraintMainMenu;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        constraintMainMenu = findViewById(R.id.cl_main_menu);
        weeklyPlan = findViewById(R.id.cv_weekly_plan);
        exams = findViewById(R.id.cv_exams);

        teachers = findViewById(R.id.cv_teachers);
        maps = findViewById(R.id.cv_maps);

        profile = findViewById(R.id.cv_profile);
        settings = findViewById(R.id.cv_settings);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.nav_home);

        View headerView = navigationView.getHeaderView(0);
        TextView navMail = (TextView) headerView.findViewById(R.id.tv_header_email);
        mAuth = FirebaseAuth.getInstance();
        if (firebaseUser != null) {
            String userMail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
            navMail.setText(userMail);
        }


        //startAlarmBroadcastReceiver(MainMenuActivity.this);

//        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        boolean firstStartSport = preferences.getBoolean("firstStartSport",true);
//
//        if(firstStartSport){
//            startAlarmBroadcastReceiver(MainMenuActivity.this);
//        }

        weeklyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent timetable = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(timetable);

            }
        });

        exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exam = new Intent(MainMenuActivity.this, ExamsActivity.class);
                startActivity(exam);

            }
        });

        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teacher = new Intent(MainMenuActivity.this, TeachersActivity.class);
                startActivity(teacher);

            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(MainMenuActivity.this, MapsActivity.class);
                startActivity(map);

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profiles = new Intent(MainMenuActivity.this, ProfileActivity.class);
                startActivity(profiles);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(MainMenuActivity.this, SettingActivity.class);
                startActivity(setting);

            }
        });


    }

    @Override
    public void onBackPressed() {
        // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                Intent settings = new Intent(MainMenuActivity.this, SettingActivity.class);
//                startActivity(settings);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.nav_home:
                removeF();
                constraintMainMenu.setVisibility(View.VISIBLE);
                break;

            case R.id.nav_settings:
                Intent settings = new Intent(MainMenuActivity.this, SettingActivity.class);
                navigationView.setCheckedItem(R.id.nav_settings);
                startActivity(settings);
                break;

            case R.id.nav_cse_moodle:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new CseMoodleFragment());
                break;

            case R.id.nav_fcst_moodle:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new FcstMoodleFragment());
                break;

            case R.id.nav_tu_mail:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new TuMailFragment());
                break;

            case R.id.nav_uiss:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new UissFragment());
                break;

            case R.id.nav_etus:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new EtusFragment());
                break;

            case R.id.nav_sport_site:
                removeF();
                constraintMainMenu.setVisibility(View.GONE);
                replaceFragment(new SportFragment());
                break;

            case R.id.nav_session:
                Intent session = new Intent(MainMenuActivity.this, SearchSessionFilesActivity.class);
                navigationView.setCheckedItem(R.id.nav_session);
                startActivity(session);
                break;

            case R.id.nav_semester:
                Intent semester = new Intent(MainMenuActivity.this, SearchSemesterFilesActivity.class);
                navigationView.setCheckedItem(R.id.nav_session);
                startActivity(semester);
                break;


            default:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.add(R.id.frame_layout, fragment, "blank");
        fragmentTransaction.commit();

    }


    private void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    private void removeF() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentByTag("blank");
        if (f != null) {
            fragmentTransaction.remove(f);
            fragmentTransaction.commit();

        }


        //Fragment f = getFragmentManager().findFragmentByTag("first");
//        if(f!=null) fragmentTransac.remove(f);
//        fragmentTransac.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        //        if(firebaseUser == null){
        //            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        //            startActivity(intent);
        //            finish();
        //        }


    }
}