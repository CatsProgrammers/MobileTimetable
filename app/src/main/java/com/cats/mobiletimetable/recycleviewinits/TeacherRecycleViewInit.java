package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.Utils;
import com.cats.mobiletimetable.adapters.LessonListAdapter;
import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.api.responsemodels.LessonResponseModel;
import com.cats.mobiletimetable.api.responsemodels.TeacherResponseModel;
import com.cats.mobiletimetable.converters.LessonConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherRecycleViewInit implements SuperRecycleViewInit {

    Calendar calendar;
    Context context;
    RecyclerView recyclerView;
    AppDatabase db;
    RuzApi ruzApi;
    LessonListAdapter lessonListAdapter;

    //TODO вытащить куда-нибудь конструктор (?)
    public TeacherRecycleViewInit(Context context, RecyclerView recyclerView, Calendar calendar) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.calendar = calendar;

        db = AppDatabase.getDbInstance(context);
        ruzApi = AppApi.getRuzApiInstance(context);

        initRecycleView();
        loadRecordList();
        loadApiData();
    }

    @Override
    public void initRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        lessonListAdapter = new LessonListAdapter(context);
        recyclerView.setAdapter(lessonListAdapter);

    }

    @Override
    public void loadRecordList() {
        List<LessonWithDetails> recordList = db.lessonDao().getAllLessonsWithDetails();
        lessonListAdapter.setLessonList(recordList);
    }

    @Override
    public void loadApiData() {

        String startDate = Utils.stringFormater(calendar.getTime());
        String endDate = Utils.stringFormater(calendar.getTime());

        Setting teacherSetting = db.settingsDao().getItemByName(Utils.teacherSettingsKey);
        //Если препода еще не устанавливали
        if (teacherSetting == null) {
            Toast.makeText(context, "Выберите преподавателя в настройках", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<TeacherResponseModel>> teacherCall = ruzApi.getTeacherByString(teacherSetting.value);
        teacherCall.enqueue(new Callback<List<TeacherResponseModel>>() {
            @Override
            public void onResponse(Call<List<TeacherResponseModel>> call, Response<List<TeacherResponseModel>> response) {
                if ((response.isSuccessful()) && (response.body() != null)) {
                    TeacherResponseModel currentTeacher = response.body().get(0);
                    loadLessonData(currentTeacher.id, startDate, endDate);
                } else {
                    Toast.makeText(context, "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TeacherResponseModel>> call, Throwable t) {
                Toast.makeText(context, "Connection error: " + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadLessonData(@NonNull String teacherId, @NonNull String start, @NonNull String finish) {
        LessonConverter lessonConverter = new LessonConverter(db);
        Call<List<LessonResponseModel>> call = ruzApi.getTimetableByTeacher(teacherId, start, finish, 1);
        call.enqueue(new Callback<List<LessonResponseModel>>() {
            @Override
            public void onResponse(Call<List<LessonResponseModel>> call, Response<List<LessonResponseModel>> response) {

                if ((response.isSuccessful()) && (response.body() != null)) {

                    db.lessonDao().deleteAll();
                    List<Lesson> lessonList = lessonConverter.convertToEntity(response.body());

                    for (Lesson lesson : lessonList) {
                        db.lessonDao().insertLesson(lesson);
                    }

                    loadRecordList();

                    Toast.makeText(context, "Успешно обновил расписание", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LessonResponseModel>> call, Throwable t) {
                Toast.makeText(context, "Connection error: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
