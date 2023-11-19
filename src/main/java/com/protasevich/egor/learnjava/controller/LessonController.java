package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.dto.LessonDto;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/create")
    @Operation(summary = "Сохранение одного урока")
    public ResponseEntity<LessonDto> lessonSave(@RequestBody LessonDto lessonDTO)
            throws ParametersNotSpecified {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonService.saveLesson(lessonDTO));
    }

    @PostMapping("/all")
    @Operation(summary = "Сохранение многих уроков")
    public ResponseEntity<List<LessonDto>> saveManyLessons(@RequestBody List<LessonDto> lessonDtoList)
            throws ParametersNotSpecified {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonService.saveManyLessons(lessonDtoList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одного урока")
    public ResponseEntity<LessonDto> getOneLesson(@PathVariable Long id)
            throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonService.findLessonById(id));
    }

    @PutMapping
    @Operation(summary = "Обновление одного урока")
    public ResponseEntity<LessonDto> updateLesson(@RequestBody LessonDto lessonDTO)
            throws ParametersNotSpecified {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonService.saveLesson(lessonDTO));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех урока")
    public ResponseEntity<List<LessonDto>> getAllLessons(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonService.getAllLessons(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одного урока")
    public ResponseEntity<Long> deleteLesson(@PathVariable Long id)
            throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonService.deleteLesson(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Патч одного урока")
    public ResponseEntity<LessonDto> patchLesson(@PathVariable Long id,
                                              @RequestBody LessonDto lessonDTO)
            throws ParametersNotSpecified {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonService.patchLesson(id, lessonDTO));
    }
}
