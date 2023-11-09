package com.protasevich.egor.learn.repository;

import com.protasevich.egor.learn.entity.StudentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCustomRepository {

    public StudentEntity findStudentByFirstnameAndLastnameAndGrade(String firstname, String lastname,
                                                                   int grade, String schoolName);
}
