package com.chunjae.friendy.search;

import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import com.chunjae.friendy.school.repository.SchoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<SearchSchoolDTO> findAll(Pageable pageable) {
        Page<SchoolAddress> schoolPage = schoolAddressRepository.findAll(pageable);

        List<SearchSchoolDTO> searchSchoolDTOList = new ArrayList<>();

        for (SchoolAddress schoolAddress : schoolPage) {
            School school = schoolAddress.getSchool();
            SearchSchoolDTO searchSchoolDTO = toDTO(school);
            searchSchoolDTOList.add(searchSchoolDTO);
        }

        return new PageImpl<>(searchSchoolDTOList, pageable, schoolPage.getTotalElements());
    }

    @Transactional
    public Page<SearchSchoolDTO> getSchoolBySearchKeyword(String searchCity, String searchDistrict,  String searchOption, String searchKeyword, Pageable pageable) {

        String district = searchCity + " " + searchDistrict;
        Page<SearchSchoolDTO> searchSchoolDTOList;

        if ("전체".equals(searchCity)) {
            switch (searchOption) {
                case "전체":
                    Page<School> schoolPage = searchKeyword.isEmpty() ? schoolRepository.findAll(pageable)
                            : schoolRepository.findBySearchKeyword(searchKeyword, pageable);
                    searchSchoolDTOList = schoolPage.map(this::toDTO);
                    break;
                case "name":
                    Page<School> schoolPageName = searchKeyword.isEmpty() ? schoolRepository.findAll(pageable)
                            : schoolRepository.findByName(searchKeyword, pageable);
                    searchSchoolDTOList = schoolPageName.map(this::toDTO);
                    break;
                case "address":
                    Page<SchoolAddress> schoolAddressPage = searchKeyword.isEmpty() ? schoolAddressRepository.findAll(pageable)
                            : schoolAddressRepository.findDistrictBySearchKeyword(searchKeyword, pageable);
                    searchSchoolDTOList = schoolAddressPage.map(schoolAddress -> toDTO(schoolAddress.getSchool()));
                    break;
                default:
                    // 등록자 검색

                    searchSchoolDTOList = Page.empty();
            }
        } else {
            if (!"전체".equals(searchDistrict)){
                switch (searchOption){
                    // 전x - 전x (district) - 전
                    case "전체":
                        Page<School> schoolPage = searchKeyword.isEmpty() ? schoolRepository.findByDistrict(district, pageable)
                                : schoolRepository.findByDistrictAndSearchKeyword(district, searchKeyword, pageable);
                        searchSchoolDTOList = schoolPage.map(this::toDTO);
                        break;
                    // 전x - 전x (district)- 명
                    case "name":
                        Page<School> schoolPageName = searchKeyword.isEmpty() ? schoolRepository.findByDistrict(district, pageable)
                                : schoolRepository.findByDistrictAndName(district, searchKeyword, pageable);
                        searchSchoolDTOList = schoolPageName.map(this::toDTO);
                        break;
                    // 전x - 전x (district)- 주
                    case "address":
                        Page<SchoolAddress> schoolAddressPage = searchKeyword.isEmpty() ? schoolAddressRepository.findByDistrictUsingJoin(district, pageable)
                                : schoolAddressRepository.findByDistrictAndSearchKeywordUsingJoin   (district, searchKeyword, pageable);
                        searchSchoolDTOList = schoolAddressPage.map(schoolAddress -> toDTO(schoolAddress.getSchool()));
                        break;
                    default:
                        // 등록자 검색

                        searchSchoolDTOList = Page.empty();
                }
            } else {
                switch (searchOption){
                    // 전x (searchCity) - 전  - 전
                    case "전체":
                        Page<School> schoolPage = searchKeyword.isEmpty() ? schoolRepository.findBySearchCity(searchCity, pageable)
                                : schoolRepository.findBySearchCityAndSearchKeyword(searchCity, searchKeyword, pageable);
                        searchSchoolDTOList = schoolPage.map(this::toDTO);
                        break;
                    // 전x (searchCity) - 전 - 명
                    case "name":
                        Page<School> schoolPageName = searchKeyword.isEmpty() ? schoolRepository.findBySearchCity(searchCity, pageable)
                                : schoolRepository.findBySearchCityAndName(searchCity, searchKeyword, pageable);
                        searchSchoolDTOList = schoolPageName.map(this::toDTO);
                        break;
                    // 전x (searchCity) - 전 - 주
                    case "address":
                        Page<SchoolAddress> schoolAddressPage = searchKeyword.isEmpty() ? schoolAddressRepository.findBySearchCityUsingJoin(searchCity, pageable)
                                : schoolAddressRepository.findBySearchCityAndSearchKeywordUsingJoin(searchCity, searchKeyword, pageable);
                        searchSchoolDTOList = schoolAddressPage.map(schoolAddress -> toDTO(schoolAddress.getSchool()));
                        break;
                    default:
                        // 등록자 검색

                        searchSchoolDTOList = Page.empty();
                }
            }
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
        searchSchoolDTO.setDistrict(school.getDistrict());
        searchSchoolDTO.setBoundaryCode(schoolAddress.getBoundaryCode());
        searchSchoolDTO.setLatitude(String.valueOf(schoolAddress.getLatitude()));
        searchSchoolDTO.setLongitude(String.valueOf(schoolAddress.getLongitude()));
        searchSchoolDTO.setRoadAddress(schoolAddress.getRoadAddress());
        searchSchoolDTO.setRoadAddressDetail(schoolAddress.getRoadAddressDetail());
        searchSchoolDTO.setRoadZipCode(schoolAddress.getRoadZipCode());
        searchSchoolDTO.setIdx(school.getIdx());
        searchSchoolDTO.setSchoolIdx(schoolAddress.getSchool_idx());

        return searchSchoolDTO;
    }
}
