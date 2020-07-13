package com.savemoney.steam.service;

import com.savemoney.core.domain.SteamUserEntity;
import com.savemoney.core.mapper.SteamUserMapper;
import com.savemoney.steam.domain.VanityUrlResDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class SteamAccountService extends SteamBaseService {

    /**
     * Steam User Mapper
     */
    private final SteamUserMapper steamUserMapper;

    /**
     * steamId(64 bit) 얻기
     * @param vanityUrl 사용자 지정 URL
     * @return          steamId (64 bit)
     */
    public VanityUrlResDto resolveVanityUrl(String vanityUrl) throws ParseException {
        String url = "/ISteamUser/ResolveVanityURL/v0001/";

        // 요청 URL 생성
        String requestURL = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("key", steamApiKey)
                .queryParam("vanityurl", vanityUrl)
                .build()
                .toString();

        // 요청
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestURL, String.class);

        // 응답 데이터 JSON 객체로 변환
        JSONParser jsonParser = new JSONParser();
        JSONObject originalData = (JSONObject) jsonParser.parse(response);
        JSONObject data = (JSONObject) originalData.get("response");

        if (data.get("success").equals(0) || data.get("success").equals(42)) {
            return VanityUrlResDto.builder()
                    .success(false)
                    .message((String) data.get("message"))
                    .build();
        } else {
            return VanityUrlResDto.builder()
                    .success(true)
                    .message((String) data.get("message"))
                    .steamId((String) data.get("steamid"))
                    .build();
        }
    }

    /**
     * DB 존재 유무
     * @param vanityUrl 사용자 지정 URL
     * @return          DB 존재 유무
     */
    public Boolean isSaveSteamUser(String vanityUrl) {
        if (steamUserMapper.countByVanityUrl(vanityUrl) < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Steam 계정 저장
     * @param steamUserEntity   Steam 계정 정보
     * @return                  STEAM_USER_ID
     */
    public Long registry(SteamUserEntity steamUserEntity) {
        return steamUserMapper.save(steamUserEntity);
    }

}
