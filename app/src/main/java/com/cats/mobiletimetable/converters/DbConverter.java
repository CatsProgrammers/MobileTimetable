package com.cats.mobiletimetable.converters;

import com.cats.mobiletimetable.api.responsemodels.GroupResponseModel;
import com.cats.mobiletimetable.api.responsemodels.LessonResponseModel;
import com.cats.mobiletimetable.api.responsemodels.TeacherResponseModel;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Building;
import com.cats.mobiletimetable.db.tables.Group;
import com.cats.mobiletimetable.db.tables.KindOfWork;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Teacher;

import java.util.ArrayList;
import java.util.List;

public class DbConverter {
    AppDatabase db;


    public DbConverter(AppDatabase db) {
        this.db = db;
    }


    public List<Teacher> teacherConverter(List<TeacherResponseModel> itemsList) {
        List<Teacher> resultList = new ArrayList<>();
        for (TeacherResponseModel item : itemsList) {
            resultList.add(teacherConverter(item));
        }
        return resultList;
    }

    public Teacher teacherConverter(TeacherResponseModel item) {
        Teacher teacher = new Teacher();
        teacher.name = item.label;
        return teacher;
    }

    public List<Group> groupConverter(List<GroupResponseModel> itemsList) {
        List<Group> resultList = new ArrayList<>();
        for (GroupResponseModel item : itemsList) {
            resultList.add(groupConverter(item));
        }
        return resultList;
    }

    public Group groupConverter(GroupResponseModel model) {

        Group group = new Group();
        group.description = model.description;
        group.labelId = model.id;
        group.name = model.label;
        group.type = model.type;
        return group;
    }

    public List<String> groupToStringConverter(List<Group> itemsList) {
        List<String> resultList = new ArrayList<>();
        for (Group item : itemsList) {
            resultList.add(groupToStringConverter(item));
        }
        return resultList;
    }

    public String groupToStringConverter(Group item) {
        return item.name;
    }

    public List<String> teacherToStringConverter(List<Teacher> itemsList) {
        List<String> resultList = new ArrayList<>();
        for (Teacher item : itemsList) {
            resultList.add(teacherToStringConverter(item));
        }
        return resultList;
    }

    public String teacherToStringConverter(Teacher item) {
        return item.name;
    }

    public List<Lesson> lessonConverter(List<LessonResponseModel> itemsList) {
        List<Lesson> resultList = new ArrayList<>();
        for (LessonResponseModel item : itemsList) {
            resultList.add(lessonConverter(item));
        }
        return resultList;
    }

    public Lesson lessonConverter(LessonResponseModel model) {

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


}
