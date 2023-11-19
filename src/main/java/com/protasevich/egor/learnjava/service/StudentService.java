package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.dto.CleanSchoolDto;
import com.protasevich.egor.learnjava.dto.StudentDto;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import com.protasevich.egor.learnjava.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final LessonRepository lessonRepository;

    //Save One student
    public StudentDto saveStudent(StudentDto studentDTO, Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        StudentEntity entity = StudentDto.toEntity(studentDTO);
        if (entity.getFirstname() == null ||
            entity.getLastname() == null ||
            entity.getGrade() == 0) throw new ParametersNotSpecified();
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new);
        entity.setSchool(schoolEntity);
        StudentEntity savedStudent = studentRepository.save(entity);
        return StudentDto.toSchoolModel(savedStudent);
    }

    //Patch student
    public StudentDto patchStudent(Long id, StudentDto studentDTO, Long schoolId) throws ObjectNotFound, ParametersNotSpecified {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new);
        studentEntity.setSchool(schoolEntity);
        if (studentDTO == null) throw new ParametersNotSpecified();
        StudentDto student = StudentDto.toSchoolModel(studentEntity);
        if (studentDTO.getFirstname() != null) {
            student.setFirstname(studentDTO.getFirstname());
        }
        if (studentDTO.getLastname() != null) {
            student.setLastname(studentDTO.getLastname());
        }
        if (studentDTO.getGrade() != 0) {
            student.setGrade(studentDTO.getGrade());
        }
        student.setSchoolId(schoolId);
        if (studentDTO.getId() != null) {
            student.setId(studentDTO.getId());
        }
        StudentEntity savedStudent = StudentDto.toEntity(student);
        savedStudent.setSchool(schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new));
        studentRepository.save(savedStudent);
        return student;
    }

    //Save students lesson
    public StudentDto saveMoreStudent(Long lessonId, List<Long> studentIds) throws ObjectNotFound {
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(ObjectNotFound::new);
        for (Long id : studentIds) {
            StudentEntity student = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
            student.getLessons().add(lesson);
            studentRepository.save(student);
        }
        return null;
    }

    //Save many students
    public String saveManyStudents(List<StudentDto> studentDtoList) throws ParametersNotSpecified {
        for (StudentDto studentDTO : studentDtoList) {
            if (studentDTO == null) throw new ParametersNotSpecified();
        }
        studentRepository.saveAll(StudentDto.toListEntity(studentDtoList));
        return "Ученики были сохранены успешно!";
    }

    //Assign student
    public StudentDto assignStudentLesson(Long lessonId, Long studentId) throws ObjectNotFound {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(ObjectNotFound::new);
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(ObjectNotFound::new);
        student.getLessons().add(lesson);
        StudentEntity savedStudent = studentRepository.save(student);
        return StudentDto.toModel(savedStudent);
    }

    //Get One student
    public StudentDto findStudentById(Long id) throws ObjectNotFound {
        StudentEntity student = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        return StudentDto.toModel(student);
    }


    //Find school by student id
    public CleanSchoolDto findSchool(Long id) throws ObjectNotFound {
        StudentEntity entity = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        if (entity.getSchool() == null) throw new ObjectNotFound();
        SchoolEntity schoolEntity = entity.getSchool();
        return CleanSchoolDto.toSchoolModel(schoolEntity);
    }

    //Get all students
    public List<StudentDto> getAllStudents(PageRequest pageRequest) {
        Page<StudentEntity> page = studentRepository.findAll(pageRequest);
        return StudentDto.toListModel(page.getContent());
    }

    //Delete student
    public Long deleteStudent(Long id) throws ObjectNotFound {
        studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        studentRepository.deleteById(id);
        return id;
    }

    //Find student
    public StudentDto findStudent(String firstname, String lastname, String schoolName, int grade) throws ObjectNotFound {
        StudentEntity studentEntity = studentRepository.currentFindStudent(firstname, lastname, schoolName, grade);
        if (studentEntity == null) throw new ObjectNotFound();
        return StudentDto.toModel(studentRepository
                .findById(studentEntity.getId()).orElseThrow(ObjectNotFound::new));
    }

    //Find student V2
    public StudentDto findStudentV2(String firstname, String lastname, int grade, String schoolName) throws ObjectNotFound {
        StudentEntity studentEntity = studentRepository.findStudentByFirstnameAndLastnameAndGrade(firstname, lastname, grade, schoolName);
        if (studentEntity == null) throw new ObjectNotFound();
        return StudentDto.toModel(studentRepository
                .findById(studentEntity.getId()).orElseThrow(ObjectNotFound::new));
    }

}
