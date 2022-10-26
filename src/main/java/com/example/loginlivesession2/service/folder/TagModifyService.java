package com.example.loginlivesession2.service.folder;

import com.example.loginlivesession2.dto.requestdto.TagReqDto;
import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.FolderTag;
import com.example.loginlivesession2.entity.Member;
import com.example.loginlivesession2.entity.Tag;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.exception.RequestException;
import com.example.loginlivesession2.repository.FolderRepository;
import com.example.loginlivesession2.repository.FoldertagRepository;
import com.example.loginlivesession2.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagModifyService {
    private final FolderRepository folderRepository;

    private final TagRepository tagRepository;

    private final FoldertagRepository foldertagRepository;

    @Transactional
    public String updateTag(Long folderId, TagReqDto tagReqDto, Member member){
        Folder folder = folderObject(folderId);
        authorityCheck(folder, member);

        // 들어온 태그 중 중복 제거
        HashSet<String> hashSet = new HashSet<>(tagReqDto.getTag());
        List<String> newTagList = new ArrayList<>(hashSet);


        // 1. folderTag 엔티티에서 findbyFolderId.getTagName()
        List<FolderTag> folderTagList = foldertagRepository.findByFolderId(folderId);
        List<String> TagList = folderTagList.stream()
                .map(FolderTag::getTagName).collect(Collectors.toList());


        // 2. 들어온 tagName이 폴더에 있는 태그 중에 일치하는 게 없으면 folderTag 엔티티 추가 (save)
        for (String s : newTagList) {
            if(!TagList.contains(s)){
                foldertagRepository.save(new FolderTag(folder, s));

                // Tag 엔티티에 존재하지 않으면 새 태그 save, 있으면 plusTag
                Tag tag = tagRepository.findByTagName(s).orElse(new Tag(s));

                // 2. 존재하지 않으면 새 태그 save, 있으면 plusTag
                if(tagRepository.findByTagName(s).isEmpty()){
                    tagRepository.save(tag);
                }else{
                    tag.plusTag();
                }
            }
        }

        // 3. tagName이 폴더에 있는 태그 중, 들어온 tagName 중에 없는 것 삭제
        for (String s : TagList) {
            if(!newTagList.contains(s)){
                Tag tag = tagRepository.findByTagName(s).orElse(new Tag(s));
                foldertagRepository.delete(foldertagRepository.findByFolderAndTagName(folder, s));
                // Tag 엔티티에서 -1
                tag.minusTag();
            }
        }

        return "수정 완료";
    }

    private Folder folderObject(Long id) {
        return folderRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.FOLDER_ID_NOT_FOUND_404)
        );
    }

    private void authorityCheck(Folder folder, Member member) {
        if(!folder.getMember().getUserId().equals(member.getUserId())){
            throw new RequestException(ErrorCode.UNAUTHORIZED_401);
        }
    }

    private String listToString(List<String> tagList) {
        StringBuilder tag = new StringBuilder();
        for (String s : tagList) tag.append(s);
        return tag.toString();
    }

}
