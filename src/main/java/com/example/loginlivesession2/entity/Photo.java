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
public class Photo extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String fileName;

    public Photo(Folder folder, String url, String fileName) {
        this.folder = folder;
        this.url = url;
        this.fileName = fileName;
    }
}
