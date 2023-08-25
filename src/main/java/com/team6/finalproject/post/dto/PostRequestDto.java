package com.team6.finalproject.post.dto;

import com.team6.finalproject.post.entity.PostTypeEnum;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private PostTypeEnum postType;
}
