package com.team6.finalproject.club.interest.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.interest.entity.InterestMinor;

public interface InterestMinorService {
    // 소주제 존재 유무 확인
    public InterestMinor existsInterestMinor(Long id) throws NotExistResourceException;

}
