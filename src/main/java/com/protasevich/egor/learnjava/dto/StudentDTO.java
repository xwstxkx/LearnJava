package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private int grade;
    private Long schoolId;
    private List<LessonDTO> lessons;


    public static StudentDTO toModel(StudentEntity entity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(entity.getId());
        studentDTO.setFirstname(entity.getFirstname());
        studentDTO.setLastname(entity.getLastname());
        studentDTO.setGrade(entity.getGrade());
        studentDTO.setSchoolId(entity.getSchool().getId());
        for (LessonEntity lesson : entity.getLessons()) {
            studentDTO.getLessons().add(LessonDTO.toModel(lesson));
        }
        return studentDTO;
    }

    public static StudentDTO toSchoolModel(StudentEntity entity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(entity.getId());
        studentDTO.setFirstname(entity.getFirstname());
        studentDTO.setLastname(entity.getLastname());
        studentDTO.setGrade(entity.getGrade());
        studentDTO.setSchoolId(entity.getSchool().getId());
        return studentDTO;
    }

    public static List<StudentDTO> toListModel(List<StudentEntity> studentEntities) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            studentDTOList.add(toModel(studentEntity));
        }
        return studentDTOList;
    }

    public static StudentEntity toEntity(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentDTO.getId());
        studentEntity.setFirstname(studentDTO.getFirstname());
        studentEntity.setLastname(studentDTO.getLastname());
        studentEntity.setGrade(studentDTO.getGrade());
        return studentEntity;
    }

    public static List<StudentEntity> toListEntity(List<StudentDTO> studentDTOList) {
        List<StudentEntity> studentEntities = new ArrayList<>();
        for (StudentDTO studentDTO : studentDTOList) {
            studentEntities.add(toEntity(studentDTO));
        }
        return studentEntities;
    }

    public List<LessonDTO> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        return lessons;
    }
}
