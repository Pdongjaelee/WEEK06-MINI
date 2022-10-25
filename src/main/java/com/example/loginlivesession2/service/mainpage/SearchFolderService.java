package com.example.loginlivesession2.service.mainpage;

import com.example.loginlivesession2.dto.responsedto.FolderSearchResDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.FoldertagRepository;
import com.example.loginlivesession2.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchFolderService {

    private final FolderRepository folderRepository;

    private final TagRepository tagRepository;


    private final FoldertagRepository foldertagRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 태그 검색
    @Transactional
    public List<FolderSearchResDto> searchTagFolder(String query, Member member) {

        List<Folder> folders = folderRepository.findAllByTagsContainsAndMember(query, member);

        List<FolderSearchResDto> folderSearchResDtoList = new ArrayList<>();
        for (Folder folder : folders) {
            FolderSearchResDto folderSearchResDto = new FolderSearchResDto(folder);
            folderSearchResDtoList.add(folderSearchResDto);
        }
        return folderSearchResDtoList;
    }

}