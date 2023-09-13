package com.chunjae.friendy.school;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity (name = "school_address")
public class SchoolAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String roadAddressDetail;

    @Column(nullable = false, length = 6)
    private String roadZipCode;

    @Column(nullable = false, length = 20)
    private String latitude;

    @Column(nullable = false, length = 20)
    private String longitude;

    @Column(nullable = false, length = 11)
    private String boundaryCode;

    // 학교와 주소 간의 일대일 관계 매핑
    @OneToOne
    @JoinColumn(name = "school_idx")
    private School school;

}
