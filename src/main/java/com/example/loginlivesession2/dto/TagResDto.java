package com.example.loginlivesession2.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TagResDto {

    private List<String> tags;

    public TagResDto(List<String> tags) {
        this.tags = tags;
    }
}
