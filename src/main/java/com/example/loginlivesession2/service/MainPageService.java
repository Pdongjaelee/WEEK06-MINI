package com.example.loginlivesession2.service;

import com.example.loginlivesession2.dto.FolderReqDto;
import com.example.loginlivesession2.dto.FolderSearchResDto;
import com.example.loginlivesession2.dto.MainPageResDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.entity.Photo;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final FolderRepository folderRepository;

    private final PhotoRepository photoRepository;

    @Transactional
    public String createFolder(FolderReqDto folderReqDto, Member member) {
        Folder folder = new Folder(folderReqDto.getFolderName(),
                folderReqDto.getDate(),
                listToString(folderReqDto.getTag()),
                member);
        folderRepository.save(folder);


        return "생성이 완료되었습니다!";
    }

    @Transactional(readOnly = true)
    public MainPageResDto getMainPage(Member member) {
        List<Folder> folderList = folderRepository.findAllByMemberOrderByDate(member);
        List<FolderSearchResDto> folders = folderList.stream()
                .map(FolderSearchResDto::new)
                .collect(Collectors.toList());

        return new MainPageResDto(folders, topTagRanking(), myTagRanking(member));
    }


    // 태그 많이 된 순서대로 리스트 반환
    private List<Map.Entry<String, Integer>> tagRankingList(List<Folder> folderList) {
        HashMap<String, Integer> hm = new HashMap<>();
        for (Folder tag : folderList) {
            if (tag.getTags().length() == 0) continue;
            String[] tagList = tag.getTags().substring(1).split("#");
            for (String s : tagList) {
                hm.put(s, hm.getOrDefault(s, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(hm.entrySet());
        entryList.sort(((o1, o2) -> hm.get(o2.getKey()) - hm.get(o1.getKey())));
        return entryList;
    }

    // 내 태그 top5

    private HashMap<String, Integer> myTagRanking(Member member) {
        List<Folder> myFolderList = folderRepository.findAllByMember(member);
        List<Map.Entry<String, Integer>> entryList = tagRankingList(myFolderList);
        HashMap<String, Integer> topTagsMap = new HashMap<>();
        if (entryList.size() < 5) {
            for (Map.Entry<String, Integer> entry : entryList) {
                topTagsMap.put(entry.getKey(), entry.getValue());
            }
        } else {
            for (Map.Entry<String, Integer> entry : entryList.subList(0, 5)) {
                topTagsMap.put(entry.getKey(), entry.getValue());
            }
        }
        return topTagsMap;
    }

    // 전체 태그 top5
    private List<String> topTagRanking() {
        List<Folder> allFolderList = folderRepository.findAll();
        List<Map.Entry<String, Integer>> entryList = tagRankingList(allFolderList);
        List<String> topTags = new ArrayList<>();
        if (entryList.size() < 5) {
            for (Map.Entry<String, Integer> entry : entryList) {
                topTags.add(entry.getKey());
            }
        } else {
            for (Map.Entry<String, Integer> entry : entryList.subList(0, 5)) {
                topTags.add(entry.getKey());
            }
        }
        return topTags;
    }


    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();

        for (String s : tagList) tag.append(s);
        return tag.toString();
    }

    // 폴더 삭제
    @Transactional
    public String deleteFolder(Long id, Member member) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404));
        authorityCheck(folder, member);
        List<Photo> photos = photoRepository.findAllByFolderId(id);
        photoRepository.deleteAll(photos);
        folderRepository.deleteById(id);
        return "폴더 삭제";
    }

    private void authorityCheck(Folder folder, Member member) {
        if (!folder.getMember().getUserId().equals(member.getUserId())) {
            throw new RequestException(ErrorCode.UNAUTHORIZED_401);
        }
    }
}

