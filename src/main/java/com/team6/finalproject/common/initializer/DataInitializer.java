package com.team6.finalproject.common.initializer;

import com.team6.finalproject.club.interest.entity.InterestMajor;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.club.interest.repository.InterestMajorRepository;
import com.team6.finalproject.club.interest.repository.InterestMinorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final InterestMajorRepository interestMajorRepository;
    private final InterestMinorRepository interestMinorRepository;
    @Override
    public void run(String[] args) throws Exception {
        // 스포츠
        InterestMajor sportsCategory = InterestMajor.builder()
                .majorName("스포츠")
                .build();

        InterestMinor soccerSubcategory = InterestMinor.builder()
                .minorName("축구")
                .interestMajor(sportsCategory)
                .build();

        InterestMinor baseBallSubcategory = InterestMinor.builder()
                .minorName("야구")
                .interestMajor(sportsCategory)
                .build();

        // 아웃도어
        InterestMajor outdoorCategory = InterestMajor.builder()
                .majorName("아웃도어")
                .build();

        InterestMinor campingSubcategory = InterestMinor.builder()
                .minorName("캠핑")
                .interestMajor(outdoorCategory)
                .build();

        InterestMinor fishingBallSubcategory = InterestMinor.builder()
                .minorName("낚시")
                .interestMajor(outdoorCategory)
                .build();


        interestMajorRepository.save(sportsCategory);
        interestMajorRepository.save(outdoorCategory);
        interestMinorRepository.save(soccerSubcategory);
        interestMinorRepository.save(baseBallSubcategory);
        interestMinorRepository.save(campingSubcategory);
        interestMinorRepository.save(fishingBallSubcategory);

    }
}