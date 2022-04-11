package com.cats.mobiletimetable.converters;



import androidx.room.Entity;

import com.cats.mobiletimetable.api.responsemodels.BaseResponseModel;

import java.util.List;

public interface SuperConverter<ResponseModel extends BaseResponseModel, EntityType extends Entity > {

    EntityType convertToEntity(ResponseModel model);

    List<EntityType> convertToEntity(List<ResponseModel> modelList);

    String convertToString(EntityType entity);

    List<String> convertToString(List<EntityType> entityList);
}
