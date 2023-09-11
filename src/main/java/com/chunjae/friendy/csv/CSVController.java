package com.chunjae.friendy.csv;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/csv")
public class CSVController {

    @GetMapping("")
    public String index() {
        return "admin/pages/csv/index";
    }

    @PostMapping("upload")
    public String addCSVFile(@RequestParam("file") MultipartFile file) {
        return "redirect:/admin/csv/";
    }

}
