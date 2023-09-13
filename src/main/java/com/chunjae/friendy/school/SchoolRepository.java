package com.chunjae.friendy.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s FROM school s WHERE s.name LIKE %:name%")
    List<School> findAllByName(String city, String district, String name);


}
