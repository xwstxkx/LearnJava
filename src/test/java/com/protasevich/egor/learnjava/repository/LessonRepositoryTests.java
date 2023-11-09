package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LessonRepositoryTests {

    @Autowired
    private LessonRepository lessonRepository;

    @Test
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
}
