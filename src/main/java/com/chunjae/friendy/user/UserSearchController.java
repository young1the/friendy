package com.chunjae.friendy.user;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
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
                    .roadAddress(m.getRnDetailAddress())
                    .roadZipCode(m.getRnPostalCode())
                    .latitude(m.getLatitude())
                    .longitude(m.getLongitude())
                    .levelCode(m.getScGradeCode())
                    .schoolCode(m.getSdScCode())
                    .build());
        }
        return ResponseEntity.ok(customResponse);
    }

}
