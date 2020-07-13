package com.savemoney.steam.service;

import com.savemoney.core.domain.SteamUserEntity;
import com.savemoney.core.mapper.SteamUserMapper;
import com.savemoney.steam.domain.OwnedGameDto;
import com.savemoney.steam.domain.PlayerSummariesDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

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
                // TODO: DB 저장 로직 작성
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
     * 소유 게임 목록 얻기
     * @param steamId   steamid(64 bit)
     * @return          소유 게임 목록
     */
    public List<OwnedGameDto> getOwnedGames(String steamId) {
        String url = "/IPlayerService/GetOwnedGames/v0001/";

        // 요청 URL 생성
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("key", steamApiKey)
                .queryParam("include_appinfo", "true")
                .queryParam("steamid", steamId)
                .queryParam("include_played_free_games", "true")
                .queryParam("format", "")
                .queryParam("appids_filter", "")
                .build()
                .toString();

        // 요청
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject data = (JSONObject) jsonParser.parse(result);
            JSONObject response = (JSONObject) data.get("response");

            // 소유 게임 갯수
            Long gameCount = (Long) response.get("game_count");

            // TODO: DB 저장 로직 작성

            // 소유 게임 목록
            JSONArray games = (JSONArray) response.get("games");
            List<OwnedGameDto> ownedGameDtoList = new ArrayList<>();

            // Icon, Logo Base URL
            String baseImgUrl = "http://media.steampowered.com/steamcommunity/public/images/apps/";

            games.forEach(game -> {
                JSONObject gameToJsonObject = (JSONObject) game;

                Long appId = (Long) gameToJsonObject.get("appid");
                String imgIconUrl = baseImgUrl + appId + "/" + gameToJsonObject.get("img_icon_url") + ".jpg";
                String imgLogoUrl = baseImgUrl + appId + "/" + gameToJsonObject.get("img_logo_url") + ".jpg";

                OwnedGameDto ownedGameDto = OwnedGameDto.builder()
                        .appId(appId)
                        .name((String) gameToJsonObject.get("name"))
                        .playTime2Weeks((String) gameToJsonObject.get("playtime_2weeks"))
                        .playTimeForever((Long) gameToJsonObject.get("playtime_forever"))
                        .imgIconUrl(imgIconUrl)
                        .imgLogoUrl(imgLogoUrl)
                        .hasCommunityVisibleStats((Boolean) gameToJsonObject.get("has_community_visible_stats"))
                        .build();
                ownedGameDtoList.add(ownedGameDto);
            });

            // TODO: DB 저장 로직 작성

            return ownedGameDtoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Steam 레벨 얻기
     * @param steamId   steamid(64 bit)
     * @return          레벨
     */
    public Long getSteamLevel(String steamId) {
        String url = "/IPlayerService/GetSteamLevel/v1/";

        // 요청 URL 생성
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("key", steamApiKey)
                .queryParam("steamid", steamId)
                .build()
                .toString();

        // 요청
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject data = (JSONObject) jsonParser.parse(result);
            JSONObject response = (JSONObject) data.get("response");

            // Steam 레벨
            Long playerLevel = (Long) response.get("player_level");

            // TODO: DB 저장 로직 작성

            return playerLevel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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



}
