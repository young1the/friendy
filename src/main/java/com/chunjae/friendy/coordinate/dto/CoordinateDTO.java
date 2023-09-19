package com.chunjae.friendy.coordinate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CoordinateDTO {

    private Long idx;
    private String name;
    private String road_address;
    private String road_zip_code;
    private String latitude;
    private String longitude;
    private String level_code;
}
