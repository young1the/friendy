package com.chunjae.friendy.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AdminCreateRequest {

    private String username;
    private String password1;
    private String password2;
    private String name;
    private String empNumber;
    private Long adminRole;

}
