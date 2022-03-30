package com.cats.mobiletimetable.api;

/*
TODO тут будет класс для работы с API расписания. По сути сделать порт с
https://github.com/GeorgiyDemo/fa_api/blob/master/fa_api/api.py
*/

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimetableAPI {


    @GET("api/schedule/group/{group}")
    Call<List<LessonResponseModel>> getTimetableByGroup(@Path("group") String group, @Query("start") String start, @Query("finish") String finish, @Query("lng") int lng);

    @GET("api/search")
    Call<List<GroupResponseModel>> getGroupByString(@Query("term") String term, @Query("type") String type);
}


