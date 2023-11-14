package com.template.securities.repository;

import com.template.securities.domain.Users;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<Users> findUserById(Long id);
}
