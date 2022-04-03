package com.cats.mobiletimetable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.GroupResponseModel;
import com.cats.mobiletimetable.api.LessonResponseModel;
import com.cats.mobiletimetable.api.TimetableAPI;
import com.cats.mobiletimetable.converters.DbConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Setting;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    DateTimeFormatter formatter;

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

        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dateSelectEditText.setText(formatter.format(LocalDate.now()));

        db = AppDatabase.getDbInstance(getApplicationContext());
        api = AppApi.getApiInstance(getApplicationContext());
        converter = new DbConverter(db);

        initRecycleView();
        loadRecordList();
        loadApiData();

    }

    private void loadApiData() {
        Setting currentGroup = db.settingsDao().getItemByName("currentGroup");
        //Если группу еще не устанавливали
        if (currentGroup == null){
            Toast.makeText(getApplicationContext(), "Выберите группу в настройках", Toast.LENGTH_SHORT).show();
            return;
        }


        String startDate = Utils.stringFormater(myCalendar.getTime());
        String endDate = Utils.stringFormater(myCalendar.getTime());

        //TODO: динамично подставлять группу пользователя
        Call<List<GroupResponseModel>> groupCall = api.getGroupByString(currentGroup.value, "group");
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

                    db.lessonDao().deleteAll();
                    List<Lesson> lessonList = converter.lessonConverter(response.body());

                    for (Lesson lesson : lessonList) {
                        db.lessonDao().insertLesson(lesson);
                    }

                    //List<LessonWithDetails> recordList = db.lessonDao().getAllLessonsWithDetails();
                    //lessonListAdapter.setLessonList(recordList);
                    loadRecordList();

                    Toast.makeText(getApplicationContext(), "Успешно обновил расписание", Toast.LENGTH_SHORT).show();

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateSelectEditText.setText(dateFormat.format(myCalendar.getTime()));
        loadApiData();
    }




    // создание меню из XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // обработка нажатий на меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String settingsString = getResources().getString(R.string.action_settings);
        if (item.getTitle().toString().equals(settingsString)) {
            //TODO: добавить листенер на обновлялку
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

}