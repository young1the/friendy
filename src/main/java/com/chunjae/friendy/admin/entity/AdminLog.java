package com.chunjae.friendy.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "admin_log")
public class AdminLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long logIdx;

        @Column(nullable = false)
        private String userName;

        @Column(nullable = false)
        private LocalDateTime accessDate;

        @Column(nullable = false, length = 50)
        private String ipAddress;


}
