package com.cats.mobiletimetable.api;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class AppApi {

    private final static String ruzBaseUrl = "https://ruz.fa.ru";
    private final static String faBaseIUrl = "https://schedule.fa.ru";

    private static RuzApi ruzApi;
    private static FaApi faApi;

    public static RuzApi getRuzApiInstance(Context context) {

        if (ruzApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ruzBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            ruzApi = retrofit.create(RuzApi.class);
        }
        return ruzApi;
    }

    public static FaApi getFaApiInstance(Context context) {

        if (faApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(faBaseIUrl).addConverterFactory(GsonConverterFactory.create()).build();
            faApi = retrofit.create(FaApi.class);
        }
        return faApi;
    }
}