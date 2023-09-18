package com.chunjae.friendy.csv.entity;

import com.chunjae.friendy.admin.entity.Admin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter @Setter
@Entity(name = "csv_file")
public class CSVFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "admin_idx")
    private Admin admin;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private char currentData;

}
