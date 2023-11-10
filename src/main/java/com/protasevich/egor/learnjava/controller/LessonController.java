package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.model.LessonModel;
import com.protasevich.egor.learnjava.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "Сохранение одного урока")
    public ResponseEntity<String> lessonSave(@RequestBody LessonModel lessonModel) throws ParametersNotSpecified {
        return ResponseEntity.ok(lessonService.lessonSave(lessonModel));
    }

    @PostMapping("/all")
    @Operation(summary = "Сохранение многих уроков")
    public ResponseEntity<SchoolEntity> saveManyLessons(@RequestBody List<LessonModel> lessonModelList) throws ParametersNotSpecified {
        return ResponseEntity.ok(lessonService.saveManyLessons(lessonModelList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одного урока")
    public ResponseEntity<LessonModel> getOneLesson(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok(lessonService.getOneLesson(id));
    }

    @PutMapping
    @Operation(summary = "Обновление одного урока")
    public ResponseEntity<String> updateLesson(@RequestBody LessonModel lessonModel) throws ParametersNotSpecified {
        return ResponseEntity.ok(lessonService.lessonSave(lessonModel));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех урока")
    public ResponseEntity<List<LessonModel>> getAllLessons(@RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(lessonService.getAllLessons(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одного урока")
    public ResponseEntity<Long> deleteLesson(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok(lessonService.deleteLesson(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Патч одного урока")
    public ResponseEntity<String> patchLesson(@PathVariable Long id,
                                              @RequestBody LessonModel lessonModel) throws ParametersNotSpecified {
        return ResponseEntity.ok(lessonService.patchLesson(id, lessonModel));
    }
}
