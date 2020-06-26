package com.savemoney.web.controller;

import com.savemoney.core.domain.SteamUserEntity;
import com.savemoney.steam.domain.ResolveVanityUrlDto;
import com.savemoney.steam.domain.ResolveVanityUrlReqDto;
import com.savemoney.steam.service.SteamUserService;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/steam/id")
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
     * steamid(64 bit) 얻기
     * @param request                   요청 객체
     * @param resolveVanityUrlReqDto    사용자 지정 URL 객체
     * @param errors                    에러 객체
     * @return                          steamid(64 bit)
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity getSteamId(HttpServletRequest request,
                                     @Valid ResolveVanityUrlReqDto resolveVanityUrlReqDto, Errors errors) {
        Map<String, Object> response = new HashMap<>();

        // 유효성 확인
        if (errors.hasErrors()) {
            response.put("success", false);
            response.put("message", errors.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }

        // DB에 저장 유무 확인
        if (steamUserService.isSaveSteamUser(resolveVanityUrlReqDto.getVanityUrl())) {
            response.put("success", false);
            response.put("message", "이미 등록 되었습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // Get steamid
        ResolveVanityUrlDto resolveVanityUrlDto = steamUserService.getSteamId(resolveVanityUrlReqDto.getVanityUrl());
        if (resolveVanityUrlDto == null) {
            // null
            response.put("success", false);
            response.put("message", "Steam Web API 호출 중, 문제가 발생하였습니다");
            return ResponseEntity.badRequest().body(response);
        } else if (resolveVanityUrlDto.getSuccess() == 42) {
            // No Match
            response.put("success", false);
            response.put("message", "URL이 존재하지 하지 않습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // Token에서 사용자 구별 정보(아이디) 추출
        String id = jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN"));

        // 등록 데이터 생성
        SteamUserEntity steamUserEntity = SteamUserEntity.builder()
                .id(id)
                .vanityUrl(resolveVanityUrlReqDto.getVanityUrl())
                .steamId(resolveVanityUrlDto.getSteamid())
                .build();

        // TODO: DB 저장 로직
        if (steamUserService.registry(steamUserEntity) == null) {
            response.put("success", false);
            response.put("message", "등록에 실패하였습니다");
            return ResponseEntity.badRequest().body(response);
        }

        // 조회 성공
        response.put("success", true);
        response.put("meassage", "등록에 성공하였습니다");
        response.put("data", resolveVanityUrlDto.getSteamid());

        return ResponseEntity.ok().body(response);
    }

}
