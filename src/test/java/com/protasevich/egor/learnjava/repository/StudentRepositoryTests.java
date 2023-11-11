package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.StudentEntity;
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
class StudentRepositoryTests {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryTests(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Test
    @DisplayName("POST /schools/ возвращает HTTP-ответ со статусом 200 OK и school entity")
    public void StudentRepository_SaveStudent_ReturnSavedStudent() {

        //Arrange
        StudentEntity studentEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        //Act
        StudentEntity entity = studentRepository.save(studentEntity);
        //Assert
        Assertions.assertNotNull(studentRepository.save(studentEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("POST /schools/all возвращает HTTP-ответ со статусом 200 OK и lesson entities")
    public void StudentRepository_SaveAll_ReturnSavedStudent() {

        //Arrange
        StudentEntity studentEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        //Act
        StudentEntity entity = studentRepository.save(studentEntity);
        //Assert
        Assertions.assertNotNull(studentRepository.save(studentEntity));
        Assertions.assertNotEquals(entity.getId(), 0);
    }

    @Test
    @DisplayName("GET /schools/all возвращает HTTP-ответ со статусом 200 OK и все lesson entity")
    public void StudentRepository_FindAll_ReturnListOfStudents() {

        //Arrange
        StudentEntity studentEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        StudentEntity studentEntity2 = StudentEntity.builder()
                .firstname("Илья")
                .lastname("Новик")
                .grade(15)
                .build();
        //Act
        studentRepository.save(studentEntity);
        studentRepository.save(studentEntity2);
        List<StudentEntity> entities = studentRepository.findAll();
        //Assert
        Assertions.assertNotNull(studentRepository.findAll());
        Assertions.assertEquals(entities.size(), 2);
    }

    @Test
    @DisplayName("GET /schools/{id} возвращает HTTP-ответ со статусом 200 OK lesson entity")
    public void StudentRepository_FindById_ReturnsStudentEntity() throws ObjectNotFound {
        //Arrange
        StudentEntity studentEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        StudentEntity studentEntity2 = StudentEntity.builder()
                .firstname("Илья")
                .lastname("Новик")
                .grade(15)
                .build();
        //Act
        studentRepository.save(studentEntity);
        studentRepository.save(studentEntity2);
        StudentEntity student = studentRepository.findById(studentEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        //Assert
        Assertions.assertNotNull(studentRepository.findById(studentEntity2.getId()));
        Assertions.assertEquals(student.getFirstname(), "Егор");
        Assertions.assertEquals(student.getLastname(), "Протасевич");
        Assertions.assertEquals(student.getGrade(), 11);
    }

    @Test
    @DisplayName("PUT /schools/ возвращает HTTP-ответ со статусом 200 OK и lesson entity")
    public void StudentRepository_UpdateStudent_ReturnsStudentEntity() throws ObjectNotFound {
        //Arrange
        StudentEntity schoolEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        //Act
        studentRepository.save(schoolEntity);

        StudentEntity studentSave = studentRepository.findById(schoolEntity.getId())
                .orElseThrow(ObjectNotFound::new);
        studentSave.setFirstname("Егор");
        studentSave.setLastname("Протасевич");
        studentSave.setGrade(11);

        StudentEntity schoolSaved = studentRepository.save(studentSave);
        //Assert
        Assertions.assertNotNull(schoolSaved);
        Assertions.assertEquals(schoolSaved.getFirstname(), "Егор");
        Assertions.assertEquals(schoolSaved.getLastname(), "Протасевич");
        Assertions.assertEquals(schoolSaved.getGrade(), 11);
    }

    @Test
    @DisplayName("DELETE /schools/{id} возвращает HTTP-ответ со статусом 200 OK и пустую lesson entity")
    public void StudentRepository_DeleteStudent_ReturnsStudentIsEmpty() {
        //Arrange
        StudentEntity studentEntity = StudentEntity.builder()
                .firstname("Егор")
                .lastname("Протасевич")
                .grade(11)
                .build();
        //Act
        studentRepository.save(studentEntity);
        studentRepository.deleteById(studentEntity.getId());
        Optional<StudentEntity> studentReturn = studentRepository.findById(studentEntity.getId());
        //Assert
        Assertions.assertEquals(studentReturn, Optional.empty());
    }
}