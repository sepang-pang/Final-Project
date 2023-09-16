package com.team6.finalproject.meeting_user.repository;

import com.team6.finalproject.meeting_user.entity.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingUserRepository extends JpaRepository<MeetingUser,Long>,MeetingUserRepositoryCustom{
}
