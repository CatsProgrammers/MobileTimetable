package com.cats.mobiletimetable.converters;

import androidx.room.Entity;

import com.cats.mobiletimetable.api.responsemodels.BaseResponseModel;
import com.cats.mobiletimetable.db.tables.Teacher;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractConverter<ResponseModel extends BaseResponseModel, EntityType extends Entity> implements SuperConverter<ResponseModel, EntityType> {

    @Override
    public List<EntityType> convertToEntity(List<ResponseModel> modelList){
        List<EntityType> resultList = new ArrayList<>();
        for (ResponseModel item : modelList) {
            resultList.add(convertToEntity(item));
        }
        return resultList;
    }

    @Override
    public List<String> convertToString(List<EntityType> entityList){
        List<String> resultList = new ArrayList<>();
        for (EntityType item : entityList) {
            resultList.add(convertToString(item));
        }
        return resultList;
    }

}
