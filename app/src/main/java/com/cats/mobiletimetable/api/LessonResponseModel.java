package com.cats.mobiletimetable.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LessonResponseModel extends BaseResponseModel {

    @SerializedName("auditorium")
    @Expose
    public String auditorium;
    @SerializedName("auditoriumAmount")
    @Expose
    public Long auditoriumAmount;
    @SerializedName("auditoriumOid")
    @Expose
    public Long auditoriumOid;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("beginLesson")
    @Expose
    public String beginLesson;
    @SerializedName("building")
    @Expose
    public String building;
    @SerializedName("buildingGid")
    @Expose
    public Long buildingGid;
    @SerializedName("buildingOid")
    @Expose
    public Long buildingOid;
    @SerializedName("contentOfLoadOid")
    @Expose
    public Long contentOfLoadOid;
    @SerializedName("contentOfLoadUID")
    @Expose
    public String contentOfLoadUID;
    @SerializedName("contentTableOfLessonsName")
    @Expose
    public String contentTableOfLessonsName;
    @SerializedName("contentTableOfLessonsOid")
    @Expose
    public Long contentTableOfLessonsOid;
    @SerializedName("createddate")
    @Expose
    public String createddate;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("dateOfNest")
    @Expose
    public String dateOfNest;
    @SerializedName("dayOfWeek")
    @Expose
    public Long dayOfWeek;
    @SerializedName("dayOfWeekString")
    @Expose
    public String dayOfWeekString;
    @SerializedName("detailInfo")
    @Expose
    public String detailInfo;
    @SerializedName("discipline")
    @Expose
    public String discipline;
    @SerializedName("disciplineOid")
    @Expose
    public Long disciplineOid;
    @SerializedName("disciplineinplan")
    @Expose
    public Object disciplineinplan;
    @SerializedName("disciplinetypeload")
    @Expose
    public Long disciplinetypeload;
    @SerializedName("duration")
    @Expose
    public Long duration;
    @SerializedName("endLesson")
    @Expose
    public String endLesson;
    @SerializedName("group")
    @Expose
    public String group;
    @SerializedName("groupOid")
    @Expose
    public Long groupOid;
    @SerializedName("groupUID")
    @Expose
    public String groupUID;
    @SerializedName("group_facultyoid")
    @Expose
    public Long groupFacultyoid;
    @SerializedName("hideincapacity")
    @Expose
    public Long hideincapacity;
    @SerializedName("isBan")
    @Expose
    public Boolean isBan;
    @SerializedName("kindOfWork")
    @Expose
    public String kindOfWork;
    @SerializedName("kindOfWorkComplexity")
    @Expose
    public Long kindOfWorkComplexity;
    @SerializedName("kindOfWorkOid")
    @Expose
    public Long kindOfWorkOid;
    @SerializedName("kindOfWorkUid")
    @Expose
    public String kindOfWorkUid;
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
    @SerializedName("lessonNumberEnd")
    @Expose
    public Long lessonNumberEnd;
    @SerializedName("lessonNumberStart")
    @Expose
    public Long lessonNumberStart;
    @SerializedName("lessonOid")
    @Expose
    public Long lessonOid;
    @SerializedName("listOfLecturers")
    @Expose
    public List<LecturerResponseModel> listOfLecturers = null;
    @SerializedName("modifieddate")
    @Expose
    public String modifieddate;
    @SerializedName("note")
    @Expose
    public Object note;
    @SerializedName("note_description")
    @Expose
    public String noteDescription;
    @SerializedName("parentschedule")
    @Expose
    public String parentschedule;
    @SerializedName("replaces")
    @Expose
    public Object replaces;
    @SerializedName("stream")
    @Expose
    public Object stream;
    @SerializedName("streamOid")
    @Expose
    public Long streamOid;
    @SerializedName("stream_facultyoid")
    @Expose
    public Long streamFacultyoid;
    @SerializedName("subGroup")
    @Expose
    public Object subGroup;
    @SerializedName("subGroupOid")
    @Expose
    public Long subGroupOid;
    @SerializedName("subgroup_facultyoid")
    @Expose
    public Long subgroupFacultyoid;
    @SerializedName("tableofLessonsName")
    @Expose
    public String tableofLessonsName;
    @SerializedName("tableofLessonsOid")
    @Expose
    public Long tableofLessonsOid;
    @SerializedName("url1")
    @Expose
    public String url1;
    @SerializedName("url1_description")
    @Expose
    public String url1Description;
    @SerializedName("url2")
    @Expose
    public String url2;
    @SerializedName("url2_description")
    @Expose
    public String url2Description;

}