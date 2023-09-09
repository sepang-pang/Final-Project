package com.team6.finalproject.profile.profileinterest.repository;

import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileInterestRepository extends JpaRepository<ProfileInterest, Long> {
    List<ProfileInterest> findByProfile(Profile profile);
}
