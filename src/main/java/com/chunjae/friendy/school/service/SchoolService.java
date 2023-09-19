package com.chunjae.friendy.school.service;


import com.chunjae.friendy.admin.repository.AdminRepository;
import com.chunjae.friendy.school.dto.SchoolRequest;
import com.chunjae.friendy.school.dto.WeatherResponseDTO;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.entity.SchoolLog;
import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import com.chunjae.friendy.school.repository.SchoolLogRepository;
import com.chunjae.friendy.school.repository.SchoolRepository;
import com.chunjae.friendy.coordinate.util.Coordinate;
import com.chunjae.friendy.coordinate.util.CoordinateConverter;
import com.chunjae.friendy.coordinate.util.CoordinateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolAddressRepository schoolAddressRepository;
    private final SchoolLogRepository schoolLogRepository;
    private final AdminRepository adminRepository;


    //학교 엔티티와 주소 엔티티 함께 조회
    public School detailSchool(long idx) {
        return schoolRepository.findById(idx)
                .map(school -> {
                    school.getAddress();
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
            double latitude = Double.parseDouble(school.getAddress().getLatitude());
            double longitude = Double.parseDouble(school.getAddress().getLongitude());

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
    public SchoolService(SchoolRepository schoolRepository, SchoolAddressRepository schoolAddressRepository, SchoolLogRepository schoolLogRepository, AdminRepository adminRepository) {
        this.schoolRepository = schoolRepository;
        this.schoolAddressRepository = schoolAddressRepository;
        this.schoolLogRepository = schoolLogRepository;
        this.adminRepository = adminRepository;
    }

    private void mapSchoolByRequest(School school, SchoolRequest request) {
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

    private void mapSchoolAddressByRequest(SchoolAddress schoolAddress, SchoolRequest request) {
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

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    // 날씨 조회
    public WeatherResponseDTO getWeatherByCoordinates(String latitude, String longitude) {
        try {
            // 현재 날짜와 시간 설정
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String baseDate = dateFormat.format(now); // 오늘 날짜로 설정

            // 위도와 경도 이용하여 nx, ny 좌표값 계산
            Map<String, Double> result = CoordinateConverter.convertToNxNy(Double.parseDouble(latitude), Double.parseDouble(longitude));
            int nx = (int) Math.round(result.get("nx"));
            int ny = (int) Math.round(result.get("ny"));

            // API 요청 URL 생성
            String apiUrl = weatherApiUrl +
                    "?serviceKey=" + weatherApiKey +
                    "&pageNo=1" +
                    "&numOfRows=1000" +
                    "&dataType=JSON" +
                    "&base_date=" + baseDate +
                    "&base_time=0500" +
                    "&nx=" + nx +
                    "&ny=" + ny;

            // HTTP 요청을 보내고 API 응답 데이터를 문자열로 받아옴
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application.json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String apiResponse = response.toString();

                // JSON 문자열을 원하는 DTO로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                WeatherResponseDTO weatherResponseDTO = objectMapper.readValue(apiResponse, WeatherResponseDTO.class);

                return weatherResponseDTO;
            } else {
                System.out.println("HTTP 요청 실패: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void createLog(Long schoolIdx, String adminId) {
        Long adminIdx = adminRepository.findByUsername(adminId).getIdx();

        SchoolLog schoolLog = new SchoolLog();
        schoolLog.setCreatedAt(LocalDateTime.now());
        schoolLog.setSchoolIdx(schoolRepository.findByIdx(schoolIdx));
        schoolLog.setAdminIdx(adminRepository.findByIdx(adminIdx));
        schoolLog.setModifiedAt(null);
        schoolLogRepository.save(schoolLog);

    }

    public void updateLog(Long schoolIdx, String adminId) {
        Long adminIdx = adminRepository.findByUsername(adminId).getIdx();

        SchoolLog schoolLog = new SchoolLog();
        schoolLog.setCreatedAt(null);
        schoolLog.setSchoolIdx(schoolRepository.findByIdx(schoolIdx));
        schoolLog.setAdminIdx(adminRepository.findByIdx(adminIdx));
        schoolLog.setModifiedAt(LocalDateTime.now());
        schoolLogRepository.save(schoolLog);

    }


    // 주어진 좌표와 반경 내의 학교 주소 검색
    public List<SchoolAddress> findSchoolsInRadius(double latitude, double longitude, double radius) {
        // CoordinateUtil 클래스를 이용하여 주어진 좌표와 반경 내의 좌표 범위 계산
        Coordinate pivot = new Coordinate(latitude, longitude);
        Coordinate maxCoordinate = CoordinateUtil.getMaxCoordinateByKM(pivot, radius);
        Coordinate minCoordinate = CoordinateUtil.getMinCoordinateByKM(pivot, radius);

        // 좌표 범위 내의 학교 주소 검색
        return schoolAddressRepository.findByLatitudeBetweenAndLongitudeBetween(
                minCoordinate.getLatitude()+"",
                maxCoordinate.getLatitude()+"",
                minCoordinate.getLongitude()+"",
                maxCoordinate.getLongitude()+""
        ).stream().filter(ele -> CoordinateUtil.isInArea(new Coordinate(latitude, longitude), new Coordinate(ele.getLatitude(), ele.getLongitude()), 3.0))
                .collect(Collectors.toList());
    }

}
