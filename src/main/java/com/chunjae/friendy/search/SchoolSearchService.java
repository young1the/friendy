package com.chunjae.friendy.search;

import com.chunjae.friendy.school.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolSearchService {

    private final SchoolRepository schoolRepository;
    private final SchoolAddressRepository schoolAddressRepository;


    public SchoolSearchService(SchoolAddressRepository schoolAddressRepository, SchoolRepository schoolRepository) {
        this.schoolAddressRepository = schoolAddressRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public List<SearchSchoolDTO> getAllSchools(){

        List<School> schools = schoolRepository.findAll();
        List<SearchSchoolDTO> searchSchoolDTOList = new ArrayList<>();

        for (School school: schools) {
            SearchSchoolDTO searchSchoolDTO = toDTO(school);
            searchSchoolDTOList.add(searchSchoolDTO);
        }

        return searchSchoolDTOList;
    }

    @Transactional
    public List<SearchSchoolDTO> getSchoolByName(String city, String district, String name){

        List<School> schools = schoolRepository.findAllByName(city,district,name);
        List<SearchSchoolDTO> searchSchoolDTOList = new ArrayList<>();

        for (School school: schools) {
            SearchSchoolDTO searchSchoolDTO = toDTO(school);
            searchSchoolDTOList.add(searchSchoolDTO);
        }

        return searchSchoolDTOList;
    }

    private SearchSchoolDTO toDTO(School school){
        SchoolAddress schoolAddress = school.getAddress();
        SearchSchoolDTO searchSchoolDTO = new SearchSchoolDTO();

        searchSchoolDTO.setSchoolCode(school.getSchoolCode());
        searchSchoolDTO.setCityEduOffice(school.getCityEduOffice());
        searchSchoolDTO.setDayNight(school.getDayNight());
        searchSchoolDTO.setDeletedYn(school.getDeletedYn());
        searchSchoolDTO.setDistrictEduOffice(school.getDistrictEduOffice());
        searchSchoolDTO.setEstablishment(school.getEstablishment());
        searchSchoolDTO.setFax(school.getFax());
        searchSchoolDTO.setGender(school.getGender());
        searchSchoolDTO.setLevelCode(school.getLevelCode());
        searchSchoolDTO.setName(school.getName());
        searchSchoolDTO.setTel(school.getTel());
        searchSchoolDTO.setUrl(school.getUrl());
        searchSchoolDTO.setBoundaryCode(schoolAddress.getBoundaryCode());
        searchSchoolDTO.setLatitude(schoolAddress.getLatitude());
        searchSchoolDTO.setLongitude(schoolAddress.getLongitude());
        searchSchoolDTO.setRoadAddress(schoolAddress.getRoadAddress());
        searchSchoolDTO.setRoadAddressDetail(schoolAddress.getRoadAddressDetail());
        searchSchoolDTO.setRoadZipCode(schoolAddress.getRoadZipCode());

        return searchSchoolDTO;
    }
}
