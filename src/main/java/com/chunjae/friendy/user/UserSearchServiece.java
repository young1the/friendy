package com.chunjae.friendy.user;

import com.chunjae.friendy.school.entity.SchoolAddress;
import com.chunjae.friendy.school.repository.SchoolAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSearchServiece {

    private final SchoolAddressRepository schoolAddressRepository;

    public List<UserSearchResponseInterface> searchKeyword(String keyword) {

        List<UserSearchResponseInterface> searchKeywords = schoolAddressRepository.findSchoolNamesByKeyword(keyword);

        return searchKeywords;
    }


}
