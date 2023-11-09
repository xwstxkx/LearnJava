package com.protasevich.egor.learn.controller;

import com.protasevich.egor.learn.exceptions.ObjectNotFound;
import com.protasevich.egor.learn.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learn.service.StudentService;
import com.protasevich.egor.learn.model.SchoolWithoutStudentModel;
import com.protasevich.egor.learn.model.StudentModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Сохранение одного студента")
    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel studentModel,
                                                      @RequestParam(value = "schoolId") Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity.ok(studentService.saveStudent(studentModel, schoolId));
    }

    @PostMapping("/lessons")
    @Operation(summary = "Сохранение уроков для многих студентов")
    public ResponseEntity<StudentModel> saveMoreStudent(@RequestParam Long lessonId,
                                                        @RequestParam List<Long> studentIds) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.saveMoreStudent(lessonId, studentIds));
    }

    @PostMapping("/lesson")
    @Operation(summary = "Сохранение уроков для одного студента")
    public ResponseEntity<StudentModel> assignStudentLesson(@RequestParam Long lessonId,
                                                            @RequestParam Long studentId) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.assignStudentLesson(lessonId, studentId));
    }

    @PostMapping("/students")
    @Operation(summary = "Сохранение многих студентов")
    public ResponseEntity<String> saveManyStudents(@RequestBody List<StudentModel> studentModelList) throws ParametersNotSpecified {
        return ResponseEntity.ok(studentService.saveManyStudents(studentModelList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одного студента")
    public ResponseEntity<StudentModel> getOneStudent(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.getOneStudent(id));
    }

    @GetMapping("/school/{id}")
    @Operation(summary = "Получение школы студента")
    public ResponseEntity<SchoolWithoutStudentModel> getOneSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.findSchool(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<List<StudentModel>> getAllStudents(@RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getAllStudents(PageRequest.of(page, size)));
    }

    @PutMapping
    @Operation(summary = "Обновление одного студента")
    public ResponseEntity<StudentModel> updateStudent(@RequestBody StudentModel studentModel,
                                                      @RequestParam(value = "schoolId") Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity.ok(studentService.saveStudent(studentModel, schoolId));
    }

    @PatchMapping("/patch/{id}/{schoolId}")
    @Operation(summary = "Патч одного студента")
    public ResponseEntity<StudentModel> patchStudent(@PathVariable Long id,
                                                     @RequestBody StudentModel studentModel,
                                                     @PathVariable(value = "schoolId") Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity.ok(studentService.patchStudent(id, studentModel, schoolId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одного студента")
    public ResponseEntity<Long> deleteStudent(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("/student")
    @Operation(summary = "Поиск одного студента по имени, фамилии, школе и классу")
    public ResponseEntity<StudentModel> findStudent(@RequestParam String firstname, @RequestParam String lastname,
                                                    @RequestParam String schoolName, @RequestParam int grade) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.findStudent(firstname, lastname, schoolName, grade));
    }

    @GetMapping("/studentV2")
    @Operation(summary = "Поиск одного студента по имени, фамилии, школе и классу (Через критерии)")
    public ResponseEntity<StudentModel> findStudentV2(@RequestParam String firstname, @RequestParam String lastname,
                                                      @RequestParam int grade, @RequestParam String schoolName) throws ObjectNotFound {
        return ResponseEntity.ok(studentService.findStudentV2(firstname, lastname, grade, schoolName));
    }
}
