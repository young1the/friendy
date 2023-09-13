package com.chunjae.friendy.school;

import groovy.util.logging.Slf4j;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Slf4j
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

    @Column(nullable = false)
    private char deletedYn;

    // 학교 엔티티와 주소 엔티티 간의 일대일 관계 매핑
    @OneToOne(mappedBy = "school", cascade = CascadeType.ALL)
    private SchoolAddress address;

    @Column(nullable = false)
    private String district;

}
