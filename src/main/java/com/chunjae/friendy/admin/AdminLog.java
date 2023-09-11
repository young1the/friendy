package com.chunjae.friendy.admin;

import com.chunjae.friendy.admin.Admin;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "admin_log")
public class AdminLog {

        @Id
        @ManyToOne
        @JoinColumn(name = "admin")
        private Admin admin_idx;

        @Column(nullable = false)
        private LocalDate access_date;

        @Column(nullable = false, length = 15)
        private String ipv4;

        @Column(nullable = false, length = 39)
        private String ipv6;

}
