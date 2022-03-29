package com.cats.mobiletimetable;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.adapters.LessonListAdapter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    EditText dateSelectEditText;
    RecyclerView timetableRecyclerView;
    private LessonListAdapter lessonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateSelectEditText = findViewById(R.id.dateEditText);

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


        initRecycleView();
        loadRecordList();

    }

    private void initRecycleView() {

        timetableRecyclerView = findViewById(R.id.timetableRecyclerView);
        timetableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        timetableRecyclerView.addItemDecoration(dividerItemDecoration);
        lessonListAdapter = new LessonListAdapter(this);
        timetableRecyclerView.setAdapter(lessonListAdapter);

    }

    private void loadRecordList() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<LessonWithDetails> recordList = db.lessonDao().getAllLessonsWithDetails();
        lessonListAdapter.setLessonList(recordList);
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        dateSelectEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

}