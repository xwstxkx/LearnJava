package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import com.protasevich.egor.learnjava.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager em;

    @Override
    public StudentEntity findStudentByFirstnameAndLastnameAndGrade(String firstname, String lastname,
                                                                   int grade, String schoolName) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<StudentEntity> cq = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> root = cq.from(StudentEntity.class);
        Join<StudentEntity, SchoolEntity> schoolsJoin = root.join("school");
        schoolsJoin.on(cb.and(cb.equal(root.get("school").get("id"), schoolsJoin.get("id")), cb.equal(schoolsJoin.get("name"), schoolName)));

        Predicate firstnamePredicate = cb.equal(root.get("firstname"), firstname);
        Predicate lastnamePredicate = cb.equal(root.get("lastname"), lastname);
        Predicate gradePredicate = cb.equal(root.get("grade"), grade);

        cq.where(firstnamePredicate, lastnamePredicate, gradePredicate);

        TypedQuery<StudentEntity> query = em.createQuery(cq);
        return query.getSingleResult();
    }
//    SELECT st.id, st.firstname, st.lastname, st.grade, st.school_id" +
//            " FROM students st JOIN schools sc" +
//            " on sc.id = st.school_id and sc.name = :schoolName" +
//            " WHERE st.firstname = :firstname" +
//            " and st.lastname = :lastname " +
//            " and  st.grade = :grade"
}
