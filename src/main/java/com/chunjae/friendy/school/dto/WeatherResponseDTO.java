package com.chunjae.friendy.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDTO {
    @JsonProperty("response")
    private Response response;
}

@Getter
@Setter
class Response {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("body")
    private Body body;
}

@Getter
@Setter
class Header {
    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("resultMsg")
    private String resultMsg;
}

@Getter
@Setter
class Body {
    @JsonProperty("dataType")
    private String dataType;
    @JsonProperty("items")
    private Items items;
    @JsonProperty("pageNo")
    private String pageNo;
    @JsonProperty("numOfRows")
    private String numOfRows;
    @JsonProperty("totalCount")
    private String totalCount;
}

@Getter
@Setter
class Items {
    @JsonProperty("item")
    private List<Item> item;
}

@Getter
@Setter
class Item {
    @JsonProperty("baseDate")
    private String baseDate;
    @JsonProperty("baseTime")
    private String baseTime;
    @JsonProperty("category")
    private String category;
    @JsonProperty("fcstDate")
    private String fcstDate;
    @JsonProperty("fcstTime")
    private String fcstTime;
    @JsonProperty("fcstValue")
    private String fcstValue;
    @JsonProperty("nx")
    private String nx;
    @JsonProperty("ny")
    private String ny;
}



