package com.savemoney.web.controller;

import com.savemoney.steam.service.SteamUserService;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/steam/user")
public class SteamUserController {

    /**
     * JWT Token Provider
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Steam User Service
     */
    private final SteamUserService steamUserService;

    /**
     * Steam 사용자 정보 조회
     * @param httpServletRequest    요청 객체
     * @return                      Steam 사용자 정보
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getSteamUserInfo(HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();

        // 요청 객체에서 Token 받아오기
        String xAuthToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if (xAuthToken == null) {
            response.put("success", false);
            response.put("message", "정상적인 접근이 아닙니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        }

        // SteamId 조회
        String steamId = steamUserService.getSteamIdById(jwtTokenProvider.getUserPk(xAuthToken));
        if (steamId == null) {
            response.put("success", false);
            response.put("message", "Steam 계정과 연동이 되어 있지 않습니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        }

        // Steam User 정보 조회
        response.put("success", true);
        response.put("player", steamUserService.getPlayerSummaries(steamId));

        return ResponseEntity.ok().body(response);
    }

}
