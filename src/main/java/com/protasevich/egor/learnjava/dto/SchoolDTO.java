package com.protasevich.egor.learnjava.dto;

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
public class SchoolDTO {

    private Long id;
    private String name;
    private List<StudentDTO> students;

    public static SchoolDTO toModel(SchoolEntity entity) {
        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setId(entity.getId());
        schoolDTO.setName(entity.getName());
        for (StudentEntity student : entity.getStudents()) {
            schoolDTO.getStudents().add(StudentDTO.toModel(student));
        }
        return schoolDTO;
    }

    public static List<SchoolDTO> toListModel(List<SchoolEntity> schoolEntities) {
        List<SchoolDTO> schoolDTOList = new ArrayList<>();
        for (SchoolEntity schoolEntity : schoolEntities) {
            schoolDTOList.add(toModel(schoolEntity));
        }
        return schoolDTOList;
    }

    public static SchoolEntity toEntity(SchoolDTO schoolDTO) {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setId(schoolDTO.getId());
        schoolEntity.setName(schoolDTO.getName());
        return schoolEntity;
    }

    public static List<SchoolEntity> toListEntity(List<SchoolDTO> schoolDTOList) {
        List<SchoolEntity> schoolEntities = new ArrayList<>();
        for (SchoolDTO schoolDTO : schoolDTOList) {
            schoolEntities.add(toEntity(schoolDTO));
        }
        return schoolEntities;
    }

    public List<StudentDTO> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }
}
