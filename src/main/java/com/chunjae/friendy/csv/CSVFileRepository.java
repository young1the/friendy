package com.chunjae.friendy.csv;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CSVFileRepository extends JpaRepository<CSVFile, Long> {
    List<CSVFile> findAllByOrderByCreatedAtDesc();
    void deleteByFileName(String fileName);

    CSVFile findByFileName(String fileName);
}
