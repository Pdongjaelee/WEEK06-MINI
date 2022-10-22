package com.example.loginlivesession2.jwt.filter;

import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if(accessToken != null) {
            if(!jwtUtil.tokenValidation(accessToken)){
                jwtExceptionHandler(response);
                return;
            }
            setAuthentication(jwtUtil.getUserId(accessToken));
        }else if(refreshToken != null) {
            if(!jwtUtil.refreshTokenValidation(refreshToken)){
                jwtExceptionHandler(response);
                return;
            }
            setAuthentication(jwtUtil.getUserId(refreshToken));
        }

        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String UserId) {
        Authentication authentication = jwtUtil.createAuthentication(UserId);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            String json = new ObjectMapper().writeValueAsString(ResponseDto.fails(HttpStatus.UNAUTHORIZED,"TOKEN이 만료되었습니다"));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
