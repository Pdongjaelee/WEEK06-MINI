package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long>, FolderRepositoryCustom {


    List<Folder> findAllByMemberOrderByDateDesc(Member member);

    List<Folder> findByMember(Member member);

}
