package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/{folderId}")
    public ResponseDto<?> addPhotos(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                      @PathVariable Long folderId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseDto.success(folderService.addPhotos(multipartFile, folderId, userDetails.getAccount()));
    }

}
