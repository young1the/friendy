package com.chunjae.friendy.school.service;


import com.chunjae.friendy.school.dto.SchoolAddRequest;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.repository.SchoolRepository;
import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolAddressRepository schoolAddressRepository;

    //학교 엔티티와 주소 엔티티 함께 조회
    public School detailSchool(long idx) {
        return schoolRepository.findById(idx)
                .map(school -> { school.getAddress();
                    return school;
                })
                .orElse(null);
    }

    //지도 사용을 위한 위치 정보 조회
    public Map<String, Object> locationSchool(long idx) {

        Optional<School> schoolOptional = schoolRepository.findById(idx);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();

            // School로 데이터 추출
            String schoolName = school.getName();
            String latitude = school.getAddress().getLatitude();
            String longitude = school.getAddress().getLongitude();

            // 데이터를 Map에 담아 반환
            Map<String, Object> locationData = new HashMap<>();
            locationData.put("schoolName", schoolName);
            locationData.put("latitude", latitude);
            locationData.put("longitude", longitude);

            return locationData;
        } else {
            // 엔티티를 찾을 수 없을 때 예외 처리 또는 에러 처리
            throw new EntityNotFoundException("School not found with ID: " + idx);
        }
    }


    @Autowired
    public SchoolService(SchoolRepository schoolRepository, SchoolAddressRepository schoolAddressRepository) {
        this.schoolRepository = schoolRepository;
        this.schoolAddressRepository = schoolAddressRepository;
    }
        public School addSchool(SchoolAddRequest schoolAddRequest) {
            // School 객체 생성
            School school = new School();
            school.setCityEduOffice(schoolAddRequest.getCityEduOffice());
            school.setDistrictEduOffice(schoolAddRequest.getDistrictEduOffice());
            school.setSchoolCode(schoolAddRequest.getSchoolCode());
            school.setName(schoolAddRequest.getName());
            school.setLevelCode(schoolAddRequest.getLevelCode());
            school.setEstablishment(schoolAddRequest.getEstablishment());
            school.setDayNight(schoolAddRequest.getDayNight());
            school.setTel(schoolAddRequest.getTel());
            school.setFax(schoolAddRequest.getFax());
            school.setUrl(schoolAddRequest.getUrl());
            school.setGender(schoolAddRequest.getGender());
            school.setDeletedYn(school.getDeletedYn());


            // SchoolAddress 객체 생성
            SchoolAddress schoolAddress = new SchoolAddress();
            schoolAddress.setRoadAddress(schoolAddRequest.getRoadAddress());
            schoolAddress.setRoadAddressDetail(schoolAddRequest.getRoadAddressDetail());
            schoolAddress.setRoadZipCode(schoolAddRequest.getRoadZipCode());
            schoolAddress.setLatitude(schoolAddRequest.getLatitude());
            schoolAddress.setLongitude(schoolAddRequest.getLongitude());
            schoolAddress.setBoundaryCode(schoolAddRequest.getBoundaryCode());

            // School과 SchoolAddress 관계 설정
            school.setAddress(schoolAddress);
            schoolAddress.setSchool(school);

            // School과 SchoolAddress를 한 번에 저장
            schoolRepository.save(school);


            return school;
        }

}
