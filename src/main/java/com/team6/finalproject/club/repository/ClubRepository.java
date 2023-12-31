package com.team6.finalproject.club.repository;

import com.team6.finalproject.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {
}
