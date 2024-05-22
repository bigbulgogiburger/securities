package com.template.securities.user.repository;

import com.template.securities.user.domain.QUsers;
import com.template.securities.user.domain.Users;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

import static com.template.securities.user.domain.QUsers.*;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository{


    public UserCustomRepositoryImpl() {
        super(Users.class);
    }

    public Optional<Users> findUserById(Long id){
        Users userInfo = from(users).where(users.userId.eq(id)).fetchOne();

        return Optional.ofNullable(userInfo);
    }
}
