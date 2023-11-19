package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.dto.SchoolDto;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;


    //Save/update school
    public SchoolDto saveSchool(SchoolDto schoolDto) throws ParametersNotSpecified {
        if (schoolDto.getName() == null) throw new ParametersNotSpecified();
        schoolRepository.save(SchoolDto.toEntity(schoolDto));
        return schoolDto;
    }

    public SchoolDto patchSchool(Long id, SchoolDto schoolDto) throws ObjectNotFound {
        if (id == null) throw new ObjectNotFound();
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        SchoolDto school = SchoolDto.toModel(schoolEntity);
        if (schoolDto.getName() != null) {
            school.setName(schoolDto.getName());
        }
        SchoolEntity savedSchool = SchoolDto.toEntity(school);
        schoolRepository.save(savedSchool);
        return schoolDto;
    }

    //Save many schools
    public List<SchoolDto> saveManySchools(List<SchoolDto> schoolDtoList) throws ParametersNotSpecified {
        for (SchoolDto schoolDTO : schoolDtoList) {
            if (schoolDTO.getName() == null) {
                throw new ParametersNotSpecified();
            }
        }
        schoolRepository.saveAll(SchoolDto.toListEntity(schoolDtoList));
        return schoolDtoList;
    }


    //Get one school
    public SchoolDto findById(Long id) throws ObjectNotFound {
        SchoolEntity school = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        return SchoolDto.toModel(school);
    }

    //Get all schools
    public List<SchoolDto> getAllSchools(PageRequest pageRequest) {
        Page<SchoolEntity> page = schoolRepository.findAll(pageRequest);
        return SchoolDto.toListModel(page.getContent());
    }

    //Delete school
    public Long deleteSchool(Long id) throws ObjectNotFound {
        schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        schoolRepository.deleteById(id);
        return id;
    }

    //School transaction
    @Transactional
    public void transactionSchool(SchoolDto schoolDTO, int page, int size) throws ParametersNotSpecified {
        saveSchool(schoolDTO);
        getAllSchools(PageRequest.of(page, size));
    }
}
