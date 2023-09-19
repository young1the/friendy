package com.chunjae.friendy.user;

import com.chunjae.friendy.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchService userSearchServiece;

    @GetMapping("/elem/search")
    public String showSearchForm(){
        return "admin/pages/user_search";
    }

    @PostMapping("/elem/search")
    @ResponseBody
    public ResponseEntity<List<UserSearchResponseInterface>> showSearch(@RequestBody UserSearchRequestDTO dto){
        List<UserSearchResponseInterface> resultSearch = userSearchServiece.searchKeyword(dto.getKeyword());
        return ResponseEntity.ok(resultSearch);
    }

    @PostMapping("/middle/search")
    @ResponseBody
    public ResponseEntity<List<CustomMiddleSearchResponse>> showMiddleSearch(@RequestBody UserSearchRequestDTO dto){
        String requestURL = "http://10.41.1.94:8080/middle/search?keyword=" + dto.getKeyword();
        List<MiddleSearchResponse> middleResponse = userSearchServiece.getSearchListFromMiddleServer(requestURL);

        List<CustomMiddleSearchResponse> customResponse = new ArrayList<>();

        for (MiddleSearchResponse m : middleResponse){
            customResponse.add(CustomMiddleSearchResponse.builder()
                    .idx(m.getSchoolId())
                    .name(m.getScName())
                    .roadAddress(m.getRnAddress())
                    .roadZipCode(m.getRnPostalCode())
                    .latitude(m.getLatitude())
                    .longitude(m.getLongitude())
                    .levelCode(m.getScGradeCode())
                    .schoolCode(m.getSdScCode())
                    .build());
        }
        return ResponseEntity.ok(customResponse);
    }

    @PostMapping("/high/school/search")
    @ResponseBody
    public ResponseEntity<List<CustomHighSearchResponse>> showHighSearch(@RequestParam String keyword){

        String requestURL = "http://10.41.0.174:8080/high/school/search?keyword=" + keyword;
        List<HighSearchResponse> highResponses = userSearchServiece.getSearchListFromHighServer(requestURL);

        List<CustomHighSearchResponse> customResponse = new ArrayList<>();

        for (HighSearchResponse h : highResponses){
            customResponse.add(CustomHighSearchResponse.builder()
                    .idx(String.valueOf(h.getIdx()))
                    .name(h.getSchoolName())
                    .roadAddress(h.getStreetAddr())
//                    .latitude()
//                    .longitude()
                    .levelCode("04")
//                    .schoolCode()
                    .build());
        }
        return ResponseEntity.ok(customResponse);
    }

}
