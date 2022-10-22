package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Photo;
import com.example.loginlivesession2.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByFolderId(Long folderId);
}
