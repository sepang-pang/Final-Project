package com.team6.finalproject.club.interest.controller;

import com.team6.finalproject.club.interest.dto.InterestMinorAllDto;
import com.team6.finalproject.club.interest.service.InterestMinorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterestMinorController {
    private final InterestMinorService interestMinorService;

    @GetMapping("/interest-minor/{majorId}")
    public List<InterestMinorAllDto> findAllInterestMinorByMajorId(@PathVariable Long majorId) {
        return interestMinorService.findAllInterestMinorByMajorId(majorId);
    }
}
