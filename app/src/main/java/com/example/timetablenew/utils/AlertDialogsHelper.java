package com.example.timetablenew.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.example.timetablenew.R;
import com.example.timetablenew.adapters.ExamsAdapter;
import com.example.timetablenew.adapters.TeachersAdapter;
import com.example.timetablenew.adapters.WeekAdapter;
import com.example.timetablenew.model.Exam;
import com.example.timetablenew.model.Teacher;
import com.example.timetablenew.model.Week;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.example.timetablenew.adapters.FragmentsTabAdapter;

//import org.xdty.preference.colorpicker.ColorPickerDialog;
//import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlertDialogsHelper {

    public static void getEditSubjectDialog(final Activity activity, final View alertLayout, final ArrayList<Week> adapter, final ListView listView, int position) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subject_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacher_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.room_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView from_time = alertLayout.findViewById(R.id.from_time);
        final TextView to_time = alertLayout.findViewById(R.id.to_time);

        final EditText type = alertLayout.findViewById(R.id.type_dialog);
        editTextHashs.put(R.string.subject_type, type);
        final EditText frequency = alertLayout.findViewById(R.id.frequency_dialog);
        editTextHashs.put(R.string.subject_frequency, frequency);

        final Week week = adapter.get(position);

        subject.setText(week.getSubject());
        teacher.setText(week.getTeacher());
        room.setText(week.getRoom());
        from_time.setText(week.getFromTime());
        to_time.setText(week.getToTime());
        type.setText(week.getType());
        frequency.setText(week.getFrequency());

        from_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                from_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setFromTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        to_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                to_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setToTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        //alert.setTitle(R.string.edit_subject);
        alert.setCancelable(false);
        TextView title = alertLayout.findViewById(R.id.dialog_subject_title);
        title.setText(R.string.edit_subject);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!from_time.getText().toString().matches(".*\\d+.*") || !to_time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper db = new DbHelper(activity);
                    WeekAdapter weekAdapter = (WeekAdapter) listView.getAdapter(); // In order to get notifyDataSetChanged() method.

                    week.setSubject(subject.getText().toString());
                    week.setTeacher(teacher.getText().toString());
                    week.setRoom(room.getText().toString());
                    week.setType(type.getText().toString());
                    week.setFrequency(frequency.getText().toString());

                    db.updateWeek(week);
                    weekAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddSubjectDialog(final Activity activity, final View alertLayout, final FragmentsTabAdapter adapter, final ViewPager viewPager) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subject_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacher_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.room_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView from_time = alertLayout.findViewById(R.id.from_time);
        final TextView to_time = alertLayout.findViewById(R.id.to_time);
        final EditText type = alertLayout.findViewById(R.id.type_dialog);
        editTextHashs.put(R.string.subject_type, type);
        final EditText frequency = alertLayout.findViewById(R.id.frequency_dialog);
        editTextHashs.put(R.string.subject_frequency, frequency);

        final Week week = new Week();

        from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                from_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setFromTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                to_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setToTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        TextView title = alertLayout.findViewById(R.id.dialog_subject_title);
        title.setText(R.string.add_subject);
        Button cancel = alertLayout.findViewById(R.id.cancel);
        Button submit = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!from_time.getText().toString().matches(".*\\d+.*") || !to_time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    Matcher fragment = Pattern.compile("(.*Fragment)").matcher(adapter.getItem(viewPager.getCurrentItem()).toString());
                    week.setSubject(subject.getText().toString());
                    week.setFragment(fragment.find() ? fragment.group() : null);
                    week.setTeacher(teacher.getText().toString());
                    week.setRoom(room.getText().toString());
                    week.setType(type.getText().toString());
                    week.setFrequency(frequency.getText().toString());

                    dbHelper.insertWeek(week);
                    adapter.notifyDataSetChanged();
                    subject.getText().clear();
                    teacher.getText().clear();
                    room.getText().clear();
                    from_time.setText(R.string.select_time);
                    to_time.setText(R.string.select_time);
                    type.getText().clear();
                    frequency.getText().clear();

                    subject.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getEditExamDialog(final Activity activity, final View alertLayout, final ArrayList<Exam> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subjectexam_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacherexam_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.roomexam_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView date = alertLayout.findViewById(R.id.dateexam_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeexam_dialog);
        final EditText type = alertLayout.findViewById(R.id.typeexam_dialog);
        editTextHashs.put(R.string.subject_type, type);

        final Exam exam = adapter.get(listposition);

        subject.setText(exam.getSubject());
        teacher.setText(exam.getTeacher());
        room.setText(exam.getRoom());
        date.setText(exam.getDate());
        time.setText(exam.getTime());
        type.setText(exam.getType());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth));
                        exam.setDate(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                exam.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        TextView title = alertLayout.findViewById(R.id.dialog_exam_title);
        title.setText(R.string.edit_exam);

        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    exam.setSubject(subject.getText().toString());
                    exam.setTeacher(teacher.getText().toString());
                    exam.setRoom(room.getText().toString());
                    exam.setType(type.getText().toString());
                    dbHelper.updateExam(exam);

                    ExamsAdapter examsAdapter = (ExamsAdapter) listView.getAdapter();
                    examsAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddExamDialog(final Activity activity, final View alertLayout, final ExamsAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subjectexam_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacherexam_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.roomexam_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView date = alertLayout.findViewById(R.id.dateexam_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeexam_dialog);
        final EditText type = alertLayout.findViewById(R.id.typeexam_dialog);
        editTextHashs.put(R.string.subject_type, type);

        final Exam exam = new Exam();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth));
                        exam.setDate(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                exam.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        TextView title = alertLayout.findViewById(R.id.dialog_exam_title);
        title.setText(R.string.add_exam);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    //ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    exam.setSubject(subject.getText().toString());
                    exam.setTeacher(teacher.getText().toString());
                    exam.setRoom(room.getText().toString());
                    exam.setType(type.getText().toString());
                    dbHelper.insertExam(exam);

                    adapter.clear();
                    adapter.addAll(dbHelper.getExam());
                    adapter.notifyDataSetChanged();

                    subject.getText().clear();
                    teacher.getText().clear();
                    room.getText().clear();
                    date.setText(R.string.select_date);
                    time.setText(R.string.select_time);
                    type.getText().clear();
                    subject.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }


    public static void getEditTeacherDialog(final Activity activity, final View alertLayout, final ArrayList<Teacher> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.name_dialog);
        editTextHashs.put(R.string.name, name);
        final EditText post = alertLayout.findViewById(R.id.post_dialog);
        editTextHashs.put(R.string.post, post);
        final EditText phone_number = alertLayout.findViewById(R.id.phonenumber_dialog);
        editTextHashs.put(R.string.phone_number, phone_number);
        final EditText email = alertLayout.findViewById(R.id.email_dialog);
        editTextHashs.put(R.string.email, email);
        final Teacher teacher = adapter.get(listposition);

        name.setText(teacher.getName());
        post.setText(teacher.getOffice());
        phone_number.setText(teacher.getPhoneNumber());
        email.setText(teacher.getEmail());

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        TextView title = alertLayout.findViewById(R.id.dialog_teacher_title);
        title.setText(R.string.edit_teacher);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(post.getText()) || TextUtils.isEmpty(phone_number.getText()) || TextUtils.isEmpty(email.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    TeachersAdapter teachersAdapter = (TeachersAdapter) listView.getAdapter();
                    teacher.setName(name.getText().toString());
                    teacher.setOffice(post.getText().toString());
                    teacher.setPhoneNumber(phone_number.getText().toString());
                    teacher.setEmail(email.getText().toString());
                    dbHelper.updateTeacher(teacher);
                    teachersAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }


    public static void getAddTeacherDialog(final Activity activity, final View alertLayout, final TeachersAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.name_dialog);
        editTextHashs.put(R.string.name, name);
        final EditText post = alertLayout.findViewById(R.id.post_dialog);
        editTextHashs.put(R.string.post, post);
        final EditText phone_number = alertLayout.findViewById(R.id.phonenumber_dialog);
        editTextHashs.put(R.string.phone_number, phone_number);
        final EditText email = alertLayout.findViewById(R.id.email_dialog);
        editTextHashs.put(R.string.email, email);
        final Teacher teacher = new Teacher();

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);

        TextView title = alertLayout.findViewById(R.id.dialog_teacher_title);
        title.setText(R.string.add_teacher);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(post.getText()) || TextUtils.isEmpty(phone_number.getText()) || TextUtils.isEmpty(email.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    teacher.setName(name.getText().toString());
                    teacher.setOffice(post.getText().toString());
                    teacher.setPhoneNumber(phone_number.getText().toString());
                    teacher.setEmail(email.getText().toString());
                    dbHelper.insertTeacher(teacher);

                    adapter.clear();
                    adapter.addAll(dbHelper.getTeacher());
                    adapter.notifyDataSetChanged();

                    name.getText().clear();
                    post.getText().clear();
                    phone_number.getText().clear();
                    email.getText().clear();
                    name.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }

}
