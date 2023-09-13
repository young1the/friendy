package com.chunjae.friendy.admin.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "admin_role")
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 10)
    private String category;

}
