package com.cats.mobiletimetable.api.responsemodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LecturerResponseModel extends BaseResponseModel {

    @SerializedName("lecturer")
    @Expose
    public String lecturer;
    @SerializedName("lecturerCustomUID")
    @Expose
    public String lecturerCustomUID;
    @SerializedName("lecturerEmail")
    @Expose
    public String lecturerEmail;
    @SerializedName("lecturerOid")
    @Expose
    public Long lecturerOid;
    @SerializedName("lecturerUID")
    @Expose
    public String lecturerUID;
    @SerializedName("lecturer_rank")
    @Expose
    public String lecturerRank;
    @SerializedName("lecturer_title")
    @Expose
    public String lecturerTitle;

}