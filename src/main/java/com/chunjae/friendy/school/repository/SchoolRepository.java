package com.chunjae.friendy.school.repository;

import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.search.AddressDTO;
import com.chunjae.friendy.search.SearchSchoolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "SELECT s FROM school s WHERE s.name LIKE %:searchKeyword% and s.deletedYn='N'")
    Page<School> findByName(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    School findByIdx(Long idx);


    @Query(value = "SELECT s FROM school s WHERE (s.district LIKE %:findBySearchKeyword% OR s.name LIKE %:findBySearchKeyword%) and s.deletedYn='N'")
    Page<School> findBySearchKeyword(@Param("findBySearchKeyword")String findBySearchKeyword, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:district% and s.deletedYn='N'")
    Page<School> findByDistrict(@Param("district")String district, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:searchCity% and s.deletedYn='N'")
    Page<School> findBySearchCity(@Param("searchCity")String searchCity, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:district% AND s.name LIKE %:searchKeyword% and s.deletedYn='N'")
    Page<School> findByDistrictAndName(@Param("district")String district, @Param("searchKeyword")String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:searchCity% and (s.district LIKE %:searchKeyword% or s.name like %:searchKeyword%) and s.deletedYn='N'")
    Page<School> findBySearchCityAndSearchKeyword(@Param("searchCity") String searchCity, @Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:district% and (s.district LIKE %:searchKeyword% or s.name like %:searchKeyword%) and s.deletedYn='N'")
    Page<School> findByDistrictAndSearchKeyword(@Param("district") String district, @Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s FROM school s WHERE s.district LIKE %:searchCity% and s.name like %:searchKeyword% and s.deletedYn='N'")
    Page<School> findBySearchCityAndName(@Param("searchCity") String searchCity, @Param("searchKeyword") String searchKeyword, Pageable pageable);





}
