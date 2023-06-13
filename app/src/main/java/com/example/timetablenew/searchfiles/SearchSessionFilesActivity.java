package com.example.timetablenew.searchfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablenew.R;

import java.util.Objects;

public class SearchSessionFilesActivity extends AppCompatActivity {

    TextView facultyName;
    TextView examSession;
    Button buttonSearch;
    Spinner spinnerSpecialty;
    Spinner spinnerCourse;
    Spinner spinnerStream;
    String[] specialty = {"-", "КСИ", "ИТИ", "Киберсигурност"};
    String[] course = {"-", "1", "2", "3"};
    String[] stream = {"-", "8", "9", "10"};
    String[] specialtyStrings;
    String chosenSpecialty = "";
    String chosenCourse = "";
    String chosenStream = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_session_files);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search_session_files);

        facultyName = findViewById(R.id.facultyName);
        examSession = findViewById(R.id.examSession);
        spinnerSpecialty = findViewById(R.id.spinnerSpecialty);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerStream = findViewById(R.id.spinnerStream);
        buttonSearch = findViewById(R.id.buttonSearch);

        specialtyStrings = new String[]{getString(R.string.dash), getString(R.string.specialty_ksi), getString(R.string.specialty_iti), getString(R.string.specialty_cybersecurity)};

        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(SearchSessionFilesActivity.this, android.R.layout.simple_spinner_item, specialtyStrings);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);


        spinnerSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                chosenSpecialty = value;
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(SearchSessionFilesActivity.this, android.R.layout.simple_spinner_item, course);
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCourse.setAdapter(adapterCourse);

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                chosenCourse = value;
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterStream = new ArrayAdapter<String>(SearchSessionFilesActivity.this, android.R.layout.simple_spinner_item, stream);
        adapterStream.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStream.setAdapter(adapterStream);

        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                chosenStream = value;
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHtml();
            }
        });
    }


    private void openHtml() {
        String value = "";
        //  0     1      2          3
        //{"-", "КСИ", "ИТИ", "Киберсигурност"};

        String course = getString(R.string.course);
        String flow = getString(R.string.flow);

        if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("1") && chosenStream.equals("9")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-9-kurs-1_2.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("1") && chosenStream.equals("8")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-8-kurs-1_2.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("1") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98--potok-10-kurs-1_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[3]) && chosenCourse.equals("1") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%B8%D0%B1%D0%B5%D1%80%D1%81%D0%B8%D0%B3%D1%83%D1%80%D0%BD%D0%BE%D1%81%D1%82--potok-10-kurs-1_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("2") && chosenStream.equals("9")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-9-kurs-2_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("2") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-10-kurs-2_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("2") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98--potok-10-kurs-2_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("3") && chosenStream.equals("9")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-9-kurs-3_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("3") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-10-kurs-3_1.html";
            openHtmlWebView(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("3") && chosenStream.equals("10")) {
            Toast.makeText(SearchSessionFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/examsfiles/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98--potok-10-kurs-3_1.html";
            openHtmlWebView(value);
        } else {
            Toast.makeText(SearchSessionFilesActivity.this, "Nothing Found!", Toast.LENGTH_SHORT).show();
        }
    }


    private void openHtmlWebView(String value) {
        SessionHtmlActivity.setChangeUrl(value);
        Intent intent = new Intent(SearchSessionFilesActivity.this, SessionHtmlActivity.class);
        startActivity(intent);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }


    @Override
    public void onBackPressed() {

    }

}