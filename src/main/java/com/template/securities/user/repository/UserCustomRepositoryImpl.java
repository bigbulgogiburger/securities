package com.template.securities.user.repository;

import com.template.securities.user.domain.QUsers;
import com.template.securities.user.domain.Users;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository{


    public UserCustomRepositoryImpl() {
        super(Users.class);
    }

    public Optional<Users> findUserById(Long id){
        Users users = from(QUsers.users).where(QUsers.users.userId.eq(id)).fetchOne();

        return Optional.ofNullable(users);
    }
}
