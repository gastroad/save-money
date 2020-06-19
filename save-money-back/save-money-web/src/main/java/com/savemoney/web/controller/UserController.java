package com.savemoney.web.controller;

import com.savemoney.web.config.security.mapper.UserMapper;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    /**
     * User Mapper
     */
    private final UserMapper userMapper;

    /**
     * 패스워드 암호화 Component
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT Component
     */
    private final JwtTokenProvider jwtTokenProvider;

    /*
     * TODO: Controller Database 연동(인증)
     */
    @RequestMapping(value = "/getAccess/", method = RequestMethod.GET)
    public String getAccess(@RequestParam("id") String id,
                            @RequestParam("password") String passwrd) {
        if (!"password".equals(passwrd)) {
            return "ERROR";
        }
        List<String> roles = new ArrayList<String>() {{ add("USER"); }};
        return jwtTokenProvider.createToken(id, roles);
    }


}
