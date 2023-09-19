package com.chunjae.friendy.admin.repository;

import com.chunjae.friendy.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByusername(String username);
    Admin findByUsername(String username);

    Admin findByIdx(Long idx);

}
