package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.R;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class RecycleViewCreator {

    public static SuperRecycleViewInit createInstance(Setting setting, Context context, RecyclerView recyclerView, Calendar calendar) {

        List<String> userTypes = Arrays.asList(context.getResources().getStringArray(R.array.user_types));

        if ((setting == null) || (setting.value.equals(userTypes.get(0)))) {
            return new GroupRecycleViewInit(context, recyclerView, calendar);
        }
        return new TeacherRecycleViewInit(context, recyclerView, calendar);
    }
}
