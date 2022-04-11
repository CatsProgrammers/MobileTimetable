package com.cats.mobiletimetable.converters;

import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.db.tables.Group;

public class GroupConverter extends AbstractConverter<GroupResponseModel, Group> {

    @Override
    public Group convertToEntity(GroupResponseModel model) {
        Group group = new Group();
        group.description = model.description;
        group.labelId = model.id;
        group.name = model.label;
        group.type = model.type;
        return group;
    }

    @Override
    public String convertToString(Group entity) {
        return entity.name;
    }
}

