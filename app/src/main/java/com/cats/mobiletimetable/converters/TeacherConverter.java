package com.cats.mobiletimetable.converters;

import com.cats.mobiletimetable.api.responsemodels.TeacherResponseModel;
import com.cats.mobiletimetable.db.tables.Teacher;


public class TeacherConverter extends AbstractConverter<TeacherResponseModel, Teacher> {
    @Override
    public Teacher convertToEntity(TeacherResponseModel model) {
        Teacher teacher = new Teacher();
        teacher.name = model.label;
        return teacher;
    }

    @Override
    public String convertToString(Teacher entity) {
        return entity.name;
    }
}




