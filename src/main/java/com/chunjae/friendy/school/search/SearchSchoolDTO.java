package com.chunjae.friendy.school.search;


import com.chunjae.friendy.school.School;
import com.chunjae.friendy.school.SchoolAddress;
import groovy.util.logging.Slf4j;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Slf4j
public class SearchSchoolDTO {

    private String cityEduOffice;

    private String districtEduOffice;

    private String schoolCode;

    private String name;

    private String levelCode;

    private String establishment;

    private char dayNight;

    private String tel;

    private String fax;

    private String url;

    private char gender;

    private char deletedYn;

    private String boundaryCode;

    private String latitude;

    private String longitude;

    private String roadAddress;

    private String roadAddressDetail;

    private String roadZipCode;

}
