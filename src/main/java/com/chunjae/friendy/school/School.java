package com.chunjae.friendy.school;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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
   private String schoolCode;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 2)
   private String levelCode;

    @Column(nullable = false, length = 5)
    private String establishment;

    @Column(nullable = false, length = 3)
    private String dayNight;

    @Column(length = 20)
    private String tel;

    @Column(length = 20)
    private String fax;

    @Column
    private String url;

    @Column(nullable = false, length = 5)
    private String gender;

    @Column(nullable = false, length = 2)
    private String deletedYn;


    public void setDeletedYn(String deletedYn) {
        this.deletedYn = deletedYn;
    }
}
