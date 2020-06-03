package com.savemoney.core.service;

import com.savemoney.core.domain.UserAuthority;
import com.savemoney.core.mapper.UserAuthorityMapper;
import com.savemoney.core.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService<T> {

    private final UserMapper userMapper;
    private final UserAuthorityMapper userAuthorityMapper;

    public T getUserById(String id) {
        return (T) userMapper.findById(id);
    }

    public List<UserAuthority> getUserAuthoritiesById(String id) {
        return userAuthorityMapper.findById(id);
    }

}
