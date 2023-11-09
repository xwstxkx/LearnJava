package com.protasevich.egor.learnjava.model;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
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
