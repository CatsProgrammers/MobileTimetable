package com.cats.mobiletimetable.converters;

import com.cats.mobiletimetable.api.responsemodels.LessonResponseModel;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Building;
import com.cats.mobiletimetable.db.tables.KindOfWork;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Teacher;

public class LessonConverter extends AbstractConverter<LessonResponseModel, Lesson> {

    private final AppDatabase db;

    public LessonConverter(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Lesson convertToEntity(LessonResponseModel model) {

        Lesson lesson = new Lesson();

        lesson.name = model.discipline;
        lesson.beginLesson = model.beginLesson;
        lesson.endLesson = model.endLesson;
        lesson.url = model.url1;
        lesson.auditorium = model.auditorium;

        //Если нет потока, то значит, что это конкретная группа
        if (model.stream == null) {
            lesson.stream = model.group;
        }
        //Иначе это все же поток
        else {
            String bufStream = (String) model.stream;
            lesson.stream = bufStream.trim();
        }

        //Работаем со зданием
        long buildingId;
        Building connectedBuilding = db.buildingDao().getBuildingByLabel(model.building);
        if (connectedBuilding == null) {
            Building building = new Building();
            building.label = model.building;
            buildingId = db.buildingDao().insertBuilding(building);
        } else {
            buildingId = connectedBuilding.id;
        }
        lesson.buildingId = buildingId;

        //Работаем с преподавателем
        long teacherId;
        Teacher connectedTeacher = db.teacherDao().getTeacherByName(model.lecturerTitle);
        if (connectedTeacher == null) {
            Teacher teacher = new Teacher();
            teacher.name = model.lecturerTitle;
            teacher.rank = model.lecturerRank;
            teacher.email = model.lecturerEmail;
            teacherId = db.teacherDao().insertTeacher(teacher);
        } else {
            teacherId = connectedTeacher.id;
        }
        lesson.teacherId = teacherId;

        //Работаем с видами работ
        long kindOfWorkId;
        KindOfWork connectedKindOfWork = db.kindOfWorkDao().getItemByName(model.kindOfWork);
        if (connectedKindOfWork == null) {
            KindOfWork kindOfWork = new KindOfWork();
            kindOfWork.name = model.kindOfWork;
            kindOfWorkId = db.kindOfWorkDao().insertItem(kindOfWork);
        } else {
            kindOfWorkId = connectedKindOfWork.id;
        }
        lesson.kindOfWorkId = kindOfWorkId;
        return lesson;
    }

    @Override
    public String convertToString(Lesson entity) {
        return entity.name;
    }
}
