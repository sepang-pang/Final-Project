package com.team6.finalproject.chat.service.message;

import com.team6.finalproject.chat.dto.message.ChatMessageRequestDto;
import com.team6.finalproject.chat.dto.message.ChatMessageResponseDto;
import com.team6.finalproject.chat.entity.message.ChatMessage;
import com.team6.finalproject.chat.entity.room.ChatRoom;
import com.team6.finalproject.chat.redis.pub.RedisPublisher;
import com.team6.finalproject.chat.repository.message.ChatMessageRepository;
import com.team6.finalproject.chat.service.room.ChatRoomService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Chat Message Service")
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomService chatRoomService;
    private final RedisPublisher redisPublisher;
    private final ChatMessageRepository chatMessageRepository;

    @Override // 메시지 전송
    @Transactional
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto messageDto, User sender) {
        // 채팅방 조회
        log.info("1");
        ChatRoom chatRoom = chatRoomService.findChatRoom(messageDto.getChatRoomId());

        log.info("2");
        ChatMessage message = ChatMessage.builder()
                .content(messageDto.getContent())
                .sender(sender)
                .chatRoom(chatRoom)
                .build();

        log.info("3");
        chatMessageRepository.save(message);

        log.info("4");
        redisPublisher.publish(chatRoom.getRoomId(), messageDto.getContent());


        log.info("5");
        return new ChatMessageResponseDto(message);
    }
}
