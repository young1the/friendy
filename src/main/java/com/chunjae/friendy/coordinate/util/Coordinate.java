package com.chunjae.friendy.coordinate.util;

import com.chunjae.friendy.school.entity.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "Coordinate")
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private double latitude;
    private double longitude;

    @OneToOne
    @JoinColumn(name = "school_id") // school_id는 실제 테이블의 외래 키 컬럼 이름에 맞추세요.
    private School school;



    // 기본 생성자
    public Coordinate() {
    }

    /**
     * 문자열로 위도와 경도를 입력해서 Coordinate 객체를 생성합니다.
     * @param latitude : 위도
     * @param longitude : 경도
     */
    public Coordinate(String latitude, String longitude) {
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }


    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getRadianLatitude() {
        return Math.toRadians(this.latitude);
    }

    public double getRadianLongitude() {
        return Math.toRadians(this.longitude);
    }

}
