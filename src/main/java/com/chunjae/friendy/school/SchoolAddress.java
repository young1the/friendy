package com.chunjae.friendy.school;

import jakarta.persistence.*;

@Entity (name = "school_address")
public class SchoolAddress {

    @Id
    @OneToOne
    @JoinColumn(name = "school")
    private School schoolIdx;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String roadAddressDetail;

    @Column(nullable = false, length = 6)
    private String roadZipCode;

    @Column(nullable = false, length = 11)
    private String latitude;

    @Column(nullable = false, length = 11)
    private String longitude;

    @Column(nullable = false, length = 11)
    private String boundaryCode;

}
