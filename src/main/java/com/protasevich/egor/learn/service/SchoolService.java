package com.protasevich.egor.learn.service;

import com.protasevich.egor.learn.entity.SchoolEntity;
import com.protasevich.egor.learn.exceptions.ObjectNotFound;
import com.protasevich.egor.learn.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learn.repository.SchoolRepository;
import com.protasevich.egor.learn.model.SchoolModel;
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
    public SchoolEntity saveOne(SchoolModel schoolModel) throws ParametersNotSpecified {
        if (schoolModel.getName() == null) throw new ParametersNotSpecified();
        return schoolRepository.save(SchoolModel.toEntity(schoolModel));
    }

    public SchoolModel patchSchool(Long id, SchoolModel schoolModel) throws ObjectNotFound {
        if (id == null) throw new ObjectNotFound();
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        SchoolModel school = SchoolModel.toModel(schoolEntity);
        if (schoolModel.getName() != null) {
            school.setName(schoolModel.getName());
        }
        SchoolEntity savedSchool = SchoolModel.toEntity(school);
        schoolRepository.save(savedSchool);
        return school;
    }

    //Save many schools
    public SchoolEntity saveManySchools(List<SchoolModel> schoolModelList) throws ParametersNotSpecified {
        for (SchoolModel schoolModel : schoolModelList) {
            if (schoolModel.getName() == null) {
                throw new ParametersNotSpecified();
            }
        }
        schoolRepository.saveAll(SchoolModel.toListEntity(schoolModelList));
        return null;
    }


    //Get one school
    public SchoolModel getOne(Long id) throws ObjectNotFound {
        SchoolEntity school = schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        return SchoolModel.toModel(school);
    }

    //Get all schools
    public List<SchoolModel> getAllSchools(PageRequest pageRequest) {
        Page<SchoolEntity> page = schoolRepository.findAll(pageRequest);
        return SchoolModel.toListModel(page.getContent());
    }

    //Delete school
    public Long deleteSchool(Long id) throws ObjectNotFound {
        schoolRepository.findById(id).orElseThrow(ObjectNotFound::new);
        schoolRepository.deleteById(id);
        return id;
    }

    //School transaction
    @Transactional
    public void transactionSchool(SchoolModel schoolModel, int page, int size) throws ParametersNotSpecified {
        saveOne(schoolModel);
        getAllSchools(PageRequest.of(page, size));
    }
}
