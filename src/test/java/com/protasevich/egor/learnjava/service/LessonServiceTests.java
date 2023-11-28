package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.dto.LessonDto;
import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class LessonServiceTests {

    @Mock
    private LessonRepository lessonRepository;
    @InjectMocks
    private LessonService lessonService;
    private LessonDto lessonDto1;
    private LessonDto lessonDto2;
    private LessonEntity lessonEntity;

    @BeforeEach
    public void init() {
        lessonDto1 = LessonDto.builder().name("Английский язык").build();
        lessonDto2 = LessonDto.builder().name("Русский язык").build();
        lessonEntity = LessonEntity.builder().name("Английский язык").build();
    }

    @Test
    void LessonServiceTests_lessonSave_ReturnsLessonEntity() throws ParametersNotSpecified {

        when(lessonRepository.save(Mockito.any(LessonEntity.class))).thenReturn(lessonEntity);

        LessonDto savedLesson = lessonService.saveLesson(lessonDto1);

        Assertions.assertNotNull(savedLesson);
    }


    @Test
    void LessonServiceTests_patchLesson_returnsLessonDto() throws ParametersNotSpecified {

        if (lessonDto1.getName() != null) {
            lessonEntity.setName(lessonDto1.getName());
        }

        when(lessonRepository.findById(1L)).thenReturn(Optional.ofNullable(lessonEntity));
        when(lessonRepository.save(Mockito.any(LessonEntity.class))).thenReturn(lessonEntity);

        LessonDto savedLesson = lessonService.patchLesson(1L, lessonDto1);

        Assertions.assertNotNull(savedLesson);
    }

    @Test
    void LessonServiceTests_findLessonById_ReturnsLessonDto() throws ObjectNotFound {
        when(lessonRepository.findById(1L)).thenReturn(Optional.ofNullable(lessonEntity));

        LessonDto savedLesson = lessonService.findLessonById(1L);

        Assertions.assertNotNull(savedLesson);
    }

    @Test
    void LessonServiceTests_getAllLessons_ReturnsLessonEntities() {

        Page<LessonEntity> page = Mockito.mock(Page.class);

        when(lessonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        List<LessonDto> saveLessons = lessonService.getAllLessons(PageRequest.of(1, 10));

        Assertions.assertNotNull(saveLessons);
    }

    @Test
    void LessonServiceTests_saveManyLessons_ReturnsListOfLessonDto() throws ParametersNotSpecified {
        List<LessonDto> lessonDtos = new ArrayList<>();
        lessonDtos.add(lessonDto1);
        lessonDtos.add(lessonDto2);

        when(lessonRepository.saveAll(Mockito.anyList())).thenReturn(Collections.singletonList(lessonEntity));

        List<LessonDto> savedLesson = lessonService.saveManyLessons(lessonDtos);

        Assertions.assertNotNull(savedLesson);
        Assertions.assertEquals(savedLesson, List.of(lessonDto1, lessonDto2));
    }

    @Test
    void LessonServiceTests_deleteLesson_ReturnsDeletedLessonId() {

        LessonEntity lessonEntity = LessonEntity.builder().name("Английский язык").build();

        when(lessonRepository.findById(1L)).thenReturn(Optional.ofNullable(lessonEntity));

        Assertions.assertAll(() -> lessonService.deleteLesson(1L));
    }
}