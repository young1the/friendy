package com.chunjae.friendy.school.search;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/school/search")
public class SchoolSearchController {

    private final SchoolSearchService schoolSearchService;

    public SchoolSearchController(SchoolSearchService schoolSearchService) {
        this.schoolSearchService = schoolSearchService;
    }

    @GetMapping("")
    public String getAllSchools(Model model){

        List<SearchSchoolDTO> schools = schoolSearchService.getAllSchools();
        model.addAttribute("schools", schools);

        return "school/search/searchSchool";
    }

    @PostMapping("/category")
    public String findSchoolByCategory(Model model,@RequestParam("city") String city, @RequestParam("district") String district, @RequestParam("name") String name){

        List<SearchSchoolDTO> schools = schoolSearchService.getSchoolByName(city,district,name);
        model.addAttribute("schools", schools);

        return "school/search/searchSchool";
    }
}
