package com.protasevich.egor.learnjava.repository;

import com.protasevich.egor.learnjava.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    SchoolEntity findByName(String name);
}
