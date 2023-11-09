package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.repository.LessonRepository;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import com.protasevich.egor.learnjava.repository.StudentRepository;
import com.protasevich.egor.learnjava.model.SchoolWithoutStudentModel;
import com.protasevich.egor.learnjava.model.StudentModel;
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
    public StudentModel saveStudent(StudentModel studentModel, Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        StudentEntity entity = StudentModel.toEntity(studentModel);
        if (entity.getFirstname() == null ||
            entity.getLastname() == null ||
            entity.getGrade() == 0) throw new ParametersNotSpecified();
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new);
        entity.setSchool(schoolEntity);
        StudentEntity savedStudent = studentRepository.save(entity);
        return StudentModel.toSchoolModel(savedStudent);
    }

    //Patch student
    public StudentModel patchStudent(Long id, StudentModel studentModel, Long schoolId) throws ObjectNotFound, ParametersNotSpecified {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new);
        studentEntity.setSchool(schoolEntity);
        if (studentModel == null) throw new ParametersNotSpecified();
        StudentModel student = StudentModel.toSchoolModel(studentEntity);
        if (studentModel.getFirstname() != null) {
            student.setFirstname(studentModel.getFirstname());
        }
        if (studentModel.getLastname() != null) {
            student.setLastname(studentModel.getLastname());
        }
        if (studentModel.getGrade() != 0) {
            student.setGrade(studentModel.getGrade());
        }
        student.setSchoolId(schoolId);
        if (studentModel.getId() != null) {
            student.setId(studentModel.getId());
        }
        StudentEntity savedStudent = StudentModel.toEntity(student);
        savedStudent.setSchool(schoolRepository.findById(schoolId).orElseThrow(ObjectNotFound::new));
        studentRepository.save(savedStudent);
        return student;
    }

    //Save students lesson
    public StudentModel saveMoreStudent(Long lessonId, List<Long> studentIds) throws ObjectNotFound {
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(ObjectNotFound::new);
        for (Long id : studentIds) {
            StudentEntity student = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
            student.getLessons().add(lesson);
            studentRepository.save(student);
        }
        return null;
    }

    //Save many students
    public String saveManyStudents(List<StudentModel> studentModelList) throws ParametersNotSpecified {
        for (StudentModel studentModel : studentModelList) {
            if (studentModel == null) throw new ParametersNotSpecified();
        }
        studentRepository.saveAll(StudentModel.toListEntity(studentModelList));
        return "Ученики были сохранены успешно!";
    }

    //Assign student
    public StudentModel assignStudentLesson(Long lessonId, Long studentId) throws ObjectNotFound {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(ObjectNotFound::new);
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(ObjectNotFound::new);
        student.getLessons().add(lesson);
        StudentEntity savedStudent = studentRepository.save(student);
        return StudentModel.toModel(savedStudent);
    }

    //Get One student
    public StudentModel getOneStudent(Long id) throws ObjectNotFound {
        StudentEntity student = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        return StudentModel.toModel(student);
    }


    //Find school by student id
    public SchoolWithoutStudentModel findSchool(Long id) throws ObjectNotFound {
        StudentEntity entity = studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        if (entity.getSchool() == null) throw new ObjectNotFound();
        SchoolEntity schoolEntity = entity.getSchool();
        return SchoolWithoutStudentModel.toSchoolModel(schoolEntity);
    }

    //Get all students
    public List<StudentModel> getAllStudents(PageRequest pageRequest) {
        Page<StudentEntity> page = studentRepository.findAll(pageRequest);
        return StudentModel.toListModel(page.getContent());
    }

    //Delete student
    public Long deleteStudent(Long id) throws ObjectNotFound {
        studentRepository.findById(id).orElseThrow(ObjectNotFound::new);
        studentRepository.deleteById(id);
        return id;
    }

    //Find student
    public StudentModel findStudent(String firstname, String lastname, String schoolName, int grade) throws ObjectNotFound {
        StudentEntity studentEntity = studentRepository.currentFindStudent(firstname, lastname, schoolName, grade);
        if (studentEntity == null) throw new ObjectNotFound();
        return StudentModel.toModel(studentRepository
                .findById(studentEntity.getId()).orElseThrow(ObjectNotFound::new));
    }

    //Find student V2
    public StudentModel findStudentV2(String firstname, String lastname, int grade, String schoolName) throws ObjectNotFound {
        StudentEntity studentEntity = studentRepository.findStudentByFirstnameAndLastnameAndGrade(firstname, lastname, grade, schoolName);
        if (studentEntity == null) throw new ObjectNotFound();
        return StudentModel.toModel(studentRepository
                .findById(studentEntity.getId()).orElseThrow(ObjectNotFound::new));
    }

}
