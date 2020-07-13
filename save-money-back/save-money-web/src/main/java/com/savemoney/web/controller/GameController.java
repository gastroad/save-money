package com.savemoney.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    /**
     * 게임 목록 조회
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getGame() {
        // TODO: DB에서 게임 목록 조회(Controller)
        return null;
    }

}
