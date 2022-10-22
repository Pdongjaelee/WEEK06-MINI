package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.FolderReqDto;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.FolderService;
import com.example.loginlivesession2.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainpage")
public class MainPageController {

    private final MainPageService mainPageService;

    @PostMapping
    public ResponseDto<?> createFolder(@RequestBody FolderReqDto folderReqDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(mainPageService.createFolder(folderReqDto, userDetails.getAccount()));
    }



}
