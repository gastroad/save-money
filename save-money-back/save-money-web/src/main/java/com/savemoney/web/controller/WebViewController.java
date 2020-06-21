package com.savemoney.web.controller;

import com.savemoney.core.service.CoreService;
import com.savemoney.core.util.cryption.Aes256;
import com.savemoney.steam.service.SteamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WebViewController {

    /**
     * Core Service
     */
    private final CoreService coreService;

    /**
     * Steam Service
     */
    private final SteamService steamService;

    /**
     * AES-256 Component
     */
    private final Aes256 aes256;

    /**
     * Web module Test
     * @return  테스트 문구
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello Save Money!";
    }

    /**
     * Core module test
     * @return  테스트 문구
     */
    @RequestMapping(value = "/core/", method = RequestMethod.GET)
    public String core() {
        return coreService.load();
    }

    /**
     * Steam module test
     * @return  테스트 문구
     */
    @RequestMapping(value = "/steam/", method = RequestMethod.GET)
    public String steam() {
        return steamService.load();
    }

    /**
     * AES-256 Component test
     * @param value 값
     * @return      암호화, 복호화 결과
     */
    @RequestMapping(value = "/aes256/", method = RequestMethod.GET)
    public ResponseEntity cryption(@RequestParam("value") String value) {
        Map<String, Object> response = new HashMap<>();

        String enc = aes256.encryption(value);
        response.put("enc", enc);
        response.put("dec", aes256.decryption(enc));

        return ResponseEntity.ok().body(response);
    }

}
