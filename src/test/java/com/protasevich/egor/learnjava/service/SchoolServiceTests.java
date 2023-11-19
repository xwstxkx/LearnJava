package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.dto.SchoolDto;
import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTests {

    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private SchoolService schoolService;
    private SchoolDto schoolDto;
    private SchoolEntity schoolEntity;

    @BeforeEach
    public void init() {
        schoolDto = SchoolDto.builder().name("Средняя школа №1 г. Пинска").build();
        schoolEntity = SchoolEntity.builder().name("Средняя школа №1 г. Пинска").build();
    }

    @Test
    void SchoolServiceTests_saveSchool_ReturnsVoid() throws ParametersNotSpecified {
        when(schoolRepository.save(Mockito.any(SchoolEntity.class)))
                .thenReturn(schoolEntity);

        SchoolDto savedSchool = schoolService.saveSchool(schoolDto);

        Assertions.assertNotNull(savedSchool);
    }

    @Test
    void SchoolServiceTests_patchSchool_returnsSchoolDto()
            throws ObjectNotFound {
        if (schoolDto.getName() != null) {
            schoolEntity.setName(schoolDto.getName());
        }

        when(schoolRepository.findById(1L))
                .thenReturn(Optional.ofNullable(schoolEntity));
        when(schoolRepository.save(Mockito.any(SchoolEntity.class)))
                .thenReturn(schoolEntity);

        SchoolDto savedSchool = schoolService.patchSchool(1L, schoolDto);

        Assertions.assertNotNull(savedSchool);
    }

    @Test
    void SchoolServiceTests_saveManySchools_ReturnsListOfSchoolDto()
            throws ParametersNotSpecified {

        SchoolDto schoolDto1 = SchoolDto.builder()
                .name("Английский язык").build();
        SchoolDto schoolDto2 = SchoolDto.builder()
                .name("Русский язык").build();

        List<SchoolDto> schoolDtos = new ArrayList<>();
        schoolDtos.add(schoolDto1);
        schoolDtos.add(schoolDto2);

        when(schoolRepository.saveAll(Mockito.anyList())).thenReturn(Collections.singletonList(schoolEntity));

        List<SchoolDto> savedSchool = schoolService.saveManySchools(schoolDtos);

        Assertions.assertNotNull(savedSchool);
        Assertions.assertEquals(savedSchool, List.of(schoolDto1, schoolDto2));
    }

    @Test
    void SchoolServiceTests_findLessonById_ReturnsSchoolDto()
            throws ObjectNotFound {
        when(schoolRepository.findById(1L)).thenReturn(Optional.ofNullable(schoolEntity));

        SchoolDto savedSchool = schoolService.findById(1L);

        Assertions.assertNotNull(savedSchool);
    }

    @Test
    void SchoolServiceTests_getAllSchools_ReturnsSchoolEntities() {
        Page<SchoolEntity> page = Mockito.mock(Page.class);

        when(schoolRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        List<SchoolDto> savedSchools = schoolService
                .getAllSchools(PageRequest.of(1, 10));

        Assertions.assertNotNull(savedSchools);
    }

    @Test
    void SchoolServiceTests_deleteSchool_ReturnsDeletedSchoolId() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.ofNullable(schoolEntity));

        Assertions.assertAll(() -> schoolService.deleteSchool(1L));
    }

    @Test
    void transactionSchool() throws ParametersNotSpecified {
        Page<SchoolEntity> page = Mockito.mock(Page.class);

        when(schoolRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);
        when(schoolRepository.save(Mockito.any(SchoolEntity.class)))
                .thenReturn(schoolEntity);

        List<SchoolDto> savedSchools = schoolService
                .getAllSchools(PageRequest.of(1, 10));
        SchoolDto savedSchool = schoolService.saveSchool(schoolDto);

        Assertions.assertNotNull(savedSchools);
        Assertions.assertNotNull(savedSchool);
    }
}