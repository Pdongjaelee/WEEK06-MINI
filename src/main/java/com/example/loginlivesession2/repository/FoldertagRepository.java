package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.FolderTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoldertagRepository extends JpaRepository<FolderTag,Long> {
    FolderTag findByFolderAndTagName(Folder folder, String s);

    List<FolderTag> findByFolderId(Long folderId);

}
