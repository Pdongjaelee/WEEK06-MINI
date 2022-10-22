package com.example.loginlivesession2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Folder {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String folderName;

    @Column(nullable = false)
    private String date;

    @Column
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Folder(String folderName, String date, String tag, Member member) {
        this.folderName = folderName;
        this.date = date;
        this.tags = tag;
        this.member = member;
    }

    // tag 업데이트
    public void updateTag(String tag){
        this.tags = tag;
    }

}
