package com.example.loginlivesession2.service.mainpage;

import com.example.loginlivesession2.entity.*;
import com.example.loginlivesession2.repository.FolderRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.loginlivesession2.entity.QFolder.folder;
import static com.example.loginlivesession2.entity.QFolderTag.folderTag;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class FolderRepositoryImpl implements FolderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Folder> findByKeyword(String keyword, Member member) {
        QFolder folder = QFolder.folder;
        QFolderTag folderTag = QFolderTag.folderTag;

        List<FolderTag> folderTagList = queryFactory.selectFrom(folderTag)
                .leftJoin(folderTag.folder,folder).fetchJoin()
                .where(folder.member.eq(member)) //본인 쓴 폴더만 조회
                .where(search(keyword)) //파일이름 or 태그 검색
                .orderBy(folder.date.desc()) // 날짜 내림차순 정렬
                .fetch();

        return folderTagList.stream()
                .map(FolderTag::getFolder)
                .distinct() // 중복제거
                .collect(Collectors.toList());
    }

    private BooleanBuilder search(String keyword){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(hasText(keyword)){
            booleanBuilder.or(folder.folderName.contains(keyword));
        }
        if(hasText(keyword)){
            booleanBuilder.or(folderTag.tagName.contains(keyword));
        }
        return booleanBuilder;
    }


}
