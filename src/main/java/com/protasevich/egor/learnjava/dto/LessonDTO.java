package com.protasevich.egor.learnjava.dto;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LessonDTO {

    private Long id;
    private String name;

    public static LessonDTO toModel(LessonEntity entity) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(entity.getId());
        lessonDTO.setName(entity.getName());
        return lessonDTO;
    }

    public static LessonEntity toEntity(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lessonDTO.getId());
        lessonEntity.setName(lessonDTO.getName());
        return lessonEntity;
    }

    public static List<LessonDTO> toListModel(List<LessonEntity> lessonEntities) {
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        for (LessonEntity lessonEntity : lessonEntities) {
            lessonDTOList.add(toModel(lessonEntity));
        }
        return lessonDTOList;
    }

    public static List<LessonEntity> toListEntity(List<LessonDTO> lessonDTOList) {
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for (LessonDTO lessonDTO : lessonDTOList) {
            lessonEntities.add(toEntity(lessonDTO));
        }
        return lessonEntities;
    }

    public LessonDTO() {
    }
}
