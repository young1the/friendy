package com.chunjae.friendy.admin;

import com.chunjae.friendy.admin.dto.AdminCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String index() {
        return "admin/pages/index";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/pages/login";
    }

    @GetMapping("/signup")
    public String signupPage(){
        return "admin/pages/signup";
    }

    @PostMapping("/signup")
    public String signup(AdminCreateRequest adminCreateRequest){

        adminService.createAdmin(adminCreateRequest);

        return "redirect:/login";
    }


}
