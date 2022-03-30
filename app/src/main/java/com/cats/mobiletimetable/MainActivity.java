package com.cats.mobiletimetable;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.adapters.LessonListAdapter;
import com.cats.mobiletimetable.api.GroupResponseModel;
import com.cats.mobiletimetable.api.LessonResponseModel;
import com.cats.mobiletimetable.api.TimetableAPI;
import com.cats.mobiletimetable.converters.DbConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;
import com.cats.mobiletimetable.db.tables.Lesson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    EditText dateSelectEditText;
    RecyclerView timetableRecyclerView;
    LessonListAdapter lessonListAdapter;
    TimetableAPI api;
    AppDatabase db;
    DbConverter converter;

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

        db = AppDatabase.getDbInstance(getApplicationContext());
        converter = new DbConverter(db);
        //Создание объекта Retrofit для http-запросов
        //TODO: Вынести в интерфейс
        String baseUrl = "https://ruz.fa.ru";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();


        api = retrofit.create(TimetableAPI.class);
        initRecycleView();
        loadApiData();
        loadRecordList();

    }

    private void loadApiData() {

        //Чистим от предыдущих записей
        //db.lessonDao().deleteAll();

        //TODO: взять даты с календаря
        String startDate = Utils.getCurrentDateString();
        String endDate = Utils.getNextWeekDateString();

        Call<List<GroupResponseModel>> groupCall = api.getGroupByString("ПИ19-3", "group");
        groupCall.enqueue(new Callback<List<GroupResponseModel>>() {
            @Override
            public void onResponse(Call<List<GroupResponseModel>> call, Response<List<GroupResponseModel>> response) {
                if ((response.isSuccessful()) && (Objects.requireNonNull(response.body()).size() == 1)) {
                    GroupResponseModel currentGroup = response.body().get(0);
                    loadLessonData(currentGroup.id, startDate, endDate);
                } else {
                    Toast.makeText(getApplicationContext(), "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLessonData(@NonNull String groupId, @NonNull String start, @NonNull String finish) {
        Call<List<LessonResponseModel>> call = api.getTimetableByGroup(groupId, start, finish, 1);
        call.enqueue(new Callback<List<LessonResponseModel>>() {
            @Override
            public void onResponse(Call<List<LessonResponseModel>> call, Response<List<LessonResponseModel>> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;

                    List<Lesson> lessonList = converter.lessonConverter(response.body());

                    for (Lesson lesson : lessonList) {
                        db.lessonDao().insertLesson(lesson);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LessonResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
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
        List<LessonWithDetails> recordList = db.lessonDao().getAllLessonsWithDetails();
        lessonListAdapter.setLessonList(recordList);
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        dateSelectEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

}