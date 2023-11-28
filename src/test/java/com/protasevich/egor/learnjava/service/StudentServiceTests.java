package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.dto.StudentDto;
import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import com.protasevich.egor.learnjava.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private StudentService studentService;
    private StudentDto studentDto1;
    private StudentDto studentDto2;
    private StudentEntity studentEntity1;
    private StudentEntity studentEntity2;
    private SchoolEntity schoolEntity;
    private LessonEntity lessonEntity;
    private List<StudentDto> studentDtos;
    private List<StudentEntity> studentEntities;

    @BeforeEach
    void init() {
        schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Пинска").build();
        studentDto1 = StudentDto.builder()
                .firstname("Егор").lastname("Протасевич")
                .grade(11).schoolId(1L).build();
        studentDto2 = StudentDto.builder()
                .firstname("Илья").lastname("Новик")
                .grade(3).schoolId(1L).build();
        studentEntity1 = StudentEntity.builder()
                .firstname("Егор").lastname("Протасевич")
                .grade(11).school(schoolEntity).build();
        studentEntity2 = StudentEntity.builder()
                .firstname("Илья").lastname("Новик")
                .grade(3).school(schoolEntity).build();
        lessonEntity = LessonEntity.builder()
                .name("Английский язык").build();
        studentDtos = List.of(studentDto1, studentDto2);
        studentEntities = List.of(studentEntity1, studentEntity2);
    }

    @Test
    void StudentServiceTests_studentSave_ReturnsStudentEntity()
            throws ParametersNotSpecified, ObjectNotFound {

        when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(studentEntity1);
        when(schoolRepository.findById(1L))
                .thenReturn(Optional.ofNullable(schoolEntity));

        StudentDto savedStudent = studentService.saveStudent(studentDto1, 1L);

        Assertions.assertNotNull(savedStudent);
    }

    @Test
    void StudentServiceTests_patchStudent_returnsStudentDto()
            throws ParametersNotSpecified, ObjectNotFound {

        if (studentDto1.getFirstname() != null) {
            studentEntity1.setFirstname(studentDto1.getFirstname());
        }
        if (studentDto1.getLastname() != null) {
            studentEntity1.setLastname(studentDto1.getLastname());
        }
        if (studentDto1.getGrade() != 0) {
            studentEntity1.setGrade(studentDto1.getGrade());
        }

        when(studentRepository.findById(1L))
                .thenReturn(Optional.ofNullable(studentEntity1));
        when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(studentEntity1);
        when(schoolRepository.findById(1L))
                .thenReturn(Optional.ofNullable(schoolEntity));

        StudentDto savedStudent = studentService.patchStudent(1L, studentDto1, 1L);

        Assertions.assertNotNull(savedStudent);
    }

    @Test
    void StudentServiceTests_saveMoreStudent_ReturnsListOfStudentDto() throws ObjectNotFound {

        when(lessonRepository.findById(1L))
                .thenReturn(Optional.ofNullable(lessonEntity));
        when(studentRepository.findById(1L))
                .thenReturn(Optional.ofNullable(studentEntity1));
        when(studentRepository.findById(2L))
                .thenReturn(Optional.ofNullable(studentEntity2));

        studentDtos = studentService.saveMoreStudent(1L, List.of(1L, 2L));

        Assertions.assertNotNull(studentDtos);
    }

    @Test
    void StudentServiceTests_saveManyStudent_ReturnsString() throws ParametersNotSpecified {

        when(studentRepository.saveAll(studentEntities))
                .thenReturn(null);

        String response = studentService.saveManyStudents(studentDtos);

        Assertions.assertEquals(response, "Ученики были сохранены успешно!");
    }


    @Test
    void StudentServiceTests_findStudentById_ReturnsStudentDto() throws ObjectNotFound {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(studentEntity1));

        StudentDto savedStudent = studentService.findStudentById(1L);

        Assertions.assertNotNull(savedStudent);
    }

}