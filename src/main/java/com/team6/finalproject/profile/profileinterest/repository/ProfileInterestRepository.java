package com.team6.finalproject.profile.profileinterest.repository;

import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileInterestRepository extends JpaRepository<ProfileInterest, Long> {
}
