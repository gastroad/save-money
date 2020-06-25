package com.savemoney.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SteamUserEntity extends TimeEntity {

    /**
     * STEAM_USER_ID(PK)
     */
    private Long steamUserId;

    /**
     * 사용자 구별 정보(아이디, FK)
     */
    private String id;

    /**
     * 사용자 지정 URL
     */
    private String vanityUrl;

    /**
     * 사용자 지정 URL(64 bit)
     */
    private String steamId;

}
