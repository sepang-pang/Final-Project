package com.team6.finalproject.profile.profileinterest.service;

import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.profile.profileinterest.repository.ProfileInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileInterestServiceImpl implements ProfileInterestService{

    private final ProfileInterestRepository profileInterestRepository;

    @Override
    public void save(ProfileInterest profileInterest) {
        profileInterestRepository.save(profileInterest);
    }
}
