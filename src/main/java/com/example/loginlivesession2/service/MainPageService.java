package com.example.loginlivesession2.service;

import com.example.loginlivesession2.dto.FolderReqDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final FolderRepository folderRepository;

    public String createFolder(FolderReqDto folderReqDto, Member member) {
        Folder folder = new Folder(folderReqDto.getFolderName(),
                folderReqDto.getDate(),
                listToString(folderReqDto.getTag()),
                member);

        folderRepository.save(folder);
        return "생성이 완료되었습니다!";
    }

    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();

        for (String s : tagList) tag.append(s);
        return tag.toString();
    }
}

