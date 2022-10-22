package com.example.loginlivesession2.service;

import com.example.loginlivesession2.dto.FolderReqDto;
import com.example.loginlivesession2.dto.FolderSearchResDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final FolderRepository folderRepository;

    @Transactional
    public String createFolder(FolderReqDto folderReqDto, Member member) {
        Folder folder = new Folder(folderReqDto.getFolderName(),
                folderReqDto.getDate(),
                listToString(folderReqDto.getTag()),
                member);
        folderRepository.save(folder);


        return "생성이 완료되었습니다!";
    }

    /*@Transactional
    public MainPageResDto getMainPage(Member member){
        List<Folder> folderList = folderRepository.findAllByMemberOrderByDate(member);
        List<FolderSearchResDto> folders = folderList.stream()
                .map(FolderSearchResDto::new)
                .collect(Collectors.toList());


        return new MainPageResDto(folders,tagRanking(), );
    }*/


    // 전체 tag 순위
    private List<String> tagRanking(){
        List<Folder> allTagList = folderRepository.findAll();
        HashMap<String, Integer> hm = new HashMap<>();
        for (Folder tag : allTagList) {
            String[] tagList = tag.getTags().substring(1).split("#");
            for (String s : tagList) {
                hm.put(s,hm.getOrDefault(s,0)+1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(hm.entrySet());
        entryList.sort(((o1, o2) -> hm.get(o2.getKey()) - hm.get(o1.getKey())));
        List<String> topTags = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : entryList.subList(0,5)){
            topTags.add(entry.getKey());
        }
        return topTags;
    }

    // 내 tag 순위



    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();

        for (String s : tagList) tag.append(s);
        return tag.toString();
    }

    @Transactional
    public List<FolderSearchResDto> searchTagFolder(String query, Member member) {
        List<Folder> folders = folderRepository.findAllByTagsContains(query);
        List<FolderSearchResDto> folderSearchResDtos = new ArrayList<>();

        for (Folder folder : folders) {
            Folder tagFolder = folderRepository.findById(folder.getId()).orElseThrow(
                    () -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404));
            FolderSearchResDto folderSearchResDto = new FolderSearchResDto(tagFolder);
            folderSearchResDtos.add(folderSearchResDto);
        }
        return folderSearchResDtos;

    }
}

