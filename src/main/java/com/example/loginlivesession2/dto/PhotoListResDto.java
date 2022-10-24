package com.example.loginlivesession2.dto;

import com.example.loginlivesession2.entity.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class PhotoListResDto {

    private List<Photo> photos;

    public PhotoListResDto(List<Photo> photos) {
        this.photos = photos;
    }
}
