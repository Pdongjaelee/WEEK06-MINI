package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.FolderTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoldertagRepository extends JpaRepository<FolderTag,Long> {
    List<FolderTag> findByFolderId(Long folderId);
}
