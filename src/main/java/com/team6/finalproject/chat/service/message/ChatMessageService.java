package com.team6.finalproject.chat.service.message;

import com.team6.finalproject.chat.dto.message.ChatMessageRequestDto;
import com.team6.finalproject.chat.dto.message.ChatMessageResponseDto;
import com.team6.finalproject.user.entity.User;

public interface ChatMessageService {

    // 메시지 전송
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto messageDto, User sender);
}
