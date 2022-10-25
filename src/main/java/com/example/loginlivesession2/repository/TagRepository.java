package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagNameEquals(String folderTag);
}
