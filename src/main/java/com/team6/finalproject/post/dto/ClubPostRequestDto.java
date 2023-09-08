package com.team6.finalproject.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ClubPostRequestDto {
    private String title;
    private String content;
    private Long clubId;
    private MultipartFile file;
}
