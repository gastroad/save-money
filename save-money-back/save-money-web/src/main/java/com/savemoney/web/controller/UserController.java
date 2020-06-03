package com.savemoney.web.controller;

import com.savemoney.core.domain.UserAuthority;
import com.savemoney.core.domain.UserEntity;
import com.savemoney.core.service.UserService;
import com.savemoney.web.config.security.service.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    public String getAccessToken(@RequestParam("id") String id,
                                 @RequestParam("password") String password) {
        UserEntity userEntity = userService.getUserById(id);
        List<UserAuthority> userAuthorities = null;
        // System.out.println("Password Encode: " + passwordEncoder.encode(password));
        //if (!passwordEncoder.matches(password, userEntity.getPassword())) {
        if (!password.equals(userEntity.getPassword())) {
            return "Fail...";
        } else {
            userService.getUserAuthoritiesById(id);
        }
        return jwtTokenProvider.createToken(userEntity.getId(), userAuthorities  );

    }

}
