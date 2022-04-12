package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;
import android.util.Log;

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
            Log.i("INIT", "MODE: group");
            return new GroupRecycleViewInit(context, recyclerView, calendar);
        }
        Log.i("INIT", "MODE: teacher");
        return new TeacherRecycleViewInit(context, recyclerView, calendar);
    }
}
