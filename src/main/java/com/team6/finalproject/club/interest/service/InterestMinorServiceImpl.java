package com.team6.finalproject.club.interest.service;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.repository.InterestMinorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestMinorServiceImpl implements InterestMinorService{

    private final InterestMinorRepository interestMinorRepository;
    @Override
    @Transactional(readOnly = true)
    public InterestMinor existsInterestMinor(Long id) {
        return interestMinorRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 종목입니다."));
    }
}
