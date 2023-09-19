package com.chunjae.friendy.school.repository;

import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.user.dto.UserSearchResponseInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT sa FROM school_address sa inner join school s on s.idx = sa.school_idx WHERE (sa.roadAddress LIKE %:searchCity% or sa.roadAddressDetail LIKE %:searchCity%) and s.deletedYn='N' ")
    Page<SchoolAddress> findBySearchCityUsingJoin(@Param("searchCity") String searchCity, Pageable pageable);

    @Query(value = "SELECT sa FROM school_address sa inner join school s on s.idx = sa.school_idx WHERE (sa.roadAddress LIKE %:searchKeyword% or sa.roadAddressDetail like %:searchKeyword%) and s.deletedYn='N'")
    Page<SchoolAddress> findDistrictBySearchKeyword(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT s.idx as idx, s.name as name, a.road_address as roadAddress, a.road_zip_code as roadZipCode, a.latitude as latitude, a.longitude as longitude, s.level_code as levelCode, s.school_code as schoolCode FROM school s inner JOIN school_address a on s.idx = a.school_idx WHERE s.name LIKE %:keyword% and s.deleted_yn='N'", nativeQuery = true)
    List<UserSearchResponseInterface> findSchoolNamesByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT sa FROM school_address sa inner join school s on s.idx = sa.school_idx WHERE (sa.roadAddress LIKE %:district% or sa.roadAddressDetail LIKE %:district%) and s.deletedYn='N'")
    Page<SchoolAddress> findByDistrictUsingJoin(@Param("district")String district, Pageable pageable);

    @Query(value = "SELECT sa FROM school s join school_address sa on s.idx = sa.school_idx WHERE  s.district LIKE %:district% and (sa.roadAddress like %:searchKeyword% or sa.roadAddressDetail like %:searchKeyword%) and s.deletedYn='N'")
    Page<SchoolAddress> findByDistrictAndSearchKeywordUsingJoin(@Param("district") String district, @Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT sa FROM school_address sa join school s on s.idx = sa.school_idx WHERE s.district LIKE %:searchCity% and (sa.roadAddress LIKE %:searchKeyword or sa.roadAddressDetail LIKE %:searchKeyword%) and s.deletedYn='N'")
    Page<SchoolAddress> findBySearchCityAndSearchKeywordUsingJoin(@Param("searchCity") String searchCity, @Param("searchKeyword") String searchKeyword, Pageable pageable);

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
            String minLatitude, String maxLatitude,
            String minLongitude, String maxLongitude
    );
}