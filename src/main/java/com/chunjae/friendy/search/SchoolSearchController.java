package com.chunjae.friendy.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/school/search")
public class SchoolSearchController {

    private final SchoolSearchService schoolSearchService;

    public SchoolSearchController(SchoolSearchService schoolSearchService) {
        this.schoolSearchService = schoolSearchService;
    }

    @GetMapping("")
    public String searchSchools(Model model, SearchSchoolRequestDTO searchRequestDTO, @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<SearchSchoolDTO> schools;

        //검색화면 들어왔을 때 초기 화면 보이도록 함
        if (searchRequestDTO.getSearchKeyword() != null && searchRequestDTO.getSearchOption() != null
                && searchRequestDTO.getSearchDistrict() != null && searchRequestDTO.getSearchCity() != null) {
            String searchCity = searchRequestDTO.getSearchCity();
            String searchDistrict = searchRequestDTO.getSearchDistrict();
            String searchOption = searchRequestDTO.getSearchOption();
            String searchKeyword = searchRequestDTO.getSearchKeyword();
            schools = schoolSearchService.getSchoolBySearchKeyword(searchCity, searchDistrict, searchOption, searchKeyword, pageable);
        } else {
            schools = schoolSearchService.findAll(pageable);
        }

        // 페이징 처리
        int nowPage = schools.getPageable().getPageNumber() + 1;
        int totalPages = schools.getTotalPages();
        int pageSize = 10;
        int startPage = (nowPage - 1) / pageSize * pageSize + 1;
        int endPage = Math.min(startPage + pageSize - 1, totalPages);

        model.addAttribute("schools", schools);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nowPage", nowPage);

        return "school/search/searchSchool";
    }
}
