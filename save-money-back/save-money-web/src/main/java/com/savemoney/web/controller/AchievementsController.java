package com.savemoney.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/achievements")
public class AchievementsController {

    /**
     * 앱 업적 목록 조회
     * @param appId 앱 ID(PK)
     * @return      앱 업적 목록
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    public ResponseEntity getAchievements(@PathVariable("appId") String appId) {
        // TODO: DB에서 앱 업적 목록 조회(Controller)
        return null;
    }

}
