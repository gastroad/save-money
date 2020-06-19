package com.savemoney.web.config.security.service;

import com.savemoney.web.config.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * User Mapper
     */
    private final UserMapper userMapper;

    /*
     * TODO: InMemory 방식 인증 -> Database 연동 방식 인증으로 변경
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
    }

}
