package com.savemoney.web.controller.steam;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/steam/achievements")
public class SteamAchievementsController {

    /**
     * Steam 사용자 업적 조회
     * @param appId 앱 ID(PK)
     * @return      업적 목록
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    public ResponseEntity getAchievements(@PathVariable("appId") String appId) {
        // TODO: Steam 사용자 업적 조회 로직(Controller)
        return null;
    }

}
