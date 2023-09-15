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
    private void mapSchoolByRequest(School school,SchoolRequest request) {
        school.setCityEduOffice(request.getCityEduOffice());
        school.setDistrictEduOffice(request.getDistrictEduOffice());
        school.setSchoolCode(request.getSchoolCode());
        school.setName(request.getName());
        school.setLevelCode(request.getLevelCode());
        school.setEstablishment(request.getEstablishment());
        school.setDayNight(request.getDayNight());
        school.setTel(request.getTel());
        school.setFax(request.getFax());
        school.setUrl(request.getUrl());
        school.setGender(request.getGender());
        school.setDistrict(request.getDistrict());
    }

    private void mapSchoolAddressByRequest(SchoolAddress schoolAddress, SchoolRequest request){
        schoolAddress.setRoadAddress(request.getRoadAddress());
        schoolAddress.setRoadAddressDetail(request.getRoadAddressDetail());
        schoolAddress.setRoadZipCode(request.getRoadZipCode());
        schoolAddress.setLatitude(request.getLatitude());
        schoolAddress.setLongitude(request.getLongitude());
        schoolAddress.setBoundaryCode(request.getBoundaryCode());

    }

    private void setSchoolAddress(School school, SchoolAddress schoolAddress) {
        school.setAddress(schoolAddress);
        schoolAddress.setSchool(school);
    }


    public School addSchool(SchoolRequest schoolRequest) {

        School school = new School();
        SchoolAddress schoolAddress = new SchoolAddress();
        mapSchoolByRequest(school, schoolRequest);
        mapSchoolAddressByRequest(schoolAddress, schoolRequest);

        setSchoolAddress(school, schoolAddress);

        return schoolRepository.save(school);
    }



    public School update(Long idx, SchoolRequest schoolRequest) {
        Optional<School> schoolOptional = schoolRepository.findById(idx);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            SchoolAddress schoolAddress = school.getAddress();

            mapSchoolByRequest(school, schoolRequest);
            mapSchoolAddressByRequest(schoolAddress, schoolRequest);

            setSchoolAddress(school, schoolAddress);

            return schoolRepository.save(school);
        } else {
            throw new EntityNotFoundException("School not found with ID: " + idx);
        }
    }

    public void delete(long idx) {
        School school = schoolRepository.findByIdx(idx);
        school.setDeletedYn('Y');
        schoolRepository.save(school);
    }
}
