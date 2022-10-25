package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.requestdto.FolderReqDto;
import com.example.loginlivesession2.dto.responsedto.FolderSearchResDto;
import com.example.loginlivesession2.dto.responsedto.MainPageResDto;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.member.mainpage.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainpage")
public class MainPageController {

    private final MainPageService mainPageService;

    // 폴더 생성
    @PostMapping
    public ResponseEntity<ResponseDto<String>> createFolder(@RequestBody FolderReqDto folderReqDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(ResponseDto.success(mainPageService.createFolder(folderReqDto, userDetails.getAccount())), HttpStatus.CREATED);
    }

    // 메인 페이지에서 태그 검색
    @GetMapping("/search")
    public ResponseDto<List<FolderSearchResDto>> searchTagFolder(@RequestParam String query,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(mainPageService.searchTagFolder(query, userDetails.getAccount()));
    }

    // 메인페이지 보여주기, 폴더, 폴더명, 전체 이용자 태그 top5, 내 태그 top5
    @GetMapping
    public ResponseDto<MainPageResDto> getMainPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(mainPageService.getMainPage(userDetails.getAccount()));
    }

    // 폴더 삭제
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteFolder(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(mainPageService.deleteFolder(id, userDetails.getAccount()));
    }
}
