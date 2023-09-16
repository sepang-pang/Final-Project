package com.team6.finalproject.club.interest.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.interest.dto.InterestMinorAllDto;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InterestMinorService {
    // 소주제 존재 유무 확인
    public InterestMinor existsInterestMinor(Long id) throws NotExistResourceException;
    public List<InterestMinorAllDto> findAllInterestMinorByMajorId(Long majorId);

}
