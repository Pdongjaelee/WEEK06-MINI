package com.example.loginlivesession2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FolderReqDto {

    private String date;
    private String folderName;
    private List<String> tag;
}
