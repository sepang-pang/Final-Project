package com.team6.finalproject.profile.repository;

import com.team6.finalproject.profile.entity.Profile;

import java.util.Optional;

public interface ProfileRepositoryCustom {
    public Optional<Profile> existValidLocate(Long id);
    public Optional<Profile> existValidInterest(Long id);
    public Optional<Profile> existValidProfile(Long id);
}
