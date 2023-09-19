package com.chunjae.friendy.school;

import com.chunjae.friendy.school.dto.WeatherResponseDTO;
import com.chunjae.friendy.school.entity.School;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("school")
public class SchoolController {

    private final SchoolService schoolService;
    private final String weatherApiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst"; // 기상청 API URL


    // API 키 주입
    @Value("${naver.map.api.clientId}")
    private String naverMapApiKey;

    // 학교 정보 상세 조회 (admin)
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam long idx) {

        //학교의 세부 정보를 가져오기
        School school = schoolService.detailSchool(idx);
        model.addAttribute("school", school);

        //학교 위치 가져오기
        Map<String, Object> location = schoolService.locationSchool(idx);
        model.addAttribute("location", location);

        // API 키 전달
        model.addAttribute("naverMapApiKey", naverMapApiKey);

        return "admin/pages/schoolDetail";
    }


    // 학교 정보 상세 조회 (user)
    @GetMapping("/user/detail")
    public String userDetail(Model model, @RequestParam long idx) {

        //학교의 세부 정보를 가져오기
        School school = schoolService.detailSchool(idx);
        model.addAttribute("school", school);

        //학교 위치 가져오기
        Map<String, Object> location = schoolService.locationSchool(idx);
        model.addAttribute("location", location);

        // API 키 전달 (지도 표시)
        model.addAttribute("naverMapApiKey", naverMapApiKey);

        // 날씨 정보를 가져오기
        String latitude = (String) location.get("latitude");
        String longitude = (String) location.get("longitude");
        WeatherResponseDTO weatherResponseDTO = schoolService.getWeatherByCoordinates(latitude, longitude);
        model.addAttribute("weatherResponseDTO", weatherResponseDTO);

        return "user/pages/schoolDetail";
    }

}
