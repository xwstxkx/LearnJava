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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private StudentService studentService;
    private StudentDto studentDto;
    private StudentEntity studentEntity;
    private SchoolEntity schoolEntity;

    @BeforeEach
    void init() {
        schoolEntity = SchoolEntity.builder()
                .name("Средняя школа №1 г. Пинска").build();
        studentDto = StudentDto.builder()
                .firstname("Егор").lastname("Протасевич")
                .grade(11).schoolId(1L).build();
        studentEntity = StudentEntity.builder()
                .firstname("Егор").lastname("Протасевич")
                .grade(11).school(schoolEntity).build();
    }

    @Test
    void StudentServiceTests_studentSave_ReturnsStudentEntity()
            throws ParametersNotSpecified, ObjectNotFound {

        when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(studentEntity);
        when(schoolRepository.findById(1L))
                .thenReturn(Optional.ofNullable(schoolEntity));

        StudentDto savedStudent = studentService.saveStudent(studentDto, 1L);

        Assertions.assertNotNull(savedStudent);
    }

    @Test
    void StudentServiceTests_patchStudent_returnsStudentDto()
            throws ParametersNotSpecified, ObjectNotFound {

        if (studentDto.getFirstname() != null) {
            studentEntity.setFirstname(studentDto.getFirstname());
        }
        if (studentDto.getLastname() != null) {
            studentEntity.setLastname(studentDto.getLastname());
        }
        if (studentDto.getGrade() != 0) {
            studentEntity.setGrade(studentDto.getGrade());
        }

        when(studentRepository.findById(1L))
                .thenReturn(Optional.ofNullable(studentEntity));
        when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(studentEntity);
        when(schoolRepository.findById(1L))
                .thenReturn(Optional.ofNullable(schoolEntity));

        StudentDto savedStudent = studentService.patchStudent(1L, studentDto, 1L);

        Assertions.assertNotNull(savedStudent);
    }

    @Test
    void saveMoreStudent() {
    }

    @Test
    void saveManyStudents() {
    }

    @Test
    void assignStudentLesson() {
    }

    @Test
    void getOneStudent() {
    }

    @Test
    void StudentServiceTests_findStudentById_ReturnsStudentDto() throws ObjectNotFound {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(studentEntity));

        StudentDto savedStudent = studentService.findStudentById(1L);

        Assertions.assertNotNull(savedStudent);
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void findStudent() {
    }

    @Test
    void findStudentV2() {
    }
}