package com.chunjae.friendy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiddleSearchResponse {

    private String schoolId;
    private String sdScCode;
    private String scName;
    private String scGradeCode;
    private String mooeCode;
    private String ofoesCode;
    private String establishType;
    private String dnType;
    private String region;
    private String rnAddress;
    private String rnDetailAddress;
    private String rnPostalCode;
    private String latitude;
    private String longitude;
    private String telNum;
    private String faxNum;
    private String hmpgAddress;

}
