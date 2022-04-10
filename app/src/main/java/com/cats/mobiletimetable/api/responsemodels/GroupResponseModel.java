package com.cats.mobiletimetable.api.responsemodels;

public class GroupResponseModel extends BaseResponseModel {

    public String id;
    public String label;
    public String description;

    public String type;

    @Override
    public String toString() {
        return "GroupResponseModel{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}