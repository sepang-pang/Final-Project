package com.team6.finalproject.meeting.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.common.entity.DateConstants;
import com.team6.finalproject.meeting.entity.Meeting;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
public class MeetingResponseDto {

    private String title;
    private String description;
    private String media;
    private int maxMember;
    private int memberCount; // 참여자
    private String place;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private int commentCount;

    @JsonSerialize(using = DateSerializer.class)
    private LocalDateTime date;

    @JsonSerialize(using = DateDetailSerializer.class)
    private LocalDateTime dateDetail;

    public MeetingResponseDto(Meeting meeting) {
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.media = meeting.getMedia();
        this.maxMember = meeting.getMaxMember();
        this.date = meeting.getDate();
        this.dateDetail = meeting.getDate();
        this.place = meeting.getPlace();
        this.isCompleted = meeting.getIsCompleted();
        this.isDeleted = meeting.getIsDeleted();
        this.createAt = meeting.getCreatedAt();
        this.modifiedAt = meeting.getModifiedAt();
        this.commentCount = meeting.getMeetingComments().size();
        this.memberCount = meeting.getMeetingUsers().size();

    }

    public static class DateSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DateConstants.RESPONSE_FORMATTER));
        }
    }

    public static class DateDetailSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DateConstants.RESPONSE_FORMATTER_DETAIL));
        }
    }

}
