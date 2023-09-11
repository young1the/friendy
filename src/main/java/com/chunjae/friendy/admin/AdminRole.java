package com.chunjae.friendy.admin;

import jakarta.persistence.*;

import java.time.LocalDate;

public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 10)
    private String category;

}
