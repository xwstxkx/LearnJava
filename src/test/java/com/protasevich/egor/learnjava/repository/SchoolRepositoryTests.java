package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
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
public class SchoolRepositoryTests {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolRepositoryTests(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Test
    @DisplayName("POST /schools/ возвращает HTTP-ответ со статусом 200 OK и school entity")
    public void SchoolRepository_SaveSchool_ReturnSavedSchool() {

        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        //Act
        SchoolEntity entity = schoolRepository.save(schoolEntity);
        //Assert
        Assertions.assertNotNull(schoolRepository.save(schoolEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("POST /schools/all возвращает HTTP-ответ со статусом 200 OK и lesson entities")
    public void SchoolRepository_SaveAll_ReturnSavedSchool() {

        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        //Act
        SchoolEntity entity = schoolRepository.save(schoolEntity);
        //Assert
        Assertions.assertNotNull(schoolRepository.save(schoolEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("GET /schools/all возвращает HTTP-ответ со статусом 200 OK и все lesson entity")
    public void SchoolRepository_FindAll_ReturnListOfSchools() {

        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        SchoolEntity schoolEntity2 = SchoolEntity.builder()
                .name("Средняя школа №2 г. Пинска").build();
        //Act
        schoolRepository.save(schoolEntity);
        schoolRepository.save(schoolEntity2);
        List<SchoolEntity> entities = schoolRepository.findAll();
        //Assert
        Assertions.assertNotNull(schoolRepository.findAll());
        Assertions.assertEquals(entities.size(), 2);
    }

    @Test
    @DisplayName("GET /schools/{id} возвращает HTTP-ответ со статусом 200 OK lesson entity")
    public void SchoolRepository_FindById_ReturnsSchoolEntity() throws ObjectNotFound {
        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        SchoolEntity schoolEntity2 = SchoolEntity.builder()
                .name("Средняя школа №2 г. Пинска").build();
        //Act
        schoolRepository.save(schoolEntity);
        schoolRepository.save(schoolEntity2);
        SchoolEntity school = schoolRepository.findById(schoolEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        //Assert
        Assertions.assertNotNull(schoolRepository.findById(schoolEntity2.getId()));
        Assertions.assertEquals(school.getName(), "Средняя школа №1 г. Волковыска");
    }

    @Test
    @DisplayName("PUT /schools/ возвращает HTTP-ответ со статусом 200 OK и lesson entity")
    public void SchoolRepository_UpdateSchool_ReturnsSchoolEntity() throws ObjectNotFound {
        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        //Act
        schoolRepository.save(schoolEntity);

        SchoolEntity schoolSave = schoolRepository.findById(schoolEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        schoolSave.setName("Средняя школа №2 г. Пинска");

        SchoolEntity schoolSaved = schoolRepository.save(schoolSave);
        //Assert
        Assertions.assertNotNull(schoolSaved);
        Assertions.assertEquals(schoolSave.getName(), "Средняя школа №2 г. Пинска");
    }

    @Test
    @DisplayName("DELETE /schools/{id} возвращает HTTP-ответ со статусом 200 OK и пустую lesson entity")
    public void SchoolRepository_DeleteSchool_ReturnsSchoolIsEmpty() {
        //Arrange
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Волковыска").build();
        //Act
        schoolRepository.save(schoolEntity);
        schoolRepository.deleteById(schoolEntity.getId());
        Optional<SchoolEntity> schoolReturn = schoolRepository.findById(schoolEntity.getId());
        //Assert
        Assertions.assertEquals(schoolReturn, Optional.empty());
    }
}
