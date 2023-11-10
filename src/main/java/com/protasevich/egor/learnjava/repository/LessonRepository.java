package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
//    Optional<LessonEntity> findByName(String name);
}
