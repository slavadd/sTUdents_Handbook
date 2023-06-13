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

public class SearchSemesterFilesActivity extends AppCompatActivity {

    TextView facultyName;
    TextView weeklyPlan;
    TextView programTextLegend;
    TextView programTextLines;
    Button buttonSearch;
    Spinner spinnerSpecialty;
    Spinner spinnerCourse;
    Spinner spinnerStream;
    String[] specialty = {"-", "КСИ", "ИТИ", "КН (АЕ)", "КНИ (АЕ)", "Киберсигурност"};
    String[] course = {"-", "1", "2", "3", "4"};
    String[] stream = {"-", "8", "9", "10", "20"};

    String[] specialtyStrings;

    String chosenSpecialty = "";
    String chosenCourse = "";
    String chosenStream = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_semester_files);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search_semester_files);

        facultyName = findViewById(R.id.facultyName);
        weeklyPlan = findViewById(R.id.weeklyPlan);
        spinnerSpecialty = findViewById(R.id.spinnerSpecialty);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerStream = findViewById(R.id.spinnerStream);
        buttonSearch = findViewById(R.id.buttonSearch);
        programTextLegend = findViewById(R.id.programTextLegend);
        programTextLines = findViewById(R.id.programTextLines);

        programTextLegend.setText(getString(R.string.guide_ws_acronyms_l) + "\n" + getString(R.string.guide_ws_acronyms_su) + "\n" + getString(R.string.guide_ws_acronyms_lu));
        programTextLines.setText(getString(R.string.guide_ws_horizontal_line) + "\n" + getString(R.string.guide_ws_diagonal_line));

        specialtyStrings = new String[]{getString(R.string.dash), getString(R.string.specialty_ksi), getString(R.string.specialty_iti), getString(R.string.specialty_kn), getString(R.string.specialty_kni), getString(R.string.specialty_cybersecurity)};

        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(SearchSemesterFilesActivity.this, android.R.layout.simple_spinner_item, specialtyStrings);
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

        ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(SearchSemesterFilesActivity.this, android.R.layout.simple_spinner_item, course);
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

        ArrayAdapter<String> adapterStream = new ArrayAdapter<String>(SearchSemesterFilesActivity.this, android.R.layout.simple_spinner_item, stream);
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
                openPdf();

            }
        });

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    private void openPdf() {
        String value = "";

        //  0     1      2       3           4             5
        //{"-", "КСИ", "ИТИ", "КН (АЕ)", "КНИ (АЕ)", "Киберсигурност"};

        String course = getString(R.string.course);
        String flow = getString(R.string.flow);

        if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("1") && chosenStream.equals("9")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-1-9-10-03-2023-11-58.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("1") && chosenStream.equals("8")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-1-8-03-02-2023-16-15.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("1") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-1-10-09-03-2023-14-06.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[5]) && chosenCourse.equals("1") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%B8%D0%B3.-1-10-28-02-2023-14-23.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[3]) && chosenCourse.equals("1") && chosenStream.equals("20")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%9D_%D0%90%D0%95-1-20-16-02-2023-13-33.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("2") && chosenStream.equals("9")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-2-9-13-02-2023-14-58.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("2") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-2-10-27-02-2023-14-01.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("2") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-2-10-13-02-2023-14-56.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[3]) && chosenCourse.equals("2") && chosenStream.equals("20")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%9D_%D0%90%D0%95-2-20-13-02-2023-15-37.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("3") && chosenStream.equals("9")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-3-9-02-03-2023-15-23.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("3") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%A1%D0%98-3-10-07-03-2023-11-15.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("3") && chosenStream.equals("10")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-3-10-07-02-2023-11-03.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[4]) && chosenCourse.equals("3") && chosenStream.equals("20")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%9D%D0%98_%D0%90%D0%95-3-20-07-02-2023-11-15.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[1]) && chosenCourse.equals("4") && chosenStream.equals("9")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-4-9-07-02-2023-13-29.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[2]) && chosenCourse.equals("4") && chosenStream.equals("9")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-4-9-07-02-2023-13-29.pdf";
            openUrl(value);
        } else if (chosenSpecialty.equals(specialtyStrings[3]) && chosenCourse.equals("4") && chosenStream.equals("20")) {
            Toast.makeText(SearchSemesterFilesActivity.this, chosenSpecialty + ", " + course + " " + chosenCourse + ", " + flow + " " + chosenStream, Toast.LENGTH_SHORT).show();
            value = "https://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%9A%D0%9D_%D0%90%D0%95-4-20-07-02-2023-13-43.pdf";
            openUrl(value);
        } else {
            Toast.makeText(SearchSemesterFilesActivity.this, "Nothing Found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openUrl(String value) {
        String viewerUrl = "https://docs.google.com/viewer?url=";
        String pdfViewerURL = "https://drive.google.com/viewerng/viewer?embedded=true&url=";
        SemesterPdfActivity.setChangeUrl(pdfViewerURL + value);
        Intent intent = new Intent(SearchSemesterFilesActivity.this, SemesterPdfActivity.class);
        startActivity(intent);

        //without preview for pdf
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
//            startActivity(intent);

        //with preview
//      MainActivity2.setChangeUrl("https://docs.google.com/viewerng/viewer?url=http://tu-sofia.bg/weeklyprogram/2022-2023-leten/bakalavar/%D0%A4%D0%9A%D0%A1%D0%A2-%D0%98%D0%A2%D0%98-1-10-09-03-2023-14-06.pdf");
//      https://docs.google.com/viewerng/viewer?url=http://yourfile.pdf
    }

    @Override
    public void onBackPressed() {

    }

}