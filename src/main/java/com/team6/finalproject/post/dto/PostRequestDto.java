package com.team6.finalproject.post.dto;

import com.team6.finalproject.post.entity.PostTypeEnum;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private PostTypeEnum postType;
    private MultipartFile media;
    private Long ClubId;

}
