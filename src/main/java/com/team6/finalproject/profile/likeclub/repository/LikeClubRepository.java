package com.team6.finalproject.profile.likeclub.repository;

import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeClubRepository extends JpaRepository<LikeClub, Long> {
}
