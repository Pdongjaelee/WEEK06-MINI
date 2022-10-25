package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.responsedto.FolderPageResDto;
import com.example.loginlivesession2.dto.requestdto.TagReqDto;
import com.example.loginlivesession2.global.ResponseDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder/{folderId}")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<ResponseDto<String>> addPhotos(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                                    @PathVariable Long folderId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return new ResponseEntity<>(ResponseDto.success(folderService.addPhotos(multipartFile, folderId, userDetails.getAccount())), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseDto<String> updateTag(@RequestBody TagReqDto tagReqDto,
                                    @PathVariable Long folderId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(folderService.updateTag(folderId, tagReqDto, userDetails.getAccount()));
    }

    @GetMapping
    public ResponseDto<FolderPageResDto> showFolderPage(@PathVariable Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(folderService.showFolderPage(folderId, userDetails.getAccount()));
    }

    @DeleteMapping
    public ResponseDto<String> deletePhotos(@PathVariable Long folderId,@RequestParam(value = "photoId", required = true) List<Long>photoId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(folderService.deletePhotos(folderId,photoId,userDetails.getAccount()));
    }

}
