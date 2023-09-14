package com.chunjae.friendy.school.controller;

import com.chunjae.friendy.school.entity.School;

import com.chunjae.friendy.school.dto.SchoolAddRequest;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String add(SchoolAddRequest schoolAddRequest, Model model) {
        School school = schoolService.addSchool(schoolAddRequest);
        model.addAttribute("school", school);
        return "redirect:/school/add";
    }

}
