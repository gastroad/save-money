package com.savemoney.web.controller.steam;

import com.savemoney.core.domain.SteamUserEntity;
import com.savemoney.steam.domain.VanityUrlReqDto;
import com.savemoney.steam.domain.VanityUrlResDto;
import com.savemoney.steam.service.SteamAccountService;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
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
@RequestMapping("/api/steam/account")
public class SteamAccountController {

    /**
     * JWT Token Provider
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Steam User Service
     */
    private final SteamAccountService steamAccountService;

    /**
     * steamId(64 bit) 얻기
     * @param request                   요청 객체
     * @param vanityUrlReqDto           사용자 지정 URL 객체
     * @param errors                    에러 객체
     * @return                          steamid(64 bit)
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity getAccount(HttpServletRequest request,
                                     @Valid VanityUrlReqDto vanityUrlReqDto, Errors errors) {
        Map<String, Object> response = new HashMap<>();

        // 유효성 확인
        if (errors.hasErrors()) {
            response.put("success", false);
            response.put("message", errors.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }

        // DB에 저장 유무 확인
        if (steamAccountService.isSaveSteamUser(vanityUrlReqDto.getVanityUrl())) {
            response.put("success", false);
            response.put("message", "이미 등록 되었습니다");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Get steamId
            VanityUrlResDto vanityUrlResDto = steamAccountService.resolveVanityUrl(vanityUrlReqDto.getVanityUrl());
            if (vanityUrlResDto == null) {
                // null
                response.put("success", false);
                response.put("message", "Steam Web API 호출 중, 문제가 발생하였습니다");
                return ResponseEntity.badRequest().body(response);
            } else if (vanityUrlResDto.getSuccess()) {
                // No Match
                response.put("success", false);
                response.put("message", vanityUrlResDto.getMessage());
                return ResponseEntity.badRequest().body(response);
            }

            // Token에서 사용자 구별 정보(아이디) 추출
            String id = jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN"));

            // 등록 데이터 생성
            SteamUserEntity steamUserEntity = SteamUserEntity.builder()
                    .id(id)
                    .vanityUrl(vanityUrlReqDto.getVanityUrl())
                    .steamId(vanityUrlResDto.getSteamId())
                    .build();

            // 등록에 실패한 경우
            if (steamAccountService.registry(steamUserEntity) == null) {
                response.put("success", false);
                response.put("message", "등록에 실패하였습니다");
                return ResponseEntity.badRequest().body(response);
            }

            // 조회 성공
            response.put("success", true);
            response.put("meassage", "등록에 성공하였습니다");
            response.put("steamId", vanityUrlResDto.getSteamId());
        } catch (ParseException e) {
            response.put("success", false);
            response.put("message", "API 호출 중, 문제가 발생하였습니다");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().body(response);
    }

}
