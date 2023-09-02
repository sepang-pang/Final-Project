package com.team6.finalproject.chat.dto.message;

import com.team6.finalproject.chat.entity.message.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String sender;
    private String content;
    private LocalDateTime sendTime;

    public ChatMessageResponseDto(ChatMessage message) {
        this.sender = message.getSender().getProfile().getNickname();
        this.content = message.getContent();
        this.sendTime = message.getCreatedAt();
    }
}
