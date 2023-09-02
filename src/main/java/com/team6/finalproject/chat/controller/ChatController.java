package com.team6.finalproject.chat.controller;

import com.team6.finalproject.chat.dto.message.ChatMessageRequestDto;
import com.team6.finalproject.chat.dto.message.ChatMessageResponseDto;
import com.team6.finalproject.chat.dto.room.ChatRoomDto;

import com.team6.finalproject.chat.entity.message.ChatMessage;
import com.team6.finalproject.chat.redis.pub.RedisPublisher;
import com.team6.finalproject.chat.repository.room.ChatRoomRepository;
import com.team6.finalproject.chat.service.message.ChatMessageService;
import com.team6.finalproject.chat.service.room.ChatRoomService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {

    private final ChatMessageService chatService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/chat/room") // 채팅방 개설
    public ResponseEntity<ApiResponseDto> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(chatRoomDto.getReceiverId(),userDetails.getUser());
    }

    @PostMapping("/chat/message") // 채팅 전송
    public ChatMessageResponseDto sendMessage(@RequestBody ChatMessageRequestDto messageDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.sendMessage(messageDto, userDetails.getUser());
    }
}





