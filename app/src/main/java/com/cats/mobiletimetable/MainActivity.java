package com.cats.mobiletimetable;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    EditText dateSelectEditText;
    RecyclerView timetableList;

    String s1[], s2[];
    int images[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO Для деста работы с RecyclerView, потом сделать лучше
        s1 = getResources().getStringArray(R.array.programming_languages);
        s2 = getResources().getStringArray(R.array.description);

        TimetableAdapter timetableAdapter = new TimetableAdapter(this, s1, s2);

        dateSelectEditText = findViewById(R.id.dateEditText);
        timetableList = findViewById(R.id.timetableList);

        timetableList.setAdapter(timetableAdapter);
        timetableList.setLayoutManager(new LinearLayoutManager(this));


        //Обработка активности с выбором даты
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        //Нажатие на выбор даты
        dateSelectEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        dateSelectEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

}