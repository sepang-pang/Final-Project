package com.team6.finalproject.club.repository;

import com.team6.finalproject.club.entity.Club;

import java.util.Optional;

public interface ClubRepositoryCustom {
    public Optional<Club> findById(Long id);
    public Optional<Club> findByName(String name);
    public Optional<Club> findByIdAndUsername(Long id, String username);
}