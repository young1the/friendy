package com.chunjae.friendy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighSearchResponse {

    private Long idx; //인덱스

    //시도명(소재지명)
    private String cityName;

    //도로명주소
    private String streetAddr;

    //학교급(학교종류명)
    private String schoolKind;

    //학교명
    private String schoolName;

    //시도교육청명
    private String cityEduOrg;

    //지역교육청명
    private String localEduOrg;

    //등록자이름
    private String userName;

}
