package com.example.loginlivesession2.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Tag {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @Lob //긴 문자열까지 받을 수 있게해주는 어노테이션!!
    @Column(nullable = false)
    private String tags;


}
