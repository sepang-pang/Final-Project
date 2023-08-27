package com.team6.finalproject.club.apply.repository;

import com.team6.finalproject.club.apply.entity.ApplyJoinClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyJoinClubRepository extends JpaRepository<ApplyJoinClub, Long>, ApplyJoinClubRepositoryCustom {
}
