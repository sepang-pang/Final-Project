package com.team6.finalproject.club.interest.service;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.repository.InterestMinorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestMinorService {

    private final InterestMinorRepository interestMinorRepository;

    // 소주제 존재 유무 확인
    public InterestMinor existsInterestMinor(Long id) {
        return interestMinorRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 종목입니다."));
    }
}
