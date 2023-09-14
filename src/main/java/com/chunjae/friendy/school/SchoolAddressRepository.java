package com.chunjae.friendy.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SchoolAddressRepository extends JpaRepository<SchoolAddress, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE school_address", nativeQuery = true)
    void truncateTable();

    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void unsetForeignKeyCheck();
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void setForeignKeyCheck();


}
