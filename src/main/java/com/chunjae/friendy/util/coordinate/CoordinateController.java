package com.chunjae.friendy.util.coordinate;

import com.chunjae.friendy.school.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoordinateController {

    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping("/findSchoolsInRadius")
    public String findSchoolsInRadius(Model model) {

        Coordinate pivot = new Coordinate("37.4907391344", "127.0154235196");

        double radius = 10.0; // 3km 반경 설정-> 잠시 10km로

        List<Coordinate> schoolsNearby = schoolRepository.findSchoolsInRadius(pivot.getLatitude(), pivot.getLongitude(), radius);

        model.addAttribute("schools", schoolsNearby);

        return "admin/pages/schools";
    }
}
