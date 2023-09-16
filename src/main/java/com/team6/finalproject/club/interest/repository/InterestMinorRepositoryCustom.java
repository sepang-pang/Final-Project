package com.team6.finalproject.club.interest.repository;

import com.team6.finalproject.club.interest.entity.InterestMinor;

import java.util.List;

public interface InterestMinorRepositoryCustom {
    public List<InterestMinor> findByMajorId(Long majorId);
}
