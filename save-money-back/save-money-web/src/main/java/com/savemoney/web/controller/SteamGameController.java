package com.savemoney.web.controller;

import com.savemoney.steam.service.SteamGameService;
import com.savemoney.steam.service.SteamUserService;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/steam/game")
public class SteamGameController {

    /**
     * Steam User Service
     */
    private final SteamUserService steamUserService;

    /**
     * Steam Game Service
     */
    private final SteamGameService steamGameService;

    /**
     * JWT Token Provider
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 전체 사용자 업적
     * @param gameId    게임 ID
     * @return          전체 사용자 업적
     */
    @RequestMapping(value = "/global/{gameId}/", method = RequestMethod.GET)
    public ResponseEntity getGlobalAchievements(@PathVariable("gameId") String gameId) {
        Map<String, Object> response = new HashMap<>();

        try {
            response.put("success", true);
            response.put("achievements", steamGameService.getGlobalAchievementPercentagesForApp(gameId));
        } catch (ParseException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "데이터 파싱중 문제가 발생하였습니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        } catch (HttpServerErrorException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "API 요청중 문제가 발생하였습니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        }

        return ResponseEntity.ok().body(response);
    }

    /**
     *
     * @param appId    게임 ID
     * @return          개별 사용자 업적
     */
    @RequestMapping(value = "//{appId}/", method = RequestMethod.GET)
    public ResponseEntity getAchievements(HttpServletRequest httpServletRequest,
                                          @PathVariable("appId") String appId) {
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
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("success", true);
            response.put("achievements", steamGameService.getPlayerAchievements(steamId, appId));
        } catch (ParseException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "데이터 파싱중 문제가 발생하였습니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        } catch (HttpServerErrorException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "API 요청중 문제가 발생하였습니다");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(response);
        }

        return ResponseEntity.ok().body(response);
    }

}
