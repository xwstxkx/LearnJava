package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String firstname;
    private String lastname;
    private int grade;
    private Long schoolId;
    private List<LessonDto> lessons;


    public static StudentDto toModel(StudentEntity entity) {
        StudentDto studentDTO = new StudentDto();
        studentDTO.setId(entity.getId());
        studentDTO.setFirstname(entity.getFirstname());
        studentDTO.setLastname(entity.getLastname());
        studentDTO.setGrade(entity.getGrade());
        studentDTO.setSchoolId(entity.getSchool().getId());
        for (LessonEntity lesson : entity.getLessons()) {
            studentDTO.getLessons().add(LessonDto.toModel(lesson));
        }
        return studentDTO;
    }

    public static StudentDto toSchoolModel(StudentEntity entity) {
        StudentDto studentDTO = new StudentDto();
        studentDTO.setId(entity.getId());
        studentDTO.setFirstname(entity.getFirstname());
        studentDTO.setLastname(entity.getLastname());
        studentDTO.setGrade(entity.getGrade());
        studentDTO.setSchoolId(entity.getSchool().getId());
        return studentDTO;
    }

    public static List<StudentDto> toListModel(List<StudentEntity> studentEntities) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            studentDtoList.add(toModel(studentEntity));
        }
        return studentDtoList;
    }

    public static StudentEntity toEntity(StudentDto studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentDTO.getId());
        studentEntity.setFirstname(studentDTO.getFirstname());
        studentEntity.setLastname(studentDTO.getLastname());
        studentEntity.setGrade(studentDTO.getGrade());
        return studentEntity;
    }

    public static List<StudentEntity> toListEntity(List<StudentDto> studentDtoList) {
        List<StudentEntity> studentEntities = new ArrayList<>();
        for (StudentDto studentDTO : studentDtoList) {
            studentEntities.add(toEntity(studentDTO));
        }
        return studentEntities;
    }

    public List<LessonDto> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        return lessons;
    }
}
