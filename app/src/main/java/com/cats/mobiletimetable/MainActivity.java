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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.FaApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.api.responsemodels.TeacherResponseModel;
import com.cats.mobiletimetable.converters.GroupConverter;
import com.cats.mobiletimetable.converters.TeacherConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Group;
import com.cats.mobiletimetable.db.tables.Setting;
import com.cats.mobiletimetable.db.tables.Teacher;
import com.cats.mobiletimetable.recycleviewinits.RecycleViewCreator;
import com.cats.mobiletimetable.recycleviewinits.SuperRecycleViewInit;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    EditText dateSelectEditText;
    RecyclerView recyclerView;

    RuzApi ruzApi;
    FaApi faApi;
    AppDatabase db;
    DateTimeFormatter formatter;

    Setting currentUserType;
    SuperRecycleViewInit recycleViewInit;
    ActivityResultLauncher<Intent> startActivityForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateSelectEditText = findViewById(R.id.dateEditText);
        recyclerView = findViewById(R.id.timetableRecyclerView);

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
        ruzApi = AppApi.getRuzApiInstance(getApplicationContext());
        faApi = AppApi.getFaApiInstance(getApplicationContext());


        List<String> userTypes = Arrays.asList(getResources().getStringArray(R.array.user_types));

        //TODO Обновляем раз в недельку или если нет вообще записей
        syncGroupsApiData();
        syncTeachersApiData();


        currentUserType = db.settingsDao().getItemByName(Utils.userTypeSettingsKey);
        recycleViewInit = RecycleViewCreator.createInstance(currentUserType, this, recyclerView, myCalendar);

        //Когда возвращаемся с настроек - обновляем расписание
        startActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    currentUserType = db.settingsDao().getItemByName(Utils.userTypeSettingsKey);
                    recycleViewInit = RecycleViewCreator.createInstance(currentUserType, this, recyclerView, myCalendar);
                }
        );

    }


    private void syncTeachersApiData() {

        TeacherConverter teacherConverter = new TeacherConverter();

        Call<List<TeacherResponseModel>> call = faApi.getAllTeachers();
        call.enqueue(new Callback<List<TeacherResponseModel>>() {
            @Override
            public void onResponse(Call<List<TeacherResponseModel>> call, Response<List<TeacherResponseModel>> response) {
                if ((response.isSuccessful()) && (response.body() != null)) {
                    List<Teacher> teachersList = teacherConverter.convertToEntity(response.body());
                    for (Teacher teacher : teachersList) {
                        if (db.teacherDao().getTeacherByName(teacher.name) == null) {
                            db.teacherDao().insertTeacher(teacher);
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TeacherResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection error: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Синхронизация списка всех групп с БД
     */
    private void syncGroupsApiData() {

        GroupConverter groupConverter = new GroupConverter();
        Call<List<GroupResponseModel>> call = faApi.getAllGroups();
        call.enqueue(new Callback<List<GroupResponseModel>>() {
            @Override
            public void onResponse(Call<List<GroupResponseModel>> call, Response<List<GroupResponseModel>> response) {
                if ((response.isSuccessful()) && (response.body() != null)) {
                    List<Group> groupsList = groupConverter.convertToEntity(response.body());
                    for (Group item : groupsList) {
                        if (db.groupDao().getGroupByName(item.name) == null) {
                            db.groupDao().insertGroup(item);
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection error: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateSelectEditText.setText(dateFormat.format(myCalendar.getTime()));
        recycleViewInit.loadApiData();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult.launch(intent);
        }
        return true;
    }

}