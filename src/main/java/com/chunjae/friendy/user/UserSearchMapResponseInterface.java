package com.chunjae.friendy.user;

import org.springframework.beans.factory.annotation.Value;

public interface UserSearchMapResponseInterface {
    String getSchoolIdx();
    // API 키 주입
    @Value("mo4ez0j3y6")
    String getNaverMapApiKey();
    String getLatitude();
    String getLongitude();
}
