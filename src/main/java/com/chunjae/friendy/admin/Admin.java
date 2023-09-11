package com.chunjae.friendy.admin;

import jakarta.persistence.*;
import com.chunjae.friendy.admin.AdminRole;

@Entity (name = "admin")
public class Admin {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idx;

        @ManyToOne
        @JoinColumn(name = "admin_role")
        private AdminRole adminRoleIndex;


        @Column(nullable = false, length = 20)
        private String id;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, length = 20)
        private String name;

        @Column(nullable = false, length = 10)
        private String empNumber;

}
