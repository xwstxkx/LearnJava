package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.StudentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCustomRepository {

    public StudentEntity findStudentByFirstnameAndLastnameAndGrade(String firstname, String lastname,
                                                                   int grade, String schoolName);
}
