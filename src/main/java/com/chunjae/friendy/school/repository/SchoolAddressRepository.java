package com.chunjae.friendy.school.repository;

import com.chunjae.friendy.school.entity.SchoolAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SchoolAddressRepository extends JpaRepository<SchoolAddress, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE school_address", nativeQuery = true)
    void truncateTable();

    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void unsetForeignKeyCheck();
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void setForeignKeyCheck();

    @Query(value = "SELECT * FROM school_address " +
            "WHERE longitude < :maxLongitude AND longitude > :minLongitude " +
            "AND latitude < :maxLatitude AND latitude > :minLatitude", nativeQuery = true)

    List<SchoolAddress> findSchoolsInRadius(
            @Param("maxLongitude") double maxLongitude,
            @Param("minLongitude") double minLongitude,
            @Param("maxLatitude") double maxLatitude,
            @Param("minLatitude") double minLatitude
    );

    List<SchoolAddress> findByLatitudeBetweenAndLongitudeBetween(
            double minLatitude, double maxLatitude,
            double minLongitude, double maxLongitude
    );
}
