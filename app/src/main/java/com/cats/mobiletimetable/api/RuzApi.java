package com.cats.mobiletimetable.api;

import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.api.responsemodels.LessonResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RuzApi {


    @GET("api/schedule/group/{group}")
    Call<List<LessonResponseModel>> getTimetableByGroup(@Path("group") String group, @Query("start") String start, @Query("finish") String finish, @Query("lng") int lng);

    @GET("api/search?type=group")
    Call<List<GroupResponseModel>> getGroupByString(@Query("term") String term);
}


