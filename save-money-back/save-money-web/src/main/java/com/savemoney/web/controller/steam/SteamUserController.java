package com.savemoney.web.controller.steam;

import com.savemoney.steam.service.SteamUserService;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity getUser(HttpServletRequest httpServletRequest) {
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

        // TODO: Steam 사용자 DB 저장 로직(Controller)

        // Steam User 정보 조회
        response.put("success", true);
        response.put("player", steamUserService.getPlayerSummaries(steamId));

        return ResponseEntity.ok().body(response);
    }

    /**
     * Steam 레벨 얻기
     * @param httpServletRequest    요청 객체
     * @return                      Steam 레벨
     */
    @RequestMapping(value = "/level/", method = RequestMethod.GET)
    public ResponseEntity getUserLevel(HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();

        // 요청 객체에서 Token 받아오기
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if (token == null) {
            response.put("success", false);
            response.put("message", "정상적인 접근이 아닙니다");
            return ResponseEntity.badRequest().body(response);
        }

        // 사용자 steamId 조회
        String steamId = steamUserService.getSteamIdById(jwtTokenProvider.getUserPk(token));
        if (steamId == null) {
            response.put("success", false);
            response.put("message", "Steam 계정과 연동이 되어 있지 않습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // TODO: Steam 사용자 레벨 DB 저장 로직(Controller)

        response.put("success", true);
        response.put("level", steamUserService.getSteamLevel(steamId));

        return ResponseEntity.ok().body(response);
    }

    /**
     * 사용자 Steam 게임 목록 얻기
     * @param httpServletRequest    요청 객체
     * @return                      사용자 Steam 게임 목록
     */
    @RequestMapping(value = "/game/", method = RequestMethod.GET)
    public ResponseEntity getUserGame(HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();

        // 요청 객체에서 Token 받아오기
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if (token == null) {
            response.put("success", false);
            response.put("message", "정상적인 접근이 아닙니다");
            return ResponseEntity.badRequest().body(response);
        }

        // 사용자 steamId 조회
        String steamId = steamUserService.getSteamIdById(jwtTokenProvider.getUserPk(token));
        if (steamId == null) {
            response.put("success", false);
            response.put("message", "Steam 계정과 연동이 되어 있지 않습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // TODO: Steam 사용자 게임 목록 DB 저장 로직(Controller)

        // 사용자 소유 게임 목록 조회
        response.put("success", true);
        response.put("games", steamUserService.getOwnedGames(steamId));

        return ResponseEntity.ok().body(response);
    }

    /**
     * 앱 업적 목록 조회
     * @param appId 앱 ID(PK)
     * @return      Steam 사용자 앱 업적 목록
     */
    @RequestMapping(value = "/achievements/{appId}", method = RequestMethod.GET)
    public ResponseEntity getUserAchievements(@PathVariable("appId") String appId) {
        // TODO: Steam 사용자 앱 업적 목록 조회 로직(Controller)
        return null;
    }

}
