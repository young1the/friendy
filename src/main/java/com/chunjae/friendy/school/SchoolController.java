package com.chunjae.friendy.school;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @GetMapping("/add")
    public String add(Model model) {
        School school = new School();

        model.addAttribute("school", school);
        return "admin/pages/schoolAdd";
    }
}
