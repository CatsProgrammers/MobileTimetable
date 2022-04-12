package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.adapters.LessonListAdapter;
import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.db.AppDatabase;

import java.util.Calendar;

public abstract class AbstractRecycleViewInit implements SuperRecycleViewInit {

    Calendar calendar;
    Context context;
    RecyclerView recyclerView;
    AppDatabase db;
    RuzApi ruzApi;
    LessonListAdapter lessonListAdapter;

    public AbstractRecycleViewInit(Context context, RecyclerView recyclerView, Calendar calendar) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.calendar = calendar;

        db = AppDatabase.getDbInstance(context);
        ruzApi = AppApi.getRuzApiInstance(context);

        initRecycleView();
        loadRecordList();
        loadApiData();
    }
}
