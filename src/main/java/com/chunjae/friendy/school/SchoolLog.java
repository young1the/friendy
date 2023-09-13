package com.chunjae.friendy.school;

import com.chunjae.friendy.admin.entity.Admin;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "school_log")
public class SchoolLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idx;

        @ManyToOne
        @JoinColumn(name = "school")
        private School schoolIdx;

        @ManyToOne
        @JoinColumn(name = "admin")
        private Admin adminIdx;

        @Column(nullable = false)
        private LocalDate createdAt;

        @Column(nullable = false)
        private LocalDate modifiedAt;

}
