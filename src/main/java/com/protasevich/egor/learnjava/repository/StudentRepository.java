package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.StudentEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, StudentCustomRepository {

    @Query(value = "SELECT st.id, st.firstname, st.lastname, st.grade, st.school_id" +
                   " FROM school.public.students st JOIN school.public.schools sc" +
                   " on sc.id = st.school_id and sc.name = :schoolName" +
                   " WHERE st.firstname = :firstname" +
                   " and st.lastname = :lastname " +
                   " and  st.grade = :grade", nativeQuery = true)
    StudentEntity currentFindStudent(String firstname, String lastname,
                                     String schoolName, int grade)
            throws ObjectNotFound;

}
