package com.chunjae.friendy.school.repository;

import com.chunjae.friendy.school.entity.SchoolAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.chunjae.friendy.user.UserSearchResponseInterface;
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

    @Query(value = "SELECT s FROM school_address s WHERE s.roadAddress LIKE %:district% or s.roadAddressDetail LIKE %:district%")
    Page<SchoolAddress> findByDistrict(@Param("district") String district, Pageable pageable);

    @Query(value = "SELECT s FROM school_address s WHERE s.roadAddress LIKE %:searchCity% or s.roadAddressDetail LIKE %:searchCity%")
    Page<SchoolAddress> findBySearchCity(@Param("searchCity") String searchCity, Pageable pageable);

    @Query(value = "SELECT s FROM school_address s WHERE (s.roadAddress LIKE %:district% or s.roadAddressDetail LIKE %:district%) or (s.roadAddress LIKE %:searchKeyword or s.roadAddressDetail LIKE %:searchKeyword%)")
    Page<SchoolAddress> findByDistrictAndSearchKeyword(@Param("district") String district, @Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s FROM school_address s WHERE s.roadAddress LIKE %:searchCity% and (s.roadAddress LIKE %:searchKeyword or s.roadAddressDetail LIKE %:searchKeyword%)")
    Page<SchoolAddress> findBySearchCityAndSearchKeyword(@Param("searchCity") String searchCity, @Param("searchKeyword") String searchKeyword, Pageable pageable);


    @Query(value = "SELECT s FROM school_address s WHERE s.roadAddress LIKE %:searchKeyword% or s.roadAddressDetail like %:searchKeyword%")
    Page<SchoolAddress> findDistrictBySearchKeyword(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s.idx as idx, s.name as name, a.road_address as roadAddress, a.road_zip_code as roadZipCode, a.latitude as latitude, a.longitude as longitude, s.level_code as levelCode, s.school_code as schoolCode FROM school s inner JOIN school_address a on s.idx = a.school_idx WHERE s.name LIKE %:keyword%", nativeQuery = true)
    List<UserSearchResponseInterface> findSchoolNamesByKeyword(@Param("keyword") String keyword);

}
