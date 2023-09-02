package com.team6.finalproject.chat.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {
    private String content;
    private Long chatRoomId;
}
