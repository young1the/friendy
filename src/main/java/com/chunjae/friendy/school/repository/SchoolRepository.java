package com.chunjae.friendy.school.repository;

import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.util.coordinate.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE school", nativeQuery = true)
    void truncateTable();

    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void unsetForeignKeyCheck();
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void setForeignKeyCheck();

    School findByIdx(Long idx);

    @Query("SELECT c FROM Coordinate c WHERE FUNCTION('ST_DISTANCE', FUNCTION('POINT', c.latitude, c.longitude), FUNCTION('POINT', :latitude, :longitude)) * 1000 <= :radius")
    List<Coordinate> findSchoolsInRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);

}
