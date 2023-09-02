package com.team6.finalproject.chat.service.room;

import com.team6.finalproject.chat.entity.room.ChatRoom;
import com.team6.finalproject.chat.repository.room.ChatRoomRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.user.entity.User;
import com.team6.finalproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository; // 추후 수정

    @Override
    @Transactional // 채팅방 개설
    public ResponseEntity<ApiResponseDto> createChatRoom(Long receiverId, User sender) {
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("수신자를 찾을 수 없습니다."));

        chatRoomRepository.save(
                ChatRoom.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .build());

        return ResponseEntity.ok().body(new ApiResponseDto("채팅방 개설 완료!", 200));
    }

    @Override // 채팅방 조회
    public ChatRoom findChatRoom (Long chatRoomId) {
        return chatRoomRepository.findByActiveId(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
    }
}
