package com.chunjae.friendy.school;

import com.chunjae.friendy.school.dto.WeatherResponseDTO;
import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.repository.SchoolRepository;
import com.chunjae.friendy.util.coordinate.CoordinateConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

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
            SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
            String baseDate = dateFormat.format(now); // 오늘 날짜로 설정
            //String baseTime = timeFormat.format(now); // 현재 시간
            System.out.println("위도 : " + latitude + ", 경도 : " + longitude);

            // 위도와 경도 이용하여 nx, ny 좌표값 계산
            Map<String, Double> result = CoordinateConverter.convertToNxNy(Double.parseDouble(latitude), Double.parseDouble(longitude));
            int nx = (int) Math.round(result.get("nx"));
            int ny = (int) Math.round(result.get("ny"));
            System.out.println("nx : " + nx + ", ny : " + ny);

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

            System.out.println(apiUrl);

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
}
