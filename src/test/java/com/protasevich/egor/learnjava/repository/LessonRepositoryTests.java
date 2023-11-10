package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LessonRepositoryTests {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonRepositoryTests(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Test
    @DisplayName("POST /lessons/ возвращает HTTP-ответ со статусом 200 OK и lesson entity")
    public void LessonRepository_SaveLesson_ReturnSavedLesson() {

        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        //Act
        LessonEntity entity = lessonRepository.save(lessonEntity);
        //Assert
        Assertions.assertNotNull(lessonRepository.save(lessonEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("POST /lessons/all возвращает HTTP-ответ со статусом 200 OK и lesson entities")
    public void LessonRepository_SaveAll_ReturnSavedLesson() {

        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        //Act
        LessonEntity entity = lessonRepository.save(lessonEntity);
        //Assert
        Assertions.assertNotNull(lessonRepository.save(lessonEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("GET /lessons/all возвращает HTTP-ответ со статусом 200 OK и все lesson entity")
    public void LessonRepository_FindAll_ReturnListOfLessons() {

        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        LessonEntity lessonEntity2 = LessonEntity.builder()
                .name("Русский язык").build();
        //Act
        lessonRepository.save(lessonEntity);
        lessonRepository.save(lessonEntity2);
        List<LessonEntity> entities = lessonRepository.findAll();
        //Assert
        Assertions.assertNotNull(lessonRepository.findAll());
        Assertions.assertEquals(entities.size(), 2);
    }

    @Test
    @DisplayName("GET /lessons/{id} возвращает HTTP-ответ со статусом 200 OK lesson entity")
    public void LessonRepository_FindById_ReturnsLessonEntity() throws ObjectNotFound {
        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        LessonEntity lessonEntity2 = LessonEntity.builder()
                .name("Русский язык").build();
        //Act
        lessonRepository.save(lessonEntity);
        lessonRepository.save(lessonEntity2);
        LessonEntity lesson = lessonRepository.findById(lessonEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        //Assert
        Assertions.assertNotNull(lessonRepository.findById(lessonEntity2.getId()));
        Assertions.assertEquals(lesson.getName(), "Английский язык");
    }

    @Test
    @DisplayName("PUT /lessons/ возвращает HTTP-ответ со статусом 200 OK и lesson entity")
    public void LessonRepository_UpdateLesson_ReturnsLessonEntity() throws ObjectNotFound {
        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        //Act
        lessonRepository.save(lessonEntity);

        LessonEntity lessonSave = lessonRepository.findById(lessonEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        lessonSave.setName("Русский язык");

        LessonEntity lessonSaved = lessonRepository.save(lessonSave);
        //Assert
        Assertions.assertNotNull(lessonSaved);
        Assertions.assertEquals(lessonSave.getName(), "Русский язык");
    }

    @Test
    @DisplayName("DELETE /lessons/{id} возвращает HTTP-ответ со статусом 200 OK и пустую lesson entity")
    public void LessonRepository_DeleteLesson_ReturnsLessonIsEmpty() {
        //Arrange
        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        //Act
        lessonRepository.save(lessonEntity);
        lessonRepository.deleteById(lessonEntity.getId());
        Optional<LessonEntity> lessonReturn = lessonRepository.findById(lessonEntity.getId());
        //Assert
        Assertions.assertEquals(lessonReturn, Optional.empty());
    }
}
