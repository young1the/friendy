package com.chunjae.friendy.csv.repository;

import com.chunjae.friendy.csv.CSVUploaderInfoInterface;
import com.chunjae.friendy.csv.entity.CSVFile;
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

//    @Query(value = "SELECT a.idx as idx, a.name AS name, a.id as id FROM csv_file c INNER JOIN admin a on c.admin_idx = a.idx WHERE a.id = :adminId", nativeQuery = true)
//    CSVUploaderInfoInterface findByAdminId(@Param("adminId") String adminId);
}
