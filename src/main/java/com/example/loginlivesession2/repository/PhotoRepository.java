package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByFolderId(Long folderId);
}
