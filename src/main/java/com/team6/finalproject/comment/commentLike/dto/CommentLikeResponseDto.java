package com.team6.finalproject.comment.commentLike.dto;

import com.team6.finalproject.comment.dto.CommentResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentLikeResponseDto {
    private List<CommentResponseDto> commentResponseDto;
    }
