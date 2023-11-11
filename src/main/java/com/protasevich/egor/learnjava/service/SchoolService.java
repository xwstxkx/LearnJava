package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learnjava.dto.SchoolDTO;
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
    public void saveOne(SchoolDTO schoolDTO) throws ParametersNotSpecified {
        if (schoolDTO.getName() == null) throw new ParametersNotSpecified();
        schoolRepository.save(SchoolDTO.toEntity(schoolDTO));
    }

    public void patchSchool(Long id, SchoolDTO schoolDTO) throws ObjectNotFound {
        if (id == null) throw new ObjectNotFound();
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        SchoolDTO school = SchoolDTO.toModel(schoolEntity);
        if (schoolDTO.getName() != null) {
            school.setName(schoolDTO.getName());
        }
        SchoolEntity savedSchool = SchoolDTO.toEntity(school);
        schoolRepository.save(savedSchool);
    }

    //Save many schools
    public void saveManySchools(List<SchoolDTO> schoolDTOList) throws ParametersNotSpecified {
        for (SchoolDTO schoolDTO : schoolDTOList) {
            if (schoolDTO.getName() == null) {
                throw new ParametersNotSpecified();
            }
        }
        schoolRepository.saveAll(SchoolDTO.toListEntity(schoolDTOList));
    }


    //Get one school
    public SchoolDTO getOne(Long id) throws ObjectNotFound {
        SchoolEntity school = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        return SchoolDTO.toModel(school);
    }

    //Get all schools
    public List<SchoolDTO> getAllSchools(PageRequest pageRequest) {
        Page<SchoolEntity> page = schoolRepository.findAll(pageRequest);
        return SchoolDTO.toListModel(page.getContent());
    }

    //Delete school
    public Long deleteSchool(Long id) throws ObjectNotFound {
        schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        schoolRepository.deleteById(id);
        return id;
    }

    //School transaction
    @Transactional
    public void transactionSchool(SchoolDTO schoolDTO, int page, int size) throws ParametersNotSpecified {
        saveOne(schoolDTO);
        getAllSchools(PageRequest.of(page, size));
    }
}
