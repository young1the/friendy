package com.chunjae.friendy.admin;

import jakarta.persistence.*;

@Entity (name = "admin")
public class Admin {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idx;

        // role

        @Column(nullable = false, length = 20)
        private String id;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, length = 20)
        private String name;

        @Column(nullable = false, length = 10)
        private String empNumber;

}
