package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.service.SchoolService;
import com.protasevich.egor.learnjava.model.SchoolModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
@Tag(name = "Schools")
public class SchoolController {

    private final SchoolService schoolService;


    @PostMapping("/school")
    @Operation(summary = "Сохранение одной школы")
    public ResponseEntity<String> saveSchool(@RequestBody SchoolModel schoolModel) throws ParametersNotSpecified {
        schoolService.saveOne(schoolModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Школа была сохранена!");
    }

    @PutMapping
    @Operation(summary = "Обновление одной школы")
    public ResponseEntity<String> updateSchool(@RequestBody SchoolModel schoolModel) throws ParametersNotSpecified {
        schoolService.saveOne(schoolModel);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Школа была изменена!");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одной школы")
    public ResponseEntity<SchoolModel> getOneSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok()
                .body(schoolService.getOne(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех школ")
    public ResponseEntity<List<SchoolModel>> getAllSchools(@RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size)
            throws ParametersNotSpecified {
        if (page < 0 || size < 1) {
            throw new ParametersNotSpecified();
        }
        return ResponseEntity.ok()
                .body(schoolService.getAllSchools(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одной школы")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity.ok()
                .body("Школа с id : " + schoolService.deleteSchool(id) + " была удалена ");
    }

    @PostMapping("/many")
    @Operation(summary = "Сохранение нескольких школ")
    public ResponseEntity<String> saveManySchools(@RequestBody List<SchoolModel> schoolModelList) throws ParametersNotSpecified {
        schoolService.saveManySchools(schoolModelList);
        return ResponseEntity.ok("Сохранение нескольких школ было успешно завершено");
    }

    @PatchMapping("/patch/{id}")
    @Operation(summary = "Патч одной школы")
    public ResponseEntity<String> patchSchool(@PathVariable Long id,
                                              @RequestBody SchoolModel schoolModel) throws ObjectNotFound {
        schoolService.patchSchool(id, schoolModel);
        return ResponseEntity.ok("Патч был успешно завершён");
    }

    @PostMapping("/school/v2")
    @Operation(summary = "Транзакция одной школы")
    public ResponseEntity<ResponseEntity<List<SchoolModel>>> saveSchoolTransaction(@RequestBody SchoolModel schoolModel,
                                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                                   @RequestParam(required = false, defaultValue = "10") int size) throws ParametersNotSpecified {
        schoolService.transactionSchool(schoolModel, page, size);
        return ResponseEntity.ok(getAllSchools(page, size));
    }
}
