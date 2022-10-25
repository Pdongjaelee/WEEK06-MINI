package com.example.loginlivesession2.dto.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@Builder
public class FolderPageResDto {

    private List<FolderResDto> photos;
    private List<String> tags;

    public FolderPageResDto(List<FolderResDto> photos, List<String> tags) {
        this.photos = photos;
        this.tags = tags;
    }
}
