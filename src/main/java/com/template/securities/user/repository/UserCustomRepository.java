package com.template.securities.user.repository;

import com.template.securities.user.domain.Users;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<Users> findUserById(Long id);
}
