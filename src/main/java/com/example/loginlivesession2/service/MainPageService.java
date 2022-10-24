package com.example.loginlivesession2.service;

import com.amazonaws.services.s3.AmazonS3Client;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final FolderRepository folderRepository;

    private final PhotoRepository photoRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public String createFolder(FolderReqDto folderReqDto, Member member) {
        LocalDate date = LocalDate.parse(folderReqDto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Folder folder = new Folder(folderReqDto.getFolderName(),
                date,
                listToString(folderReqDto.getTag()),
                member);
        folderRepository.save(folder);
        return "생성이 완료되었습니다!";
    }

    @Transactional(readOnly = true)
    public MainPageResDto getMainPage(Member member) {
        List<Folder> folderList = folderRepository.findAllByMemberOrderByDateDesc(member);
        List<FolderSearchResDto> folders = folderList.stream()
                .map(FolderSearchResDto::new)
                .collect(Collectors.toList());

        return new MainPageResDto(folders, topTagRanking(), myTagRanking(member));
    }


    // 태그 많이 된 순서대로 리스트 반환
    private List<Map.Entry<String, Integer>> tagRankingList(List<Folder> folderList) {
        HashMap<String, Integer> hm = new HashMap<>();
        for (Folder folder : folderList) {
            if (folder.getTags().length() == 0) continue;
            String[] tagList = folder.getTags().substring(1).split("#");
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
        if(entryList.size() > 5)  entryList.subList(0,5);
        for (Map.Entry<String, Integer> entry : entryList) {
                topTagsMap.put(entry.getKey(), entry.getValue());
        }
        return topTagsMap;
    }

    // 전체 태그 top5
    private List<String> topTagRanking() {
        List<Folder> allFolderList = folderRepository.findByDateAfter(LocalDate.now().minusDays(7));
        List<Map.Entry<String, Integer>> entryList = tagRankingList(allFolderList);
        List<String> topTags = new ArrayList<>();
        if(entryList.size() > 5)  entryList.subList(0,5);
        for (Map.Entry<String, Integer> entry : entryList) {
            topTags.add(entry.getKey());
        }
        return topTags;
    }

    // 태그 검색
    @Transactional
    public List<FolderSearchResDto> searchTagFolder(String query, Member member) {

        List<Folder> folders = folderRepository.findAllByTagsContainsAndMember(query,member);

        List<FolderSearchResDto> folderSearchResDtoList = new ArrayList<>();
        for (Folder folder : folders) {
            FolderSearchResDto folderSearchResDto = new FolderSearchResDto(folder);
            folderSearchResDtoList.add(folderSearchResDto);
        }
        return folderSearchResDtoList;
    }

    // 폴더 삭제
    @Transactional
    public String deleteFolder(Long id, Member member) {
        Folder folder = folderObject(id);
        authorityCheck(folder, member);
        List<Photo> photos = photoRepository.findAllByFolderId(id);
        for (Photo photo : photos) amazonS3Client.deleteObject(bucketName,photo.getFileName());
        photoRepository.deleteAll(photos);
        folderRepository.deleteById(id);
        return "폴더 삭제";
    }

    private Folder folderObject(Long id) {
        return folderRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404)
        );
    }

    private void authorityCheck(Folder folder, Member member) {
        if (!folder.getMember().getUserId().equals(member.getUserId())) {
            throw new RequestException(ErrorCode.UNAUTHORIZED_401);
        }
    }

    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();
        for (String s : tagList) tag.append(s);
        return tag.toString();
    }
}