package com.chunjae.friendy.school;

import jakarta.persistence.*;

@Entity(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    
    @Column(nullable = false, length = 20)
    private String cityEduOffice;

    @Column(nullable = false, length = 20)
    private String districtEduOffice;
    
    @Column(nullable = false, unique = true, length = 10)
   private String SchoolCode;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 2)
   private String levelCode;

    @Column(nullable = false, length = 5)
    private String establishment;

    @Column(nullable = false)
    private char dayNight;

    @Column(length = 15)
    private String tel;

    @Column(length = 15)
    private String fax;

    @Column
    private String url;

    @Column(nullable = false)
    private char gender;

    @Column(nullable = false, unique = true)
    private char deletedYn;


}
