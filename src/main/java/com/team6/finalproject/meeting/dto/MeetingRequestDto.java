package com.team6.finalproject.meeting.dto;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.common.entity.DateConstants;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
public class MeetingRequestDto {
    private String name;
    private String description;
    private int maxMember;
    private ActivityTypeEnum ACTIVITY_TYPE;
    private String place;

    @JsonDeserialize(using = com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DateConstants.REQUEST_FORMATTER);
        }
    }
}
