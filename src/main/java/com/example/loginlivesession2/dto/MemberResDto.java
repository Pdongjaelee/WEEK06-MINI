package com.example.loginlivesession2.dto;

import com.example.loginlivesession2.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class MemberResDto {
    private Long id;
    private String userId;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
    }
}
