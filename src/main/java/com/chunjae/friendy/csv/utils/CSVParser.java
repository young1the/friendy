package com.chunjae.friendy.csv.utils;

import com.chunjae.friendy.school.entity.School;
import com.chunjae.friendy.school.entity.SchoolAddress;
import org.springframework.data.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class CSVParser {

    private Map<String, String> schoolMap;
    private Map<String, String> schoolAddressMap;
    private final List<String> thead;

    public CSVParser(String line) {
        this.thead = this.parseCSVLine(line);
        this.schoolMap = getSchoolMap();
        this.schoolAddressMap = getSchoolAddressMap();
    }

    public Map<String, String> getSchoolMap() {
        Map<String, String> map = new HashMap<>();
        map.put("시도교육청", "cityEduOffice");
        map.put("지역교육청", "districtEduOffice");
        map.put("지역", "district");
        map.put("정보공시 학교코드", "schoolCode");
        map.put("학교명", "name");
        map.put("학교급코드", "levelCode");
        map.put("설립구분", "establishment");
        map.put("주야구분", "dayNight");
        map.put("전화번호", "tel");
        map.put("팩스번호", "fax");
        map.put("홈페이지 주소", "url");
        map.put("남녀공학 구분", "gender");
        return map;
    }

    public Map<String, String> getSchoolAddressMap() {
        Map<String, String> map = new HashMap<>();
        map.put("학교도로명 주소", "roadAddress");
        map.put("학교도로명 상세주소", "roadAddressDetail");
        map.put("학교도로명 우편번호", "roadZipCode");
        map.put("위도", "latitude");
        map.put("경도", "longitude");
        map.put("법정동코드", "boundaryCode");
        return map;
    }

    public List<String> parseCSVLine(String line) {
        List<String> parsed = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isQuoteClosed = true;
        for (char b : line.toCharArray()) {
            if (b == '\"') {
                isQuoteClosed = !isQuoteClosed;
            } else if (b == ',' && isQuoteClosed) {
                parsed.add(stringBuilder.toString().trim());
                stringBuilder.setLength(0);
            } else {
                if (!isQuoteClosed) {
                    stringBuilder.append(b);
                }
            }
        }
        parsed.add(stringBuilder.toString());
        return parsed;
    }

    private void reflectSetValue(Object target, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Class<?> targetClass = target.getClass();
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, newValue);
    }

    public Pair<School, SchoolAddress> getSchoolPair(String line) throws NoSuchFieldException, IllegalAccessException {
        School school = new School();
        SchoolAddress schoolAddress = new SchoolAddress();
        List<String> row = parseCSVLine(line);
        for (int i = 0; i < row.size(); ++i) {
            String column = thead.get(i);
            String value = row.get(i);
            if (schoolMap.containsKey(column)) {
                reflectSetValue(school, schoolMap.get(column), value);
            }
            if (schoolAddressMap.containsKey(column)) {
                reflectSetValue(schoolAddress, schoolAddressMap.get(column), value);
            }
        }
        return Pair.of(school, schoolAddress);
    }

}
