package com.team6.finalproject.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubPostRequestDto {
    private String title;
    private String content;
    private Long clubId;

}
