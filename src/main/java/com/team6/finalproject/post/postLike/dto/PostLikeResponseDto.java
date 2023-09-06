package com.team6.finalproject.post.postLike.dto;

import com.team6.finalproject.post.dto.ClubPostResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PostLikeResponseDto {
    private List<ClubPostResponseDto> clubPostsList;
    }
