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
public class LessonDto {

    private Long id;
    private String name;

    public static LessonDto toModel(LessonEntity entity) {
        LessonDto lessonDTO = new LessonDto();
        lessonDTO.setId(entity.getId());
        lessonDTO.setName(entity.getName());
        return lessonDTO;
    }

    public static LessonEntity toEntity(LessonDto lessonDTO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lessonDTO.getId());
        lessonEntity.setName(lessonDTO.getName());
        return lessonEntity;
    }

    public static List<LessonDto> toListModel(List<LessonEntity> lessonEntities) {
        List<LessonDto> lessonDtoList = new ArrayList<>();
        for (LessonEntity lessonEntity : lessonEntities) {
            lessonDtoList.add(toModel(lessonEntity));
        }
        return lessonDtoList;
    }

    public static List<LessonEntity> toListEntity(List<LessonDto> lessonDtoList) {
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for (LessonDto lessonDTO : lessonDtoList) {
            lessonEntities.add(toEntity(lessonDTO));
        }
        return lessonEntities;
    }

    public LessonDto() {
    }
}
