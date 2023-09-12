package com.chunjae.friendy.school;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Entity (name = "school_address")
public class SchoolAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name = "school_idx")
    private School schoolIdx;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String roadAddressDetail;

    @Column(nullable = false, length = 6)
    private String roadZipCode;

    @Column(nullable = false, length = 15)
    private String latitude;

    @Column(nullable = false, length = 15)
    private String longitude;

    @Column(nullable = false, length = 11)
    private String boundaryCode;

}
