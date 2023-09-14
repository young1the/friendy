package com.chunjae.friendy.school.controller;

import com.chunjae.friendy.school.dto.SchoolRequest;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.service.SchoolService;
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
    public String add(SchoolRequest schoolRequest, Model model) {
        School school = schoolService.addSchool(schoolRequest);
        model.addAttribute("school", school);
        return "redirect:/school/add";
    }

    @GetMapping("/update-form")
    public String update(Model model, @RequestParam long idx){

        //학교의 세부 정보를 가져오기
        School school = schoolService.detailSchool(idx);
        model.addAttribute("school", school);

        return "admin/pages/schoolUpdate";
    }

    @PostMapping("/update")
    public String update(@RequestParam long idx, @ModelAttribute SchoolRequest updateRequest) {
        // 학교 정보 업데이트
        schoolService.update(idx, updateRequest);

        // 업데이트 후에 어떤 페이지로 리다이렉트할지 리턴
        return "redirect:/school/detail?idx=" + idx;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long idx) {
        System.out.println("deleted");
        schoolService.delete(idx);
        return "redirect:/";
    }

}
