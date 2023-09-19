package com.chunjae.friendy.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomMiddleSearchResponse {

    private String idx;
    private String name;
    private String roadAddress;
    private String roadZipCode;
    private String latitude;
    private String longitude;
    private String levelCode;
    private String schoolCode;

}
