package com.team6.finalproject.meeting.repository;

import com.team6.finalproject.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long>,MeetingRepositoryCustom{
}
