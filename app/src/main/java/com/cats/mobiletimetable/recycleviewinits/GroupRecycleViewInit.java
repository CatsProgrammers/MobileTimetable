package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.Utils;
import com.cats.mobiletimetable.adapters.LessonListAdapter;
import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.api.responsemodels.LessonResponseModel;
import com.cats.mobiletimetable.converters.LessonConverter;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupRecycleViewInit extends AbstractRecycleViewInit {

    public GroupRecycleViewInit(Context context, RecyclerView recyclerView, Calendar calendar) {
        super(context, recyclerView, calendar);
    }

    @Override
    public void initRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
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

        String startDate = Utils.stringFormatter(calendar.getTime());
        String endDate = Utils.getNextWeekDateString(calendar.getTime());

        Setting groupSetting = db.settingsDao().getItemByName(Utils.groupSettingsKey);
        //Если группу еще не устанавливали
        if (groupSetting == null) {
            Toast.makeText(context, "Выберите группу в настройках", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<GroupResponseModel>> groupCall = ruzApi.getGroupByString(groupSetting.value);
        groupCall.enqueue(new Callback<List<GroupResponseModel>>() {
            @Override
            public void onResponse(Call<List<GroupResponseModel>> call, Response<List<GroupResponseModel>> response) {
                if ((response.isSuccessful()) && (response.body() != null)) {
                    GroupResponseModel currentGroup = response.body().get(0);
                    loadLessonData(currentGroup.id, startDate, endDate);
                } else {
                    Toast.makeText(context, "Server returned an error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponseModel>> call, Throwable t) {
                Toast.makeText(context, "Connection error: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLessonData(@NonNull String groupId, @NonNull String start, @NonNull String finish) {
        LessonConverter lessonConverter = new LessonConverter(db);
        Call<List<LessonResponseModel>> call = ruzApi.getTimetableByGroup(groupId, start, finish, 1);
        call.enqueue(new Callback<List<LessonResponseModel>>() {
            @Override
            public void onResponse(Call<List<LessonResponseModel>> call, Response<List<LessonResponseModel>> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;

                    db.lessonDao().deleteAll();
                    List<Lesson> lessonList = lessonConverter.convertToEntity(response.body());

                    for (Lesson lesson : lessonList) {
                        db.lessonDao().insertLesson(lesson);
                    }

                    //List<LessonWithDetails> recordList = db.lessonDao().getAllLessonsWithDetails();
                    //lessonListAdapter.setLessonList(recordList);
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
