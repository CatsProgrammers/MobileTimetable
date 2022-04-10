package com.cats.mobiletimetable.api;

import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.api.responsemodels.TeacherResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaApi {

    @GET("/api/groups.json")
    Call<List<GroupResponseModel>> getAllGroups();

    @GET("/api/lecturers.json")
    Call<List<TeacherResponseModel>> getAllTeachers();
}
