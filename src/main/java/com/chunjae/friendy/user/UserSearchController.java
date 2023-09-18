package com.chunjae.friendy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchServiece userSearchServiece;

    @GetMapping("/user/search")
    public String showSearchForm(){
        return "admin/pages/user_search";
    }

    @PostMapping("/user/search")
    @ResponseBody
    public ResponseEntity<List<UserSearchResponseInterface>> showSearch(@RequestParam String keyword){

        List<UserSearchResponseInterface> resultSearch = userSearchServiece.searchKeyword(keyword);

        if(resultSearch.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultSearch);
    }


}
