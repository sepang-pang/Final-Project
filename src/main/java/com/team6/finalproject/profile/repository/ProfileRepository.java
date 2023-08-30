package com.team6.finalproject.profile.repository;

import com.team6.finalproject.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom {
    Optional<Profile> findByUserId(Long id);
    Optional<Profile> findByNickname(String nickname);
}
