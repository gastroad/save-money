package com.savemoney.web.controller;

import com.savemoney.core.domain.UserEntity;
import com.savemoney.core.service.UserService;
import com.savemoney.core.util.cryption.Base64;
import com.savemoney.core.util.validation.ValidationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    /**
     * User Service
     */
    private final UserService userService;

    /**
     * Validation Component
     */
    private final ValidationProvider validationProvider;

    /**
     * 회원 가입
     * @param userEntity
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity join(@Valid  UserEntity userEntity, Errors errors) {
        Map<String, Object> response = new HashMap<>();

        // 유효성 체크
        if (errors.hasErrors()) {
            return validationProvider.valid(errors);
        }

        // 회원 중복 확인
        if (userService.duplicate(userEntity.getId())) {
            response.put("success", false);
            response.put("message", "아이디가 존재합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 회원 가입
        try {
            response.put("success", true);
            response.put("data", userService.join(userEntity));
        } catch (NoSuchAlgorithmException e) {
            response.put("success", false);
            response.put("message", "암호화 에러 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok().body(response);
    }

}
