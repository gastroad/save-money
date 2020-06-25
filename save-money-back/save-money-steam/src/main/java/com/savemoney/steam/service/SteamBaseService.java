package com.savemoney.steam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SteamBaseService {

    /**
     * Steam API Key
     */
    @Value("${steam.api.key}")
    protected String steamApiKey;

    /**
     * Steam API Base URL
     */
    @Value("${steam.api.url}")
    protected String baseUrl;

}
