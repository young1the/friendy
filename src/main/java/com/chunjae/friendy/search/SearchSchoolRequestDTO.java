package com.chunjae.friendy.search;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchSchoolRequestDTO {
    private String searchCity;
    private String searchDistrict;
    private String searchOption;
    private String searchKeyword;

}
