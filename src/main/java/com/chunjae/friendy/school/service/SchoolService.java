package com.chunjae.friendy.school.service;


import com.chunjae.friendy.school.dto.SchoolRequest;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import com.chunjae.friendy.school.repository.SchoolRepository;
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
    public School addSchool(SchoolRequest schoolRequest) {
        // School 객체 생성
        School school = new School();
        school.setCityEduOffice(schoolRequest.getCityEduOffice());
        school.setDistrictEduOffice(schoolRequest.getDistrictEduOffice());
        school.setSchoolCode(schoolRequest.getSchoolCode());
        school.setName(schoolRequest.getName());
        school.setLevelCode(schoolRequest.getLevelCode());
        school.setEstablishment(schoolRequest.getEstablishment());
        school.setDayNight(schoolRequest.getDayNight());
        school.setTel(schoolRequest.getTel());
        school.setFax(schoolRequest.getFax());
        school.setUrl(schoolRequest.getUrl());
        school.setGender(schoolRequest.getGender());
        school.setDistrict(schoolRequest.getDistrict());


        // SchoolAddress 객체 생성
        SchoolAddress schoolAddress = new SchoolAddress();
        schoolAddress.setRoadAddress(schoolRequest.getRoadAddress());
        schoolAddress.setRoadAddressDetail(schoolRequest.getRoadAddressDetail());
        schoolAddress.setRoadZipCode(schoolRequest.getRoadZipCode());
        schoolAddress.setLatitude(schoolRequest.getLatitude());
        schoolAddress.setLongitude(schoolRequest.getLongitude());
        schoolAddress.setBoundaryCode(schoolRequest.getBoundaryCode());

        // School과 SchoolAddress 관계 설정
        school.setAddress(schoolAddress);
        schoolAddress.setSchool(school);

        // School과 SchoolAddress를 한 번에 저장
        schoolRepository.save(school);


        return school;
    }
    public School update(Long idx, SchoolRequest updateRequest) {
        Optional<School> schoolOptional = schoolRepository.findById(idx);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();

            // 업데이트할 정보를 폼에서 받아와서 엔티티에 설정
            school.setCityEduOffice(updateRequest.getCityEduOffice());
            school.setDistrictEduOffice(updateRequest.getDistrictEduOffice());
            school.setSchoolCode(updateRequest.getSchoolCode());
            school.setName(updateRequest.getName());
            school.setLevelCode(updateRequest.getLevelCode());
            school.setEstablishment(updateRequest.getEstablishment());
            school.setDayNight(updateRequest.getDayNight());
            school.setTel(updateRequest.getTel());
            school.setFax(updateRequest.getFax());
            school.setUrl(updateRequest.getUrl());
            school.setGender(updateRequest.getGender());
            school.setDistrict(updateRequest.getDistrict());

            // 주소 정보 업데이트
            SchoolAddress schoolAddress = school.getAddress();
            schoolAddress.setRoadAddress(updateRequest.getRoadAddress());
            schoolAddress.setRoadAddressDetail(updateRequest.getRoadAddressDetail());
            schoolAddress.setRoadZipCode(updateRequest.getRoadZipCode());
            schoolAddress.setLatitude(updateRequest.getLatitude());
            schoolAddress.setLongitude(updateRequest.getLongitude());
            schoolAddress.setBoundaryCode(updateRequest.getBoundaryCode());

            // School과 SchoolAddress 관계 설정
            school.setAddress(schoolAddress);
            schoolAddress.setSchool(school);

            // School 객체를 저장하여 업데이트
            return schoolRepository.save(school);
        } else {
            // 엔티티를 찾을 수 없을 때 예외 처리 또는 에러 처리
            throw new EntityNotFoundException("School not found with ID: " + idx);
        }
    }

    public void delete(long idx) {
        School school = schoolRepository.findByIdx(idx);
        school.setDeletedYn('Y');
        schoolRepository.save(school);
    }
}
