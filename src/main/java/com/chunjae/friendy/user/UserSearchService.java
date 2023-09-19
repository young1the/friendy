package com.chunjae.friendy.user;

import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import com.chunjae.friendy.user.dto.HighSearchResponse;
import com.chunjae.friendy.user.dto.MiddleSearchResponse;
import com.chunjae.friendy.user.dto.UserSearchResponseInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final SchoolAddressRepository schoolAddressRepository;
    private final RestTemplate restTemplate;

    public List<UserSearchResponseInterface> searchKeyword(String keyword) {

        List<UserSearchResponseInterface> searchKeywords = schoolAddressRepository.findSchoolNamesByKeyword(keyword);

        return searchKeywords;
    }

    public List<MiddleSearchResponse> getSearchListFromMiddleServer(String requestURL){
        ResponseEntity<List<MiddleSearchResponse>> response = restTemplate.exchange(
                requestURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<MiddleSearchResponse>>() {
                });
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }else{
            throw new RuntimeException("HTTP request failed with status code: " + response.getStatusCode());
        }
    }

    public List<HighSearchResponse> getSearchListFromHighServer(String requestURL) {

        ResponseEntity<List<HighSearchResponse>> response = restTemplate.exchange(
                requestURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<HighSearchResponse>>() {
                });
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }else{
            throw new RuntimeException("HTTP request failed with status code: " + response.getStatusCode());
        }
    }
}
