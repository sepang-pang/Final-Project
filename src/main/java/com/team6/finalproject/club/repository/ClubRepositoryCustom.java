package com.team6.finalproject.club.repository;

import com.team6.finalproject.club.entity.Club;

import java.util.Optional;

public interface ClubRepositoryCustom {
    public Optional<Club> findByActiveId(Long id);
    public Optional<Club> findByActiveClubName(String name);
    public Optional<Club> findByActiveIdAndUsername(Long id, String username);
}