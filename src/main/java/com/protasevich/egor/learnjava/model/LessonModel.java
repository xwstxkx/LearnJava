package com.protasevich.egor.learnjava.model;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LessonModel {

    private Long id;
    private String name;

    public static LessonModel toModel(LessonEntity entity) {
        LessonModel lessonModel = new LessonModel();
        lessonModel.setId(entity.getId());
        lessonModel.setName(entity.getName());
        return lessonModel;
    }

    public static LessonEntity toEntity(LessonModel lessonModel) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lessonModel.getId());
        lessonEntity.setName(lessonModel.getName());
        return lessonEntity;
    }

    public static List<LessonModel> toListModel(List<LessonEntity> lessonEntities) {
        List<LessonModel> lessonModelList = new ArrayList<>();
        for (LessonEntity lessonEntity : lessonEntities) {
            lessonModelList.add(toModel(lessonEntity));
        }
        return lessonModelList;
    }

    public static List<LessonEntity> toListEntity(List<LessonModel> lessonModelList) {
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for (LessonModel lessonModel : lessonModelList) {
            lessonEntities.add(toEntity(lessonModel));
        }
        return lessonEntities;
    }

    public LessonModel() {
    }
}
