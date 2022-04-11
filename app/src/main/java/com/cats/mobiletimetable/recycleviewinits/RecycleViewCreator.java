package com.cats.mobiletimetable.recycleviewinits;

import android.content.Context;
import android.util.Log;

import com.cats.mobiletimetable.Utils;
import com.cats.mobiletimetable.api.FaApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.db.tables.Setting;

import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RecycleViewCreator {

    private GroupRecycleViewInit groupRecycleViewInit;
    private TeacherRecycleViewInit teacherRecycleViewInit;

    //TODO
    private static SuperRecycleViewInit createInstance(Setting setting) {

        //
        userTypes

        if ((setting == null) || (setting.value.equals(userTypes.get(0)))){
            Log.i("INIT", "MODE: group");
            recycleViewInit = new GroupRecycleViewInit(this, recyclerView, myCalendar);
        }
        else{
            Log.i("INIT", "MODE: teacher");
            recycleViewInit = new TeacherRecycleViewInit(this, recyclerView, myCalendar);
        }



        if (ruzApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ruzBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            ruzApi = retrofit.create(RuzApi.class);
        }
        return ruzApi;
    }


    getInstance



}
