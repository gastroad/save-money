package com.savemoney.steam.service;

import com.savemoney.core.domain.SteamUserEntity;
import com.savemoney.core.mapper.SteamUserMapper;
import com.savemoney.steam.domain.PlayerSummariesDto;
import com.savemoney.steam.domain.ResolveVanityUrlDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class SteamUserService extends SteamBaseService {

    /**
     * Steam User Mapper
     */
    private final SteamUserMapper steamUserMapper;

    /**
     * Steam Player 정보 얻기
     * @param steamId   steamid(64 bit)
     * @return          Steam Player 정보
     */
    public PlayerSummariesDto getPlayerSummaries(String steamId) {
        String url = "/ISteamUser/GetPlayerSummaries/v0002/";

        // 요청 URL 생성
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("key", steamApiKey)
                .queryParam("steamids", steamId)
                .queryParam("format", "")
                .build()
                .toString();

        // 요청
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestUrl, String.class);

        try {
            // 응답 데이터 JSON 객체로 변환
            JSONParser jsonParser = new JSONParser();
            JSONObject originalData = (JSONObject) jsonParser.parse(response);
            JSONObject dataResponse = (JSONObject) originalData.get("response");
            JSONArray players = (JSONArray) dataResponse.get("players");
            JSONObject data = (JSONObject) players.get(0);

            if (data == null) {
                return null;
            } else {
                return PlayerSummariesDto.builder()
                        .steamId((String) data.get("steamid"))
                        .personaName((String) data.get("personaname"))
                        .profileUrl((String) data.get("profileurl"))
                        .avatar((String) data.get("avatar"))
                        .avatarMedium((String) data.get("avatarmedium"))
                        .avatarFull((String) data.get("avatarfull"))
                        .personaState((Long) data.get("personastate"))
                        .communityVisibilityState((Long) data.get("communityvisibilitystate"))
                        .profileState((Long) data.get("profilestate"))
                        .lastLogOff((Long) data.get("lastlogoff"))
                        .commentPermission((String) data.get("commentpermission"))
                        .realName((String) data.get("realname"))
                        .primaryClanId((String) data.get("primaryclanid"))
                        .timeCreated((Long) data.get("timecreated"))
                        .gameId((String) data.get("gameid"))
                        .gameServerIp((String) data.get("gameserverip"))
                        .gameExtraInfo((String) data.get("gameextrainfo"))
                        .cityId((String) data.get("cityid"))
                        .locCountryCode((String) data.get("loccountrycode"))
                        .locStateCode((String) data.get("locstatecode"))
                        .locCityId((Long) data.get("loccityid"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * steamid(64 bit) 얻기
     * @param vanityUrl 사용자 지정 URL
     * @return          steamid (64 bit)
     */
    public ResolveVanityUrlDto getSteamId(String vanityUrl) {
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

        try {
            // 응답 데이터 JSON 객체로 변환
            JSONParser jsonParser = new JSONParser();
            JSONObject originalData = (JSONObject) jsonParser.parse(response);
            JSONObject data = (JSONObject) originalData.get("response");

            if (data == null) {
                return null;
            } else {
                return ResolveVanityUrlDto.builder()
                        .success((Long) data.get("success"))
                        .message((String) data.get("message"))
                        .steamid((String) data.get("steamid"))
                        .build();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
     * SteamId(64 bit) 조회
     * @param id    사용자 구별 정보(아이디)
     * @return      steamid(64 bit)
     */
    public String getSteamIdById(String id) {
        return steamUserMapper.findById(id);
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
