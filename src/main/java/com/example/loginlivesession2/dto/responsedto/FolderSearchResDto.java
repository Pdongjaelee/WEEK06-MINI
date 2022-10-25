package com.example.loginlivesession2.dto.responsedto;

import com.example.loginlivesession2.entity.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FolderSearchResDto {
    private Long id;
    private String folderName;

    public FolderSearchResDto(Folder folder) {
        this.id = folder.getId();
        this.folderName = folder.getFolderName();
    }
}
