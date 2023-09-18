package com.chunjae.friendy.admin;

import com.chunjae.friendy.admin.dto.AdminCreateRequest;
import com.chunjae.friendy.admin.entity.Admin;
import com.chunjae.friendy.admin.entity.AdminRole;
import com.chunjae.friendy.admin.entity.Role;
import com.chunjae.friendy.admin.repository.AdminRepository;
import com.chunjae.friendy.admin.repository.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRoleRepository adminRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> admin = this.adminRepository.findByusername(username);

        if(admin.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Admin adminUser = admin.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.SUPER.getRoleName()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(Role.NORMAL.getRoleName()));
        }

        return new User(adminUser.getUsername(), adminUser.getPassword(), authorities);

    }

    public Admin createAdmin(AdminCreateRequest adminCreateRequest){


        AdminRole adminRole = adminRoleRepository.findById(adminCreateRequest.getAdminRole())
                .orElseThrow(() -> new UsernameNotFoundException("AdminRole not found"));

        Admin admin = new Admin();
        admin.setUsername(adminCreateRequest.getUsername());
        admin.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword1()));
        admin.setName(adminCreateRequest.getName());
        admin.setEmpNumber(adminCreateRequest.getEmpNumber());
        admin.setAdminRoleIndex(adminRole);

        adminRepository.save(admin);
        return admin;
    }

}
