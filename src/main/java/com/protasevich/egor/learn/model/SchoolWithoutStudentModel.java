package com.protasevich.egor.learn.model;

import com.protasevich.egor.learn.entity.SchoolEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolWithoutStudentModel {

    private Long id;
    private String name;

    public static SchoolWithoutStudentModel toSchoolModel(SchoolEntity entity) {
        SchoolWithoutStudentModel schoolWithoutStudentModel = new SchoolWithoutStudentModel();
        schoolWithoutStudentModel.setId(entity.getId());
        schoolWithoutStudentModel.setName(entity.getName());
        return schoolWithoutStudentModel;
    }

    public SchoolWithoutStudentModel() {
    }
}
