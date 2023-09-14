package com.chunjae.friendy.school.entity;

import com.chunjae.friendy.admin.entity.Admin;
import com.chunjae.friendy.school.entity.School;
import jakarta.persistence.*;

import java.time.LocalDate;

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

        @Column(nullable = false)
        private LocalDate createdAt;

        @Column(nullable = false)
        private LocalDate modifiedAt;

}
