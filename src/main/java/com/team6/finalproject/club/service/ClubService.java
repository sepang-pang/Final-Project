package com.team6.finalproject.club.service;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.dto.ClubResponseDto;
import com.team6.finalproject.user.entity.User;

public interface ClubService {

    //동호회 개설
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto, User user);

}

