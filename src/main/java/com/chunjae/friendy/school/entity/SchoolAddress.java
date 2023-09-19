package com.chunjae.friendy.school.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (name = "school_address")
public class SchoolAddress {

    @Id
    @Column(name = "school_idx")
    private Long school_idx;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @MapsId
    @JoinColumn(name = "school_idx")
    private School school;

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

}
