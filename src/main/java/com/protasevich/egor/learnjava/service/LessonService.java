package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.dto.LessonDTO;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonDTO lessonSave(LessonDTO lessonDTO)
            throws ParametersNotSpecified {

        if (lessonDTO.getName() == null) throw new ParametersNotSpecified();
        lessonRepository.save(LessonDTO.toEntity(lessonDTO));
        return lessonDTO;
    }

    public String patchLesson(Long id, LessonDTO lessonDTO)
            throws ParametersNotSpecified {

        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(ParametersNotSpecified::new);
        LessonDTO lesson = LessonDTO.toModel(lessonEntity);

        if (lessonDTO.getName() != null) {
            lesson.setName(lessonDTO.getName());
        }
        if (lessonDTO.getId() != null) {
            lesson.setId(lessonDTO.getId());
        }

        LessonEntity savedLesson = LessonDTO.toEntity(lesson);
        lessonRepository.save(savedLesson);
        return "Патч урока был проведён успешно!";
    }

    public LessonDTO getOneLesson(Long id) throws ObjectNotFound {

        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        if (lesson == null) throw new ObjectNotFound();
        return LessonDTO.toModel(lesson);
    }

    public List<LessonDTO> getAllLessons(PageRequest pageRequest) {
        Page<LessonEntity> page = lessonRepository.findAll(pageRequest);
        return LessonDTO.toListModel(page.getContent());
    }

    public SchoolEntity saveManyLessons(List<LessonDTO> lessonDTOList)
            throws ParametersNotSpecified {
        for (LessonDTO lessonDTO : lessonDTOList) {
            if (lessonDTO.getName() == null) throw new ParametersNotSpecified();
        }
        lessonRepository.saveAll(LessonDTO.toListEntity(lessonDTOList));
        return null;
    }

    public Long deleteLesson(Long id) throws ObjectNotFound {
        lessonRepository.findById(id).orElseThrow(ObjectNotFound::new);
        lessonRepository.deleteById(id);
        return id;
    }
}
