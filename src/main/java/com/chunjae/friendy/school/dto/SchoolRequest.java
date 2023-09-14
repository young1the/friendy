package com.chunjae.friendy.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SchoolRequest {

    private String idx;
    private String schoolCode;
    private String name;
    private String levelCode;
    private String cityEduOffice;
    private String districtEduOffice;
    private String establishment;
    private String dayNight;
    private String roadAddress;
    private String roadAddressDetail;
    private String roadZipCode;
    private String latitude;
    private String longitude;
    private String boundaryCode;
    private String tel;
    private String url;
    private String fax;
    private String gender;
    private String district;

}
