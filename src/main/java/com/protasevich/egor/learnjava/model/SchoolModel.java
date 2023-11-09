package com.protasevich.egor.learnjava.model;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchoolModel {

    private Long id;
    private String name;
    private List<StudentModel> students;

    public static SchoolModel toModel(SchoolEntity entity) {
        SchoolModel schoolModel = new SchoolModel();
        schoolModel.setId(entity.getId());
        schoolModel.setName(entity.getName());
        for (StudentEntity student : entity.getStudents()) {
            schoolModel.getStudents().add(StudentModel.toModel(student));
        }
        return schoolModel;
    }

    public static List<SchoolModel> toListModel(List<SchoolEntity> schoolEntities) {
        List<SchoolModel> schoolModelList = new ArrayList<>();
        for (SchoolEntity schoolEntity : schoolEntities) {
            schoolModelList.add(toModel(schoolEntity));
        }
        return schoolModelList;
    }

    public static SchoolEntity toEntity(SchoolModel schoolModel) {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setId(schoolModel.getId());
        schoolEntity.setName(schoolModel.getName());
        return schoolEntity;
    }

    public static List<SchoolEntity> toListEntity(List<SchoolModel> schoolModelList) {
        List<SchoolEntity> schoolEntities = new ArrayList<>();
        for (SchoolModel schoolModel : schoolModelList) {
            schoolEntities.add(toEntity(schoolModel));
        }
        return schoolEntities;
    }

    public SchoolModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<StudentModel> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }
}
