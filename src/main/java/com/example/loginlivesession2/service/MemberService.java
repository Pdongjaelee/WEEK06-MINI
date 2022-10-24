package com.example.loginlivesession2.service;

import com.example.loginlivesession2.dto.LoginReqDto;
import com.example.loginlivesession2.dto.MemberReqDto;
import com.example.loginlivesession2.dto.MemberResDto;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.entity.RefreshToken;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.jwt.dto.TokenDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.example.loginlivesession2.repository.MemberRepository;
import com.example.loginlivesession2.repository.RefreshTokenRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResDto signup(MemberReqDto memberReqDto) {
        // userId 중복 검사
        if(memberRepository.findByUserId(memberReqDto.getUserId()).isPresent()){
            throw new RequestException(ErrorCode.USERID_DUPLICATION_409);
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPassword()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return new MemberResDto(member);
    }

    @Transactional
    public MemberResDto login(LoginReqDto loginReqDto, HttpServletResponse response) {

        // userId로 해당 Member 찾기
        Member member = findMember(loginReqDto);
        // password 일치여부 확인
        passwordCheck(loginReqDto, member);

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUserId());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(loginReqDto.getUserId());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefresh_Token()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefresh_Token(), loginReqDto.getUserId());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return new MemberResDto(member);

    }
    public String issueToken(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");
        if(!jwtUtil.refreshTokenValidation(refreshToken)){
            throw new RequestException(ErrorCode.JWT_EXPIRED_TOKEN_401);
        }

        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(jwtUtil.getUserId(refreshToken), "Access"));
        return "Success IssuedToken";
    }

    private void passwordCheck(LoginReqDto loginReqDto, Member member) {
        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }
    }

    private Member findMember(LoginReqDto loginReqDto) {
        return memberRepository.findByUserId(loginReqDto.getUserId()).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND_404)
        );
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAuthorization());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefresh_Token());
    }
}
