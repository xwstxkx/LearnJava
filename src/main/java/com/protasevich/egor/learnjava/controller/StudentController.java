package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.dto.SchoolWithoutStudentDTO;
import com.protasevich.egor.learnjava.dto.StudentDTO;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<StudentDTO> createStudent
            (@RequestBody StudentDTO studentDTO,
             @RequestParam(value = "schoolId") Long schoolId)
            throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.saveStudent(studentDTO, schoolId));
    }

    @PostMapping("/lessons")
    @Operation(summary = "Сохранение уроков для многих студентов")
    public ResponseEntity<StudentDTO> saveMoreStudent(@RequestParam Long lessonId,
                                                      @RequestParam List<Long> studentIds) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.saveMoreStudent(lessonId, studentIds));
    }

    @PostMapping("/lesson")
    @Operation(summary = "Сохранение уроков для одного студента")
    public ResponseEntity<StudentDTO> assignStudentLesson(@RequestParam Long lessonId,
                                                          @RequestParam Long studentId) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.assignStudentLesson(lessonId, studentId));
    }

    @PostMapping("/students")
    @Operation(summary = "Сохранение многих студентов")
    public ResponseEntity<String> saveManyStudents(@RequestBody List<StudentDTO> studentDTOList) throws ParametersNotSpecified {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.saveManyStudents(studentDTOList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одного студента")
    public ResponseEntity<StudentDTO> getOneStudent(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getOneStudent(id));
    }

    @GetMapping("/school/{id}")
    @Operation(summary = "Получение школы студента")
    public ResponseEntity<SchoolWithoutStudentDTO> getOneSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.findSchool(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getAllStudents(PageRequest.of(page, size)));
    }

    @PutMapping
    @Operation(summary = "Обновление одного студента")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO,
                                                    @RequestParam(value = "schoolId") Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.saveStudent(studentDTO, schoolId));
    }

    @PatchMapping("/patch/{id}/{schoolId}")
    @Operation(summary = "Патч одного студента")
    public ResponseEntity<StudentDTO> patchStudent(@PathVariable Long id,
                                                   @RequestBody StudentDTO studentDTO,
                                                   @PathVariable(value = "schoolId") Long schoolId) throws ParametersNotSpecified, ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.patchStudent(id, studentDTO, schoolId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одного студента")
    public ResponseEntity<Long> deleteStudent(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.deleteStudent(id));
    }

    @GetMapping("/student")
    @Operation(summary = "Поиск одного студента по имени, фамилии, школе и классу")
    public ResponseEntity<StudentDTO> findStudent(@RequestParam String firstname, @RequestParam String lastname,
                                                  @RequestParam String schoolName, @RequestParam int grade) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.findStudent(firstname, lastname, schoolName, grade));
    }

    @GetMapping("/studentV2")
    @Operation(summary = "Поиск одного студента по имени, фамилии, школе и классу (Через критерии)")
    public ResponseEntity<StudentDTO> findStudentV2(@RequestParam String firstname, @RequestParam String lastname,
                                                    @RequestParam int grade, @RequestParam String schoolName) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.findStudentV2(firstname, lastname, grade, schoolName));
    }
}
