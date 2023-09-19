package com.chunjae.friendy.school.entity;

import com.chunjae.friendy.admin.entity.Admin;
import com.chunjae.friendy.school.entity.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "school_log")
public class SchoolLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idx;

        @ManyToOne
        @JoinColumn(name = "school_idx")
        private School schoolIdx;

        @ManyToOne
        @JoinColumn(name = "admin_idx")
        private Admin adminIdx;

        @Column
        private LocalDateTime createdAt;

        @Column
        private LocalDateTime modifiedAt;



}
