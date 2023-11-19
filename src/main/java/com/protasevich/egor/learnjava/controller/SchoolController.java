package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.dto.SchoolDto;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.service.SchoolService;
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
    public ResponseEntity<String> saveSchool(@RequestBody SchoolDto schoolDTO) throws ParametersNotSpecified {
        schoolService.saveSchool(schoolDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Школа была сохранена!");
    }

    @PutMapping
    @Operation(summary = "Обновление одной школы")
    public ResponseEntity<String> updateSchool(@RequestBody SchoolDto schoolDTO) throws ParametersNotSpecified {
        schoolService.saveSchool(schoolDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Школа была изменена!");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение одной школы")
    public ResponseEntity<SchoolDto> getOneSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schoolService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех школ")
    public ResponseEntity<List<SchoolDto>> getAllSchools(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size)
            throws ParametersNotSpecified {
        if (page < 0 || size < 1) {
            throw new ParametersNotSpecified();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schoolService.getAllSchools(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление одной школы")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Школа с id : " + schoolService.deleteSchool(id) + " была удалена ");
    }

    @PostMapping("/many")
    @Operation(summary = "Сохранение нескольких школ")
    public ResponseEntity<String> saveManySchools(@RequestBody List<SchoolDto> schoolDtoList) throws ParametersNotSpecified {
        schoolService.saveManySchools(schoolDtoList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Сохранение нескольких школ было успешно завершено");
    }

    @PatchMapping("/patch/{id}")
    @Operation(summary = "Патч одной школы")
    public ResponseEntity<String> patchSchool(@PathVariable Long id,
                                              @RequestBody SchoolDto schoolDTO) throws ObjectNotFound {
        schoolService.patchSchool(id, schoolDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Патч был успешно завершён");
    }

    @PostMapping("/school/v2")
    @Operation(summary = "Транзакция одной школы")
    public ResponseEntity<ResponseEntity<List<SchoolDto>>> saveSchoolTransaction
            (@RequestBody SchoolDto schoolDTO,
             @RequestParam(required = false, defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "10") int size)
            throws ParametersNotSpecified {
        schoolService.transactionSchool(schoolDTO, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getAllSchools(page, size));
    }
}
