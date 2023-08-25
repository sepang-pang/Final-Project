package com.team6.finalproject.club.repository;

import com.team6.finalproject.club.entity.Club;

import java.util.Optional;

public interface ClubRepositoryCustom {
    public Optional<Club> findActiveClubById(Long id);
    public Optional<Club> findActiveClubByName(String name);
    public Optional<Club> findActiveByIdAndUsername(Long id, String username);
}