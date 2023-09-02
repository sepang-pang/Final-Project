package com.team6.finalproject.chat.repository.room;

import com.team6.finalproject.chat.entity.room.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    public Optional<ChatRoom> findByActiveId(Long chatRoomId);
}
