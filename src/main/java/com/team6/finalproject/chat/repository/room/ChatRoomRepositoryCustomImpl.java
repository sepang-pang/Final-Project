package com.team6.finalproject.chat.repository.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team6.finalproject.chat.entity.room.ChatRoom;
import com.team6.finalproject.chat.entity.room.QChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team6.finalproject.chat.entity.room.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ChatRoom> findByActiveId(Long chatRoomId) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(chatRoom)
                                .where(chatRoom.roomId.eq(chatRoomId),
                                        chatRoom.isDeleted.eq(false))
                                .fetchOne());
    }
}
