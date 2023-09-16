package com.team6.finalproject.club.interest.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.club.interest.dto.InterestMinorAllDto;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.repository.InterestMinorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestMinorServiceImpl implements InterestMinorService{

    private final InterestMinorRepository interestMinorRepository;
    @Override
    @Transactional(readOnly = true)
    public InterestMinor existsInterestMinor(Long id) throws NotExistResourceException {
        return interestMinorRepository.findById(id)
                .orElseThrow(()-> new NotExistResourceException("존재하지 않는 종목입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterestMinorAllDto> findAllInterestMinorByMajorId(Long majorId) {
        return interestMinorRepository.findByMajorId(majorId).stream()
                .map(InterestMinorAllDto::new)
                .toList();
    }
}
