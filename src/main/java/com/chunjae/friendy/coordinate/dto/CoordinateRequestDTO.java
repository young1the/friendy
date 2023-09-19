package com.chunjae.friendy.coordinate.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
public class CoordinateRequestDTO {

    private double latitude;
     private double longitude;
}
