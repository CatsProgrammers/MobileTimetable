package com.cats.mobiletimetable.converters;


import com.cats.mobiletimetable.api.responsemodels.BaseResponseModel;
import com.cats.mobiletimetable.db.tables.BaseTable;

import java.util.List;

public interface SuperConverter<ResponseModel extends BaseResponseModel, EntityType extends BaseTable> {

    EntityType convertToEntity(ResponseModel model);

    List<EntityType> convertToEntity(List<ResponseModel> modelList);

    String convertToString(EntityType entity);

    List<String> convertToString(List<EntityType> entityList);
}
