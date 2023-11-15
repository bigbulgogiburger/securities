package com.template.securities.user.service;

import com.template.securities.user.domain.Users;
import com.template.securities.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + "->데이터 베이스에서 찾을 수 없습니다."));

    }

    private org.springframework.security.core.userdetails.User createUser(String username, Users users) {
        if(!users.isActivated()){
            throw new RuntimeException(username + "->활성화 되어 있지 않습니다.");
        }
        List<SimpleGrantedAuthority> grantedAuthorityList = users.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),grantedAuthorityList);
    }

}
