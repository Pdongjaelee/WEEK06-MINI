package com.example.loginlivesession2.dto;

import lombok.Getter;

@Getter
public class TagResDto {

    private String tags;

    public TagResDto(String tags) {
        this.tags = tags;
    }
}
