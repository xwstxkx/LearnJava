package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

    private Long id;
    private String name;
    private List<StudentDto> students;

    public static SchoolDto toModel(SchoolEntity entity) {
        SchoolDto schoolDTO = new SchoolDto();
        schoolDTO.setId(entity.getId());
        schoolDTO.setName(entity.getName());
        for (StudentEntity student : entity.getStudents()) {
            schoolDTO.getStudents().add(StudentDto.toModel(student));
        }
        return schoolDTO;
    }

    public static List<SchoolDto> toListModel(List<SchoolEntity> schoolEntities) {
        List<SchoolDto> schoolDtoList = new ArrayList<>();
        for (SchoolEntity schoolEntity : schoolEntities) {
            schoolDtoList.add(toModel(schoolEntity));
        }
        return schoolDtoList;
    }

    public static SchoolEntity toEntity(SchoolDto schoolDTO) {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setId(schoolDTO.getId());
        schoolEntity.setName(schoolDTO.getName());
        return schoolEntity;
    }

    public static List<SchoolEntity> toListEntity(List<SchoolDto> schoolDtoList) {
        List<SchoolEntity> schoolEntities = new ArrayList<>();
        for (SchoolDto schoolDTO : schoolDtoList) {
            schoolEntities.add(toEntity(schoolDTO));
        }
        return schoolEntities;
    }

    public List<StudentDto> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }
}
