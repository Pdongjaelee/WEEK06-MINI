package com.example.loginlivesession2.service.mainpage;

import com.example.loginlivesession2.dto.responsedto.FolderSearchResDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.entity.QFolder;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.FoldertagRepository;
import com.example.loginlivesession2.repository.TagRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchFolderService {

    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public List<FolderSearchResDto> searchTagFolder(String query, Member member) {

        List<Folder> folders = folderRepository.findByKeyword(query, member);

        return folders.stream().map(FolderSearchResDto::new).collect(Collectors.toList());
    }

}