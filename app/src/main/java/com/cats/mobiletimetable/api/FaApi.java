package com.cats.mobiletimetable.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaApi {

    @GET("/api/groups.json")
    Call<List<GroupResponseModel>> getAllGroups();
}
