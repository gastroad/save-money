package com.savemoney.web.controller;

import com.savemoney.core.util.validation.ValidationProvider;
import com.savemoney.web.config.security.domain.AccessEntity;
import com.savemoney.web.config.security.domain.CertificationEntity;
import com.savemoney.web.config.security.mapper.CertificationAuthorityMapper;
import com.savemoney.web.config.security.mapper.CertificationMapper;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    /**
     * User Mapper
     */
    private final CertificationMapper certificationMapper;

    /**
     * User Authority Mapper
     */
    private final CertificationAuthorityMapper certificationAuthorityMapper;

    /**
     * 패스워드 암호화 Component
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT Component
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Validation Component
     */
    private final ValidationProvider validationProvider;

    /**
     * Access Token 얻기
     * @param accessEntity  Access 객체
     * @return              Access Token
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAccessToken(@Valid AccessEntity accessEntity, Errors errors) {
        Map<String, Object> response = new HashMap<>();

        // 유효성 체크
        if (errors.hasErrors()) {
            return validationProvider.valid(errors);
        }

        // 회원 정보 확인
        CertificationEntity certificationEntity = certificationMapper.findById(accessEntity.getId());
        if (certificationEntity == null) {
            response.put("success", false);
            response.put("message", "회원정보가 존재하지 않습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(accessEntity.getPassword(), certificationEntity.getPassword())) {
            response.put("success", false);
            response.put("message", "패스워드가 맞지 않습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // 회원 권한 확인
        List<String> roles = certificationAuthorityMapper.findAuthorityById(accessEntity.getId());
        if (roles == null) {
            response.put("success", false);
            response.put("message", "접근권한이 없습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // Access Token 발급
        response.put("success", true);
        response.put("data", jwtTokenProvider.createToken(accessEntity.getId(), roles));

        return ResponseEntity.ok().body(response);
    }

}
