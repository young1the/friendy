package com.chunjae.friendy.util.coordinate;

import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoordinateController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping("/findSchoolsInRadius")
    public String findSchoolsInRadius(Model model) {
        double latitude = 37.4907391344;
        double longitude = 127.0154235196;
        double radius = 3.0; // 3KM 반경 설정

        // 계산된 좌표 범위 내의 학교 주소 검색
        List<SchoolAddress> schoolsNearby = schoolService.findSchoolsInRadius(latitude, longitude, radius);

        model.addAttribute("schools", schoolsNearby);

        return "admin/pages/schools";
    }
}


