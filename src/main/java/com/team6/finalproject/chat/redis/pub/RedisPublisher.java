package com.team6.finalproject.chat.redis.pub;

import com.team6.finalproject.chat.entity.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher { // 메시지를 발행하는 기능

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public void publish(Long chatRoomId, String message) {
        redisTemplate.convertAndSend(topic.getTopic(), new ChatMessage(chatRoomId, message));
    }
}
