package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import com.protasevich.egor.learnjava.model.LessonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;


    public LessonEntity lessonSave(LessonModel lessonModel)
            throws ParametersNotSpecified {

        if (lessonModel.getName() == null) throw new ParametersNotSpecified();
        return lessonRepository.save(LessonModel.toEntity(lessonModel));

    }

    public LessonModel patchLesson(Long id, LessonModel lessonModel)
            throws ParametersNotSpecified {

        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(ParametersNotSpecified::new);
        LessonModel lesson = LessonModel.toModel(lessonEntity);

        if (lessonModel.getName() != null) {
            lesson.setName(lessonModel.getName());
        }
        if (lessonModel.getId() != null) {
            lesson.setId(lessonModel.getId());
        }

        LessonEntity savedLesson = LessonModel.toEntity(lesson);
        lessonRepository.save(savedLesson);
        return lesson;
    }

    public LessonModel getOneLesson(Long id) throws ObjectNotFound {

        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        if (lesson == null) throw new ObjectNotFound();
        return LessonModel.toModel(lesson);
    }

    public List<LessonModel> getAllLessons(PageRequest pageRequest) {
        Page<LessonEntity> page = lessonRepository.findAll(pageRequest);
        return LessonModel.toListModel(page.getContent());
    }

    public SchoolEntity saveManyLessons(List<LessonModel> lessonModelList)
            throws ParametersNotSpecified {
        for (LessonModel lessonModel : lessonModelList) {
            if (lessonModel.getName() == null) throw new ParametersNotSpecified();
        }
        lessonRepository.saveAll(LessonModel.toListEntity(lessonModelList));
        return null;
    }

    public Long deleteLesson(Long id) throws ObjectNotFound {
        lessonRepository.findById(id).orElseThrow(ObjectNotFound::new);
        lessonRepository.deleteById(id);
        return id;
    }
}
