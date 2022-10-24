package com.example.loginlivesession2.dto;

import com.example.loginlivesession2.entity.Folder;
import com.example.loginlivesession2.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class FolderResDto {
    //List<HashMap<String,Integer>> photos;
    //List<String> tags;
//    private String photo;

    private Long id;
    private String photos;

    public FolderResDto(Photo photo) {
        this.id = photo.getId();
        this.photos = photo.getUrl();

    }
}
