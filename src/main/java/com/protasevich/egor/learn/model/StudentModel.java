package com.protasevich.egor.learn.model;

import com.protasevich.egor.learn.entity.LessonEntity;
import com.protasevich.egor.learn.entity.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentModel {

    private Long id;
    private String firstname;
    private String lastname;
    private int grade;
    private Long schoolId;
    private List<LessonModel> lessons;


    public static StudentModel toModel(StudentEntity entity) {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(entity.getId());
        studentModel.setFirstname(entity.getFirstname());
        studentModel.setLastname(entity.getLastname());
        studentModel.setGrade(entity.getGrade());
        studentModel.setSchoolId(entity.getSchool().getId());
        for (LessonEntity lesson : entity.getLessons()) {
            studentModel.getLessons().add(LessonModel.toModel(lesson));
        }
        return studentModel;
    }

    public static StudentModel toSchoolModel(StudentEntity entity) {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(entity.getId());
        studentModel.setFirstname(entity.getFirstname());
        studentModel.setLastname(entity.getLastname());
        studentModel.setGrade(entity.getGrade());
        studentModel.setSchoolId(entity.getSchool().getId());
        return studentModel;
    }

    public static List<StudentModel> toListModel(List<StudentEntity> studentEntities) {
        List<StudentModel> studentModelList = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            studentModelList.add(toModel(studentEntity));
        }
        return studentModelList;
    }

    public static StudentEntity toEntity(StudentModel studentModel) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentModel.getId());
        studentEntity.setFirstname(studentModel.getFirstname());
        studentEntity.setLastname(studentModel.getLastname());
        studentEntity.setGrade(studentModel.getGrade());
        return studentEntity;
    }

    public static List<StudentEntity> toListEntity(List<StudentModel> studentModelList) {
        List<StudentEntity> studentEntities = new ArrayList<>();
        for (StudentModel studentModel : studentModelList) {
            studentEntities.add(toEntity(studentModel));
        }
        return studentEntities;
    }

    public List<LessonModel> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        return lessons;
    }
}
