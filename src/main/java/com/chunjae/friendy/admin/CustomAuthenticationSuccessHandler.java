package com.chunjae.friendy.admin;

import com.chunjae.friendy.admin.entity.AdminLog;
import com.chunjae.friendy.admin.repository.AdminLogRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AdminLogRepository adminLogRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //인증된 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //사용자의 IP 주소 가져오기
        String ipAddr = RemoteIPUtils.getRemoteIP(request);

        //사용자 log기록
        AdminLog adminLog =  new AdminLog();
        adminLog.setAccessDate(LocalDateTime.now());
        adminLog.setUserName(userDetails.getUsername());
        adminLog.setIpAddress(ipAddr);
        adminLogRepository.save(adminLog);

        response.sendRedirect("/school/search");

    }
}
