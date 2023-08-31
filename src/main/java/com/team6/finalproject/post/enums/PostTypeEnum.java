package com.team6.finalproject.post.enums;

import lombok.Getter;

@Getter
public enum PostTypeEnum {
    LOUNGE(PostType.LOUNGE),
    CLUB(PostType.CLUB);

    private final String postType;

    PostTypeEnum(String postType) {
        this.postType = postType;
    }

    private static class PostType {
        public static final String LOUNGE = "LOUNGE";
        public static final String CLUB = "CLUB";
    }
}

