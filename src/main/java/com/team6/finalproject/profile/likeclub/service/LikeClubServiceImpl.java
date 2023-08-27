package com.team6.finalproject.profile.likeclub.service;

import com.team6.finalproject.profile.likeclub.entity.LikeClub;
import com.team6.finalproject.profile.likeclub.repository.LikeClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeClubServiceImpl implements LikeClubService {

    private final LikeClubRepository likeClubRepository;

    @Override
    @Transactional
    public void save(LikeClub likeclub) {
        likeClubRepository.save(likeclub);
    }
}
