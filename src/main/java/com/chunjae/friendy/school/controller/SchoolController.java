package com.chunjae.friendy.school.controller;

import com.chunjae.friendy.school.dto.SchoolRequest;
import com.chunjae.friendy.school.dto.WeatherResponseDTO;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("school")
public class SchoolController {

    private final SchoolService schoolService;

    // API 키 주입
    @Value("${naver.map.api.clientId}")
    private String naverMapApiKey;

    // 학교 정보 상세 조회
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

    @GetMapping("/add")
    public String add(){ return "admin/pages/schoolAdd"; }

    @PostMapping("/add")
    public String add(SchoolRequest schoolRequest, Model model, Authentication authentication) {
        School school = schoolService.addSchool(schoolRequest);
        model.addAttribute("school", school);

        if(school != null){
            String adminId = authentication.getName();
            Long schoolIdx = school.getIdx();
            schoolService.createLog(schoolIdx, adminId);
        }

        return "redirect:/school/search";
    }

    @GetMapping("/update-form")
    public String update(Model model, @RequestParam long idx){

        //학교의 세부 정보를 가져오기
        School school = schoolService.detailSchool(idx);
        model.addAttribute("school", school);

        return "admin/pages/schoolUpdate";
    }

    @PostMapping("/update")
    public String update(@RequestParam long idx, @ModelAttribute SchoolRequest updateRequest, Authentication authentication) {
        // 학교 정보 업데이트
        School school = schoolService.update(idx, updateRequest);

        if(school != null){
            String adminId = authentication.getName();
//            Long schoolIdx = school.getIdx();
            schoolService.updateLog(idx, adminId);
        }

        // 업데이트 후에 어떤 페이지로 리다이렉트할지 리턴
        return "redirect:/school/detail?idx=" + idx;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long idx) {
        schoolService.delete(idx);
        return "redirect:/school/search";
    }

    // 학교 정보 상세 조회 (user)
    @GetMapping("/user/{idx}")
    public String userDetail(Model model, @PathVariable long idx) {

        //학교의 세부 정보를 가져오기
        School school = schoolService.detailSchool(idx);
        model.addAttribute("school", school);

        //학교 위치 가져오기
        Map<String, Object> location = schoolService.locationSchool(idx);
        model.addAttribute("location", location);

        // API 키 전달 (지도 표시)
        model.addAttribute("naverMapApiKey", naverMapApiKey);

        // 날씨 정보를 가져오기
        String latitude = location.get("latitude")+"";
        String longitude = location.get("longitude")+"";
        WeatherResponseDTO weatherResponseDTO = schoolService.getWeatherByCoordinates(latitude, longitude);
        model.addAttribute("weatherResponseDTO", weatherResponseDTO);

        return "user/pages/schoolDetail";
    }
}
