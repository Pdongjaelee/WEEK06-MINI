package com.example.loginlivesession2.dto.responsedto;

import com.example.loginlivesession2.entity.Photo;

public class PhotoResDto {

    private Long photoId;

    private String url;

    public PhotoResDto(Photo photo) {
        this.photoId = photo.getId();
        this.url = photo.getUrl();
    }
}
