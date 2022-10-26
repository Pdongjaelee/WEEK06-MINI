package com.example.loginlivesession2.service.mainpage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.loginlivesession2.dto.requestdto.FolderReqDto;
import com.example.loginlivesession2.dto.responsedto.FolderSearchResDto;
import com.example.loginlivesession2.dto.responsedto.MainPageResDto;
import com.example.loginlivesession2.entity.*;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.FoldertagRepository;
import com.example.loginlivesession2.repository.PhotoRepository;
import com.example.loginlivesession2.repository.TagRepository;
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

    private final TagRepository tagRepository;

    private final AmazonS3Client amazonS3Client;

    private final FoldertagRepository foldertagRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 폴더 생성
    @Transactional
    public String createFolder(FolderReqDto folderReqDto, Member member) {
        // 날짜 형식 맞는지 확인
        try{
            LocalDate.parse(folderReqDto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (Exception e){
            throw new RequestException(ErrorCode.DateTimeException_400);
        }
        LocalDate date = LocalDate.parse(folderReqDto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 들어온 태그 중 중복 제거
        HashSet<String> hashSet = new HashSet<>(folderReqDto.getTag());
        List<String> tagList = new ArrayList<>(hashSet);

        // 폴더 생성
        Folder folder = new Folder(folderReqDto.getFolderName(),
                date,
                listToString(tagList),
                member);
        folderRepository.save(folder);


        // 태그 추가
        for (String tagName : tagList) {

            // 1. tag 엔티티에 존재하는지 확인, 존재하면 해당 tag 객체 가져오기
            Tag tag = tagRepository.findByTagName(tagName).orElse(new Tag(tagName));

            // 2. 존재하지 않으면 새 태그 save, 있으면 plusTag
            if(tagRepository.findByTagName(tagName).isEmpty()){
                tagRepository.save(tag);
            }else{
                tag.plusTag();
            }

            // 3. FolderTag 엔티티에 저장
            foldertagRepository.save(new FolderTag(folder, tagName));

        }
        return "생성이 완료되었습니다!";
    }

    // 메인페이지 조회
    @Transactional(readOnly = true)
    public MainPageResDto getMainPage(Member member) {
        List<Folder> folderList = folderRepository.findAllByMemberOrderByDateDesc(member);
        List<FolderSearchResDto> folders = folderList.stream()
                .map(FolderSearchResDto::new)
                .collect(Collectors.toList());
        // 가장 많은 태그 top5
        List<Tag> tagList = tagRepository.findAllByOrderByCountDesc();
        if(tagList.size()>5) tagList = tagList.subList(0,5);
        List<String> topTags = tagList.stream().map(Tag::getTagName).collect(Collectors.toList());

        // 내가 단 태그 중 가장 많은 태그 top 5
        List<Folder> myFolderList = folderRepository.findAllByMember(member);
        HashMap<String, Integer> hm = new HashMap<>();
        for (Folder folder : myFolderList) {
            List<FolderTag> myTagFolderList = foldertagRepository.findByFolderId(folder.getId());
            for (FolderTag folderTag : myTagFolderList) {
                hm.put(folderTag.getTagName(), hm.getOrDefault(folderTag.getTagName(), 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(hm.entrySet());
        entryList.sort(((o1, o2) -> hm.get(o2.getKey()) - hm.get(o1.getKey())));
        HashMap<String, Integer> myTopTags = new HashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            myTopTags.put(entry.getKey(), entry.getValue());
        }

        return new MainPageResDto(folders,topTags, myTopTags);
    }

/*
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
    }*/


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