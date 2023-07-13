package com.example.timetablenew.model;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calendar {
    private String calendarUrl;

    public String getUrlContents() {
        // Fetch the calendar data from the URL
        // Return the fetched content as a string
        String theUrl = "https://tu-sofia.bg/university/calendar";
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String extractDate(String content, String regex) {
        // Extract the date using the provided regex pattern from the content
        // Return the extracted date as a string
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";

    }

    public boolean isInternetConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public String[] processDates(String content) {
        String[] regexes = {
                "[0-9]{2}.09.20[0-9]{2}\\s-\\s[0-9]{2}.09.20[0-9]{2}",
                "[0-9]{2}.09.20[0-9]{2}\\s-\\s[0-9]{2}.12.20[0-9]{2}",
                "[0-9]{2}.12.20[0-9]{2}\\s-\\s[0-9]{2}.01.20[0-9]{2}",
                "[0-9]{2}.01.20[0-9]{2}\\s-\\s[0-9]{2}.01.20[0-9]{2}",
                "[0-9]{2}.[0-9]{2}.20[0-9]{2}\\s-\\s[0-9]{2}.02.20[0-9]{2}",
                "[0-9]{2}.02.20[0-9]{2}\\s-\\s[0-9]{2}.04.20[0-9]{2}",
                "[0-9]{2}.04.20[0-9]{2}\\s-\\s[0-9]{2}.05.20[0-9]{2}",
                "[0-9]{2}.04.20[0-9]{2}\\s-\\s[0-9]{2}.05.20[0-9]{2}",
                "[0-9]{2}.05.20[0-9]{2}\\s-\\s[0-9]{2}.06.20[0-9]{2}",
                "[0-9]{2}.06.20[0-9]{2}\\s-\\s[0-9]{2}.07.20[0-9]{2}"
        };

        String[] dates = new String[regexes.length];
        for (int i = 0; i < regexes.length; i++) {
            String extractedDate = extractDate(content, regexes[i]);
            dates[i] = extractedDate;
        }
        return dates;
    }

}
