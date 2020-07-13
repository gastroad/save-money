package com.savemoney.steam.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SteamGameService extends SteamBaseService {

    public JSONArray getGlobalAchievementPercentagesForApp(String gameId)
            throws ParseException, HttpServerErrorException, ResourceAccessException {
        String url = "/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v0002/";

        // 요청 URL 생성
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("gameid", gameId)
                .queryParam("format", "")
                .build()
                .toString();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(result);
        JSONObject achievementPercentAges = (JSONObject) data.get("achievementpercentages");
        JSONArray achievements = (JSONArray) achievementPercentAges.get("achievements");
        
        // TODO: DB 저장 로직 작성

        return achievements;
    }

    public void getGlobalStatsForGame() {
    }

    /**
     *
     * @param steamId
     * @param appId
     * @return
     */
    public JSONArray getPlayerAchievements(String steamId, String appId) throws ParseException {
        String url = "/ISteamUserStats/GetPlayerAchievements/v0001/";

        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + url)
                .queryParam("key", steamApiKey)
                .queryParam("steamid", steamId)
                .queryParam("appid", appId)
                .queryParam("format", "")
                .queryParam("l", "")
                .build()
                .toString();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(result);

        return null;
    }

    public void getUserStatsForGame() {
    }

    public void getRecentlyPlayedGames() {
    }

    public void isPlayingSharedGame() {
    }

    public void getSchemaForGame() {
    }

    public void getPlayerBans() {
    }

}
