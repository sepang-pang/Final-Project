package com.team6.finalproject.club.apply.repository;

import com.team6.finalproject.club.apply.entity.ApplyJoinClub;

import java.util.List;
import java.util.Optional;

public interface ApplyJoinClubRepositoryCustom {

    public Optional<ApplyJoinClub> findByActiveId(Long id);
    public Optional<ApplyJoinClub> findByPendingApplication(Long userId, Long clubId);
    List<ApplyJoinClub> findByActiveClubId(Long clubId);
}
