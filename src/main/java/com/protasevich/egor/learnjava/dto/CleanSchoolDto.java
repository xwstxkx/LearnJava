package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleanSchoolDto {

    private Long id;
    private String name;

    public static CleanSchoolDto toSchoolModel(SchoolEntity entity) {
        CleanSchoolDto cleanSchoolDTO = new CleanSchoolDto();
        cleanSchoolDTO.setId(entity.getId());
        cleanSchoolDTO.setName(entity.getName());
        return cleanSchoolDTO;
    }

    public CleanSchoolDto() {
    }
}
