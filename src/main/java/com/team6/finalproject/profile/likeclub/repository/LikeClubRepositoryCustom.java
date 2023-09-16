package com.team6.finalproject.profile.likeclub.repository;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.likeclub.entity.LikeClub;

public interface LikeClubRepositoryCustom {
    // 클럽,프로필으로 관심 동호회 가져오기
    LikeClub findByClubAndProfile(Club club, Profile profile);
}
