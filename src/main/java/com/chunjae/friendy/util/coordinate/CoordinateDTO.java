package com.chunjae.friendy.util.coordinate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CoordinateDTO {

    private Long idx;
    private String name;
    private String roadAddress;
    private String roadZipCode;
    private double latitude;
    private double longitude;
}
