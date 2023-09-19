package com.chunjae.friendy.coordinate;

import com.chunjae.friendy.coordinate.dto.CoordinateDTO;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoordinateController {

    @Autowired
    private SchoolService schoolService;


    @GetMapping("/aroundSchool")
    public ResponseEntity<List<CoordinateDTO>> findAroundSchool(
            @RequestParam double latitude,
            @RequestParam double longitude) {

        double radius = 3.0; // 3KM 반경 설정
        List<SchoolAddress> schoolsNearby = schoolService.findSchoolsInRadius(latitude, longitude, radius);

        List<CoordinateDTO> coordinateDTOS = new ArrayList<>();
        for (SchoolAddress schoolAddress : schoolsNearby) {
            CoordinateDTO coordinateDTO = new CoordinateDTO(schoolAddress.getSchool_idx(), schoolAddress.getSchool().getName(), schoolAddress.getRoadAddress(), schoolAddress.getRoadZipCode(), schoolAddress.getLatitude(), schoolAddress.getLongitude(), schoolAddress.getSchool().getLevelCode());
            coordinateDTOS.add(coordinateDTO);
        }
        return ResponseEntity.ok(coordinateDTOS);
    }

}







