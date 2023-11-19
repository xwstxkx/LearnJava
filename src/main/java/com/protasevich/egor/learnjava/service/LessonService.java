package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.dto.LessonDto;
import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
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

    public LessonDto saveLesson(LessonDto lessonDTO)
            throws ParametersNotSpecified {

        if (lessonDTO.getName() == null) throw new ParametersNotSpecified();
        lessonRepository.save(LessonDto.toEntity(lessonDTO));
        return lessonDTO;
    }

    public LessonDto patchLesson(Long id, LessonDto lessonDTO)
            throws ParametersNotSpecified {

        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(ParametersNotSpecified::new);
        LessonDto lesson = LessonDto.toModel(lessonEntity);

        if (lessonDTO.getName() != null) {
            lesson.setName(lessonDTO.getName());
        }
        if (lessonDTO.getId() != null) {
            lesson.setId(lessonDTO.getId());
        }

        LessonEntity savedLesson = LessonDto.toEntity(lesson);
        lessonRepository.save(savedLesson);
        return lesson;
    }

    public LessonDto findLessonById(Long id) throws ObjectNotFound {

        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        if (lesson == null) throw new ObjectNotFound();
        return LessonDto.toModel(lesson);
    }

    public List<LessonDto> getAllLessons(PageRequest pageRequest) {
        Page<LessonEntity> page = lessonRepository.findAll(pageRequest);
        return LessonDto.toListModel(page.getContent());
    }

    public List<LessonDto> saveManyLessons(List<LessonDto> lessonDtoList)
            throws ParametersNotSpecified {
        for (LessonDto lessonDTO : lessonDtoList) {
            if (lessonDTO.getName() == null) throw new ParametersNotSpecified();
        }
        lessonRepository.saveAll(LessonDto.toListEntity(lessonDtoList));
        return lessonDtoList;
    }

    public Long deleteLesson(Long id) throws ObjectNotFound {
        lessonRepository.findById(id).orElseThrow(ObjectNotFound::new);
        lessonRepository.deleteById(id);
        return id;
    }
}
