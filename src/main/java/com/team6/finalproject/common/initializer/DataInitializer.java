package com.team6.finalproject.common.initializer;

import com.team6.finalproject.interest.entity.InterestMajor;
import com.team6.finalproject.interest.entity.InterestMinor;
import com.team6.finalproject.interest.repository.InterestMajorRepository;
import com.team6.finalproject.interest.repository.InterestMinorRepository;
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
        InterestMajor sportsCategory = new InterestMajor();
        sportsCategory.setMajorName("스포츠");

        InterestMinor soccerSubcategory = new InterestMinor();
        soccerSubcategory.setMinorName("축구");
        soccerSubcategory.setInterestMajor(sportsCategory);

        InterestMinor basketballSubcategory = new InterestMinor();
        basketballSubcategory.setMinorName("농구");
        basketballSubcategory.setInterestMajor(sportsCategory);

        interestMajorRepository.save(sportsCategory);
        interestMinorRepository.save(soccerSubcategory);
        interestMinorRepository.save(basketballSubcategory);
    }
}
