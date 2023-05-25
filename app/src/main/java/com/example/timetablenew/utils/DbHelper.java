package com.example.timetablenew.utils;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.timetablenew.model.Exam;
import com.example.timetablenew.model.Teacher;
import com.example.timetablenew.model.Week;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "timetabledb";
    private static final String TIMETABLE = "timetable";
    private static final String WEEK_ID = "id";
    private static final String WEEK_SUBJECT = "subject";
    private static final String WEEK_FRAGMENT = "fragment";
    private static final String WEEK_TEACHER = "teacher";
    private static final String WEEK_ROOM = "room";
    private static final String WEEK_FROM_TIME = "fromtime";
    private static final String WEEK_TO_TIME = "totime";
    private static final String WEEK_TYPE = "type";
    private static final String WEEK_FREQUENCY = "frequency";

    private static final String TEACHERS = "teachers";
    private static final String TEACHERS_ID = "id";
    private static final String TEACHERS_NAME = "name";
    private static final String TEACHERS_POST = "post";
    private static final String TEACHERS_PHONE_NUMBER = "phonenumber";
    private static final String TEACHERS_EMAIL = "email";

    private static final String EXAMS = "exams";
    private static final String EXAMS_ID = "id";
    private static final String EXAMS_SUBJECT = "subject";
    private static final String EXAMS_TEACHER = "teacher";
    private static final String EXAMS_ROOM = "room";
    private static final String EXAMS_DATE = "date";
    private static final String EXAMS_TIME = "time";
    private static final String EXAMS_TYPE = "type";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        String CREATE_TIMETABLE = "CREATE TABLE " + TIMETABLE + "("
                + WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WEEK_SUBJECT + " TEXT,"
                + WEEK_FRAGMENT + " TEXT,"
                + WEEK_TEACHER + " TEXT,"
                + WEEK_ROOM + " TEXT,"
                + WEEK_FROM_TIME + " TEXT,"
                + WEEK_TO_TIME + " TEXT,"
                + WEEK_TYPE + " TEXT,"
                + WEEK_FREQUENCY + " TEXT" + ")";


        String CREATE_TEACHERS = "CREATE TABLE " + TEACHERS + "("
                + TEACHERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TEACHERS_NAME + " TEXT,"
                + TEACHERS_POST + " TEXT,"
                + TEACHERS_PHONE_NUMBER + " TEXT,"
                + TEACHERS_EMAIL + " TEXT" + ")";

        String CREATE_EXAMS = "CREATE TABLE " + EXAMS + "("
                + EXAMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXAMS_SUBJECT + " TEXT,"
                + EXAMS_TEACHER + " TEXT,"
                + EXAMS_ROOM + " TEXT,"
                + EXAMS_DATE + " TEXT,"
                + EXAMS_TIME + " TEXT,"
                + EXAMS_TYPE + " TEXT" + ")";

        db.execSQL(CREATE_TIMETABLE);
        db.execSQL(CREATE_TEACHERS);
        db.execSQL(CREATE_EXAMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + TIMETABLE);

            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + TEACHERS);

            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + EXAMS);
                break;
        }
        onCreate(db);
    }

    /**
     * Methods for Week fragments
     **/
    public void insertWeek(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_FRAGMENT, week.getFragment());
        contentValues.put(WEEK_TEACHER, week.getTeacher());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME, week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_TYPE, week.getType());
        contentValues.put(WEEK_FREQUENCY, week.getFrequency());
        db.insert(TIMETABLE, null, contentValues);
        db.update(TIMETABLE, contentValues, WEEK_FRAGMENT, null);
        db.close();
    }

    public void deleteWeekById(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TIMETABLE, WEEK_ID + " = ? ", new String[]{String.valueOf(week.getId())});
        db.close();
    }

    public void updateWeek(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_TEACHER, week.getTeacher());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME, week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_TYPE, week.getType());
        contentValues.put(WEEK_FREQUENCY, week.getFrequency());
        db.update(TIMETABLE, contentValues, WEEK_ID + " = " + week.getId(), null);
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Week> getWeek(String fragment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Week> weeklist = new ArrayList<>();
        Week week;
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM " + TIMETABLE + " ORDER BY " + WEEK_FROM_TIME + " ) WHERE " + WEEK_FRAGMENT + " LIKE '" + fragment + "%'", null);


        try {
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    week = new Week();
                    week.setId(cursor.getInt(cursor.getColumnIndex(WEEK_ID)));
                    week.setSubject(cursor.getString(cursor.getColumnIndex(WEEK_SUBJECT)));
                    week.setTeacher(cursor.getString(cursor.getColumnIndex(WEEK_TEACHER)));
                    week.setRoom(cursor.getString(cursor.getColumnIndex(WEEK_ROOM)));
                    week.setFromTime(cursor.getString(cursor.getColumnIndex(WEEK_FROM_TIME)));
                    week.setToTime(cursor.getString(cursor.getColumnIndex(WEEK_TO_TIME)));
                    week.setType(cursor.getString(cursor.getColumnIndex(WEEK_TYPE)));
                    week.setFrequency(cursor.getString(cursor.getColumnIndex(WEEK_FREQUENCY)));
                    weeklist.add(week);

                } while (cursor.moveToNext());
            } else {
                Log.i("MyTag", "There are no entries in the data set");
            }
        } catch (Exception e) {
            Log.i(TAG, "getWeek: error");

        } finally {
            cursor.close();
            db.close();
        }
        return weeklist;
    }


    /**
     * Methods for Exams activity
     **/
    public void insertExam(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXAMS_SUBJECT, exam.getSubject());
        contentValues.put(EXAMS_TEACHER, exam.getTeacher());
        contentValues.put(EXAMS_ROOM, exam.getRoom());
        contentValues.put(EXAMS_DATE, exam.getDate());
        contentValues.put(EXAMS_TIME, exam.getTime());
        contentValues.put(EXAMS_TYPE, exam.getType());
        db.insert(EXAMS, null, contentValues);
        db.close();
    }

    public void updateExam(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXAMS_SUBJECT, exam.getSubject());
        contentValues.put(EXAMS_TEACHER, exam.getTeacher());
        contentValues.put(EXAMS_ROOM, exam.getRoom());
        contentValues.put(EXAMS_DATE, exam.getDate());
        contentValues.put(EXAMS_TIME, exam.getTime());
        contentValues.put(EXAMS_TYPE, exam.getType());
        db.update(EXAMS, contentValues, EXAMS_ID + " = " + exam.getId(), null);
        db.close();
    }

    public void deleteExamById(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXAMS, EXAMS_ID + " =? ", new String[]{String.valueOf(exam.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Exam> getExam() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Exam> examslist = new ArrayList<>();
        Exam exam;

        Cursor cursor = db.rawQuery("SELECT * FROM " + EXAMS, null);

        try {
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    exam = new Exam();
                    exam.setId(cursor.getInt(cursor.getColumnIndex(EXAMS_ID)));
                    exam.setSubject(cursor.getString(cursor.getColumnIndex(EXAMS_SUBJECT)));
                    exam.setTeacher(cursor.getString(cursor.getColumnIndex(EXAMS_TEACHER)));
                    exam.setRoom(cursor.getString(cursor.getColumnIndex(EXAMS_ROOM)));
                    exam.setDate(cursor.getString(cursor.getColumnIndex(EXAMS_DATE)));
                    exam.setTime(cursor.getString(cursor.getColumnIndex(EXAMS_TIME)));
                    exam.setType(cursor.getString(cursor.getColumnIndex(EXAMS_TYPE)));
                    examslist.add(exam);
                } while (cursor.moveToNext());
            } else {
                Log.i("MyTag", "There are no entries in the data set");
            }
        } catch (Exception e) {
            Log.i(TAG, "getExam: error");

        } finally {
            cursor.close();
            db.close();
        }
        return examslist;
    }

    public void insertTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHERS_NAME, teacher.getName());
        contentValues.put(TEACHERS_POST, teacher.getOffice());
        contentValues.put(TEACHERS_PHONE_NUMBER, teacher.getPhoneNumber());
        contentValues.put(TEACHERS_EMAIL, teacher.getEmail());
        db.insert(TEACHERS, null, contentValues);
        db.close();
    }

    public void updateTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHERS_NAME, teacher.getName());
        contentValues.put(TEACHERS_POST, teacher.getOffice());
        contentValues.put(TEACHERS_PHONE_NUMBER, teacher.getPhoneNumber());
        contentValues.put(TEACHERS_EMAIL, teacher.getEmail());
        db.update(TEACHERS, contentValues, TEACHERS_ID + " = " + teacher.getId(), null);
        db.close();
    }


    public void deleteTeacherById(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEACHERS, TEACHERS_ID + " =? ", new String[]{String.valueOf(teacher.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Teacher> getTeacher() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Teacher> teacherlist = new ArrayList<>();
        Teacher teacher;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TEACHERS, null);


        try {
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    teacher = new Teacher();
                    teacher.setId(cursor.getInt(cursor.getColumnIndex(TEACHERS_ID)));
                    teacher.setName(cursor.getString(cursor.getColumnIndex(TEACHERS_NAME)));
                    teacher.setOffice(cursor.getString(cursor.getColumnIndex(TEACHERS_POST)));
                    teacher.setPhoneNumber(cursor.getString(cursor.getColumnIndex(TEACHERS_PHONE_NUMBER)));
                    teacher.setEmail(cursor.getString(cursor.getColumnIndex(TEACHERS_EMAIL)));
                    //teacher.setColor(cursor.getInt(cursor.getColumnIndex(TEACHERS_COLOR)));
                    teacherlist.add(teacher);
                } while (cursor.moveToNext());
            } else {
                Log.i("MyTag", "There are no entries in the data set");
            }
        } catch (Exception e) {
            Log.i(TAG, "getTeacherExam: error");

        } finally {
            cursor.close();
            db.close();
        }

        return teacherlist;
    }


}

