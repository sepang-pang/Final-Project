package com.team6.finalproject.interest.repository;

import com.team6.finalproject.interest.entity.InterestMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestMajorRepository extends JpaRepository<InterestMajor, Long> {
}
