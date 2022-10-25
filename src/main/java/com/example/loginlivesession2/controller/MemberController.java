package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.LoginReqDto;
import com.example.loginlivesession2.dto.MemberReqDto;
import com.example.loginlivesession2.dto.MemberResDto;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.service.MemberService;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<ResponseDto<MemberResDto>> signup(@RequestBody @Valid MemberReqDto memberReqDto) {
        return new ResponseEntity<>(ResponseDto.success(memberService.signup(memberReqDto)),HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseDto<MemberResDto> login(@RequestBody @Valid LoginReqDto loginReqDto, HttpServletResponse response) {
        return ResponseDto.success(memberService.login(loginReqDto, response));
    }

    // 토큰 재발급
    @GetMapping("/issue/token")
    public ResponseDto<String> issuedToken(HttpServletRequest request, HttpServletResponse response){
        return ResponseDto.success(memberService.issueToken(request,response));
    }
}
