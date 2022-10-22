package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.FolderReqDto;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainpage")
public class MainPageController {

    private final MainPageService mainPageService;

    @PostMapping
    public ResponseDto<?> createFolder(@RequestBody FolderReqDto folderReqDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(mainPageService.createFolder(folderReqDto, userDetails.getAccount()));
    }

    @GetMapping
    public ResponseDto<?> getMainPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(mainPageService.getMainPage(userDetails.getAccount()));
    }




}
