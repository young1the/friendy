package com.chunjae.friendy.user;

import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final SchoolAddressRepository schoolAddressRepository;

    public List<UserSearchResponseInterface> searchKeyword(String keyword) {

        List<UserSearchResponseInterface> searchKeywords = schoolAddressRepository.findSchoolNamesByKeyword(keyword);

        return searchKeywords;
    }


}
