package com.team6.finalproject.user.repository;

import com.team6.finalproject.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    public Optional<User> findByActivePhone(String phone);
    public Optional<User> findByActiveId(Long id);
}
