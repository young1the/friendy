package com.chunjae.friendy.csv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CSVFileRepository extends JpaRepository<CSVFile, Long> {
    List<CSVFile> findAllByOrderByCreatedAtDesc();

    void deleteByFileName(String fileName);

    @Modifying(clearAutomatically = true)
    @Query("update csv_file c set c.currentData = :currentData where c.idx = :idx")
    void updateCSVFileCurrentDataByIdx(@Param(value = "idx") Long idx, @Param(value = "currentData") char currentData);

    @Modifying(clearAutomatically = true)
    @Query("update csv_file c set c.currentData = :currentData where c.fileName = :fileName")
    void updateCSVFileCurrentDataByFileName(@Param(value = "fileName") String fileName, @Param(value = "currentData") char currentData);


    CSVFile findByCurrentDataEquals(char currentData);

    CSVFile findByFileName(String fileName);
}
