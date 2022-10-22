package com.example.loginlivesession2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Getter
@NoArgsConstructor
@Builder
public class FolderPageResDto {

    private List<FolderResDto> photos;
    private List<TagResDto> tags;

    public FolderPageResDto(List<FolderResDto> photos, List<TagResDto> tags) {
        this.photos = photos;
        this.tags = tags;
    }
}
