package com.chunjae.friendy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchService userSearchServiece;

    @GetMapping("/user/search")
    public String showSearchForm(){
        return "admin/pages/user_search";
    }

    @PostMapping("/user/search")
    @ResponseBody
    public ResponseEntity<List<UserSearchResponseInterface>> showSearch(@RequestBody UserSearchRequestDTO dto){
        List<UserSearchResponseInterface> resultSearch = userSearchServiece.searchKeyword(dto.getKeyword());
        return ResponseEntity.ok(resultSearch);
    }


}
