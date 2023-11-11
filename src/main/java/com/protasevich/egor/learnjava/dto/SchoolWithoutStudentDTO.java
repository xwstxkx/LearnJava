package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolWithoutStudentDTO {

    private Long id;
    private String name;

    public static SchoolWithoutStudentDTO toSchoolModel(SchoolEntity entity) {
        SchoolWithoutStudentDTO schoolWithoutStudentDTO = new SchoolWithoutStudentDTO();
        schoolWithoutStudentDTO.setId(entity.getId());
        schoolWithoutStudentDTO.setName(entity.getName());
        return schoolWithoutStudentDTO;
    }

    public SchoolWithoutStudentDTO() {
    }
}
