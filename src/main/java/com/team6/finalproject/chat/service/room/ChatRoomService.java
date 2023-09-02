package com.team6.finalproject.chat.service.room;

import com.team6.finalproject.chat.entity.room.ChatRoom;
import com.team6.finalproject.chat.repository.room.ChatRoomRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface ChatRoomService {

    // 채팅방 개설
    public ResponseEntity<ApiResponseDto> createChatRoom(Long receiverId, User sender);

    // 채팅방 조회
    public ChatRoom findChatRoom (Long chatRoomId);
}
