package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;

import java.util.List;

public interface FolderRepositoryCustom {
    List<Folder> findByKeyword(String keyword, Member member);
}
