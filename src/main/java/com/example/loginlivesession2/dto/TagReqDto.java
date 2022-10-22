package com.example.loginlivesession2.dto;

import com.example.loginlivesession2.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TagReqDto {
    private List<String> tagList;
}
